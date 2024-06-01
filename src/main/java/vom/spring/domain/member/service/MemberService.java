package vom.spring.domain.member.service;

import org.springframework.web.multipart.MultipartFile;
import vom.spring.domain.member.dto.MemberRequestDto;
import vom.spring.domain.member.dto.MemberResponseDto;

import java.io.IOException;

public interface MemberService {
    MemberResponseDto.CreateDto create(MemberRequestDto.CreateMemberDto request);

    MemberResponseDto.VerifyNicknameDto verifyNickname(MemberRequestDto.VerfyNicknameDto reqeust);
    MemberResponseDto.ChangeNicknameDto checkChangeNickname(MemberRequestDto.ChangeNicknameDto request);
    MemberResponseDto.GetMemberDto searchNickname(String nickname);
    MemberResponseDto.UploadImageDto changeProfileImg(MultipartFile multipartFile) throws IOException;
    void deleteProfileImg(String deleteImgName);

}
