package vom.spring.member.service;

import vom.spring.member.dto.MemberRequestDto;
import vom.spring.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto.CreateDto create(MemberRequestDto.CreateMemberDto request);

    MemberResponseDto.VerifyNicknameDto verifyNickname(MemberRequestDto.VerfyNicknameDto reqeust);

    MemberResponseDto.GetMemberDto searchNickname(String nickname);
}
