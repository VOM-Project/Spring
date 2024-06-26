package vom.spring.domain.vomvom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.vomvom.domain.Vomvom;
import vom.spring.domain.vomvom.dto.VomvomRequestDto;
import vom.spring.domain.vomvom.dto.VomvomResponseDto;
import vom.spring.domain.vomvom.repository.VomvomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VomvomServiceImpl implements VomvomService{
    private final MemberRepository memberRepository;
    private final VomvomRepository vomvomRepository;

    /**
     * 봄봄 요청하기
     */
    @Transactional
    @Override
    public void RequestVomvom (VomvomRequestDto.ReqeustVomvomDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member fromMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        Member toMember = memberRepository.findById(request.getToMemberId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저입니다"));

        Vomvom vomvom = Vomvom.builder().isVomvom(false).fromMember(fromMember).toMember(toMember).build();
        vomvomRepository.save(vomvom);
    }

    /**
     * 봄봄 요청 조회
     */
    @Override
    public VomvomResponseDto.GetMembersDto getRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member toMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        //tomember이 나이고 isvomvom이 false 인 경우
        List<Vomvom> vomVoms = vomvomRepository.findByToMember(toMember);
        VomvomResponseDto.MemberDto memberDto;
        List<VomvomResponseDto.MemberDto> memberDtoList = new ArrayList<>();
        if (!vomVoms.isEmpty()) {
            for (Vomvom vom : vomVoms) {
                if (vom.isVomvom() == false) {
                    memberDto = VomvomResponseDto.MemberDto.builder()
                            .nickname(vom.getFromMember().getNickname())
                            .profileUrl(vom.getFromMember().getProfileImgUrl())
                            .build();
                    memberDtoList.add(memberDto);
                }
            }
        }
        return VomvomResponseDto.GetMembersDto.builder()
                .memberDtoList(memberDtoList)
                .build();
    }

    /**
     * 봄봄 요청 수락
     */
    @Transactional
    @Override
    public VomvomResponseDto.AcceptVomvomDto acceptRequest (VomvomRequestDto.AcceptVomvomDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member toMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        Member fromMember = memberRepository.findByNickname(request.getNickname()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저입니다"));
        Vomvom vomvom = vomvomRepository.findByFromMemberAndToMember(fromMember, toMember).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 친구 신청입니다"));
        if (vomvom.isVomvom() == false) {
            vomvom.updateIsVomvom();
        }
        return VomvomResponseDto.AcceptVomvomDto.builder()
                .isVomvom(vomvom.isVomvom())
                .build();
    }

    /**
     * 봄봄 조회
     */
    @Override
    public VomvomResponseDto.GetMembersDto getVomvom () {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        List<Vomvom> vomvoms = vomvomRepository.findByFromMemberOrToMember(member, member);
        VomvomResponseDto.MemberDto memberDto;
        List<VomvomResponseDto.MemberDto> memberDtoList = new ArrayList<>();
        for (Vomvom vom : vomvoms) {
            if (vom.isVomvom() == true) {
                if (vom.getFromMember() == member) {
                    memberDto = VomvomResponseDto.MemberDto.builder()
                            .nickname(vom.getToMember().getNickname())
                            .profileUrl(vom.getToMember().getProfileImgUrl())
                            .build();
                } else {
                    memberDto = VomvomResponseDto.MemberDto.builder()
                            .nickname(vom.getFromMember().getNickname())
                            .profileUrl(vom.getFromMember().getProfileImgUrl())
                            .build();
                }
                memberDtoList.add(memberDto);
            }
        }
        return VomvomResponseDto.GetMembersDto.builder()
                .memberDtoList(memberDtoList)
                .build();
    }


}
