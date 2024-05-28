package vom.spring.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vom.spring.domain.member.dto.MemberRequestDto;
import vom.spring.domain.member.dto.MemberResponseDto;
import vom.spring.domain.member.service.MemberService;

import java.io.IOException;

@Tag(name = "유저 API", description = "유저 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 - 닉네임 중복 불가, 생년월일, 사는지역, 관심 키워드 입력 받기
     */
    @Operation(summary = "회원등록", description = "회원 등록을 합니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })

    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto.CreateDto> createUser(@RequestBody MemberRequestDto.CreateMemberDto request) {
        MemberResponseDto.CreateDto createDto = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createDto);
    }

    @Operation(summary = "닉네임 검증", description = "닉네임 검증을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 중복 x"),
                    @ApiResponse(responseCode = "403", description = "중복된 닉네임입니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PostMapping("/join/nickname")
    public ResponseEntity<MemberResponseDto.VerifyNicknameDto> verfyNickname(@RequestBody MemberRequestDto.VerfyNicknameDto request) {
        MemberResponseDto.VerifyNicknameDto response = memberService.verifyNickname(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 닉네임 변경 - 마이페이지
     */
    @Operation(summary = "닉네임 변경", description = "닉네임 변경을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 변경 완료"),
                    @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다"),
                    @ApiResponse(responseCode = "403", description = "닉네임이 중복됩니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PutMapping("/nickname")
    public ResponseEntity<MemberResponseDto.ChangeNicknameDto> changeNickname(@RequestBody MemberRequestDto.ChangeNicknameDto request) {
        MemberResponseDto.ChangeNicknameDto changeNicknameDto = memberService.checkChangeNickname(request);
        return ResponseEntity.status(HttpStatus.OK).body(changeNicknameDto);
    }

    /**
     * 닉네임 검색 - 완전 일치해야 검색가능
     */
    @Operation(summary = "닉네임 검색", description = "닉네임 검색을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 검색 완료"),
                    @ApiResponse(responseCode = "403", description = "중복된 닉네임입니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    @GetMapping("/search")
    public ResponseEntity<MemberResponseDto.GetMemberDto> searchNickname(@RequestParam(name = "nickname") String nickname) {
        MemberResponseDto.GetMemberDto response = memberService.searchNickname(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 프로필 사진 변경
     */
    @Operation(summary = "프로필 사진 변경", description = "프로필 사진 변경을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 검색 완료"),
                    @ApiResponse(responseCode = "403", description = "중복된 닉네임입니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PutMapping("/profileImg")
    public ResponseEntity<MemberResponseDto.UploadImageDto> changeProfileImg(@RequestPart MultipartFile multipartFile) throws IOException {
        MemberResponseDto.UploadImageDto uploadImageDto;
        if (multipartFile == null || multipartFile.isEmpty()) {
            uploadImageDto = MemberResponseDto.UploadImageDto.builder()
                    .profileImgUrl(null)
                    .build();
        } else {
            uploadImageDto = memberService.changeProfileImg(multipartFile);
        }
        return ResponseEntity.status(HttpStatus.OK).body(uploadImageDto);
    }

}
