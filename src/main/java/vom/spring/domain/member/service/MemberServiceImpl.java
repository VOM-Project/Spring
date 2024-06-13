package vom.spring.domain.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.homepy.HomepyRepository;
import vom.spring.domain.member.domain.Keyword;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.domain.MemberKeyword;
import vom.spring.domain.member.domain.Region;
import vom.spring.domain.member.dto.MemberRequestDto;
import vom.spring.domain.member.dto.MemberResponseDto;
import vom.spring.domain.member.repository.KeywordRepository;
import vom.spring.domain.member.repository.MemberKeywordRepository;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.member.repository.RegionRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final KeywordRepository keywordRepository;
    private final MemberKeywordRepository memberKeywordRepository;
    private final HomepyRepository homepyRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    @Override
    public MemberResponseDto.CreateDto create(MemberRequestDto.CreateMemberDto request) {
        //1. 회원가입 시 가입된 memberId로 user 검색
        //2. region, keyword 검색해서 region 객체, keyword 객체 생성
        //3. birth date, profile img url 넣기
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("member not found with id" + request.getMemberId()));
        String memberNickname;
        if (!isExistedNickname(request.getNickname())) {
            memberNickname = request.getNickname();
        } else { //isExisted가 true이면
            throw new IllegalArgumentException("중복된 닉네임입니다");
        }
        Region region = regionRepository.findByName(request.getRegion()).orElseThrow(() -> new IllegalArgumentException("region not found with name" + request.getRegion()));
        /**
         * 관심 키워드 저장
         */
        List<Keyword> keywordList = new ArrayList<>();
        for (String keywordName : request.getKeyword()) {
            Keyword keyword = keywordRepository.findByName(keywordName).orElseThrow(() -> new IllegalArgumentException("잘못된 형식의 키워드 이름입니다."));
            MemberKeyword memberKeyword = MemberKeyword.builder().member(member).keyword(keyword).build();
            memberKeywordRepository.save(memberKeyword);
        }
        // 닉네임, 지역, 생년월일 저장
        member.joinMember(request.getNickname(), request.getBirth(), region);
        //회언가입한 회원 homepy 생성
        Homepy homepy = Homepy.builder().member(member).build();
        homepyRepository.save(homepy);
        return MemberResponseDto.CreateDto.builder()
                .memberId(member.getId())
                .nickname(memberNickname)
                .build();
    }

    /**
     * 닉네임 검증
     */
    @Override
    public MemberResponseDto.VerifyNicknameDto verifyNickname(MemberRequestDto.VerfyNicknameDto reqeust) {
        boolean isExisted = memberRepository.existsByNickname(reqeust.getNickname());
        return MemberResponseDto.VerifyNicknameDto.builder()
                .isExisted(isExisted)
                .build();
    }

    private boolean isExistedNickname(String nickname) {
        boolean isExisted = memberRepository.existsByNickname(nickname);
        return isExisted;
    }

    /**
     * 닉네임 변경
     */
    @Transactional
    @Override
    public MemberResponseDto.ChangeNicknameDto checkChangeNickname(MemberRequestDto.ChangeNicknameDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        boolean isAvailable = false;
        // 해당하는 닉네임이 존재하지 않은 경우 사용 가능한 경우
        if (!isExistedNickname(request.getNickname())) {
            member.updateNickname(request.getNickname());
            isAvailable = true;
        }
        return MemberResponseDto.ChangeNicknameDto.builder()
                .isAvailable(isAvailable)
                .build();
    }

    /**
     * 닉네임 검색
     */
    @Override
    public MemberResponseDto.GetMemberDto searchNickname(String nickname) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        boolean isExisted = false;
        Member findMember;
        if (isExistedNickname(nickname)) {
            findMember = memberRepository.findByNickname(nickname).get();
            isExisted = true;
        } else {
            findMember = null;
            return MemberResponseDto.GetMemberDto.builder()
                    .isExisted(isExisted)
                    .findMemberId(null)
                    .nickname(null)
                    .birth(null)
                    .region(null)
                    .email(null)
                    .profileImgUrl(null)
                    .build();
        }
        return MemberResponseDto.GetMemberDto.builder()
                .isExisted(isExisted)
                .findMemberId(findMember.getId())
                .nickname(findMember.getNickname())
                .email(findMember.getEmail())
                .region(findMember.getRegion().getName())
                .birth(findMember.getBirth())
                .profileImgUrl(findMember.getProfileImgUrl())
                .build();
    }

    /**
     * 프로필 사진 변경 - 추후 s3 사진 객체 올리는 부분 책임 분리
     */
    @Transactional
    @Override
    public MemberResponseDto.UploadImageDto changeProfileImg(MultipartFile multipartFile) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));

        if (member.getProfileImgName() != null) {
            deleteProfileImg(member.getProfileImgName());
        }

        String originalFilename = multipartFile.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        String imgUrl = amazonS3Client.getUrl(bucket, originalFilename).toString();
        member.updateProfileImg(originalFilename, amazonS3Client.getUrl(bucket, originalFilename).toString());

        return MemberResponseDto.UploadImageDto.builder()
                .profileImgUrl(imgUrl)
                .build();
    }

    @Transactional
    @Override
    public void deleteProfileImg(String deleteImgName) {
        amazonS3Client.deleteObject(bucket, deleteImgName);
    }


}
