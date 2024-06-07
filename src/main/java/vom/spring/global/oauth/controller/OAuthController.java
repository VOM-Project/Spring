package vom.spring.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import vom.spring.global.oauth.dto.LoginRequestDto;
import vom.spring.global.oauth.dto.LoginResponseDto;
import vom.spring.global.oauth.service.OAuthService;

@Tag(name = "구글로그인 API", description = "구글로그인 API 명세서")
@RestController
//@RequestMapping(value = "/login/oauth2", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final OAuthService oauthService;

//    /**
//     * 인가코드 받기 - back
//     */
//    @Operation(summary = "인가코드 받기-백엔드 테스트", description = "PathVariable로 registratino id를, 쿼리스트링으로 code를 받습니다")
//    @ApiResponse(responseCode = "200", description = "성공")
//    @GetMapping("/login/oauth2/code/{registrationId}")
//    public void googleLoginBack(@PathVariable(value = "registrationId") String registrationId, @RequestParam(value = "code")String code) {
//        oauthService.socialLogin(code, registrationId);
//    }

    /**
     * 프론트한테 인가코드 받기
     */
    @Operation(summary = "인가코드 받기-프론트", description = "PathVariable로 registratino id를, 쿼리스트링으로 code를 받습니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<LoginResponseDto.GetLoginDto> googleLogin(@RequestParam(value = "code")String code) {
        System.out.println("인가코드 받음:"+ code);
        log.info("인가코드 받음:"+ code);
        LoginResponseDto.GetLoginDto loginResponse = oauthService.front_socialLogin(code);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }


    /**
     * 임시로그인
     */
    @Operation(summary = "임시로그인", description = "(마스터 유저로) 임시 로그인을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "임시 로그인을 합니다"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저입니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponseDto.TempLoginDto> tempLogin(@RequestBody LoginRequestDto.TempLoginDto request) {
        LoginResponseDto.TempLoginDto loginResponse = oauthService.tempLogin(request);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
