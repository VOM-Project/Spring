package vom.spring.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.domain.Region;
import vom.spring.domain.member.dto.MemberRequestDto;
import vom.spring.domain.member.dto.MemberResponseDto;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.member.repository.RegionRepository;

@Service
@RequiredArgsConstructor
public class MemberServcieImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    @Transactional
    @Override
    public MemberResponseDto.CreateDto create(MemberRequestDto.CreateMemberDto request) {
        //0.닉네임 검증 한 번 더 필요
        //1. 회원가입 시 가입된 memberId로 user 검색
        //2. region, keyword 검색해서 region 객체, keyword 객체 생성
        //3. birth date, profile img url 넣기
        Region region = regionRepository.findByName(request.getRegion()).get();
        Member member = memberRepository.findById(request.getMemberId()).get();
        member.joinMember(request.getNickname(), /*profileImgUrl, */ request.getBirth(), region);
        return MemberResponseDto.CreateDto.builder()
                .memberId(member.getId())
                .nickname(request.getNickname())
                .build();
    }

    //닉네임 검증
    @Override
    public MemberResponseDto.VerifyNicknameDto verifyNickname(MemberRequestDto.VerfyNicknameDto reqeust) {
        boolean isduplicated = memberRepository.existsByNickname(reqeust.getNickname());
        return MemberResponseDto.VerifyNicknameDto.builder()
                .isDuplicated(isduplicated)
                .build();
    }
    //닉네임 검색
    @Override
    public MemberResponseDto.GetMemberDto searchNickname(String nickname) {
        Member findMember = memberRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저입니다"));
        return MemberResponseDto.GetMemberDto.builder()
                .nickname(findMember.getNickname())
                .build();
    }

}
