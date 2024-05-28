package vom.spring.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vom.spring.global.oauth.dto.LoginRequest;
import vom.spring.global.oauth.dto.LoginResponse;
import vom.spring.global.oauth.service.OAuthService;

@Tag(name = "구글로그인 API", description = "구글로그인 API 명세서")
@RestController
//@RequestMapping(value = "/login/oauth2", produces = "application/json")
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthService oauthService;

    /**
     * 인가코드 받기 - back
     */
    @Operation(summary = "인가코드 받기-백엔드 테스트", description = "PathVariable로 registratino id를, 쿼리스트링으로 code를 받습니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/login/oauth2/code/{registrationId}")
    public void googleLoginBack(@PathVariable(value = "registrationId") String registrationId, @RequestParam(value = "code")String code) {
        oauthService.socialLogin(code, registrationId);
    }

    /**
     * 프론트한테 인가코드 받기
     */
    @Operation(summary = "인가코드 받기-프론트", description = "PathVariable로 registratino id를, 쿼리스트링으로 code를 받습니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/login/code/{registrationId}")
    public ResponseEntity<LoginResponse> googleLogin(@PathVariable(value = "registrationId") String registrationId, @RequestParam(value = "code")String code) {
        LoginResponse loginResponse = oauthService.socialLogin(code, registrationId);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
