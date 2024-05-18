package vom.spring.domain.member.service;

import vom.spring.domain.member.dto.MemberRequestDto;
import vom.spring.domain.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto.CreateDto create(MemberRequestDto.CreateMemberDto request);

    MemberResponseDto.VerifyNicknameDto verifyNickname(MemberRequestDto.VerfyNicknameDto reqeust);

    MemberResponseDto.GetMemberDto searchNickname(String nickname);
}
