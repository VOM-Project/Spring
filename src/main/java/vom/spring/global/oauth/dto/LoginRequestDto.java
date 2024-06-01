package vom.spring.global.oauth.dto;

import lombok.*;


public class LoginRequestDto {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @Getter
    @Builder
    public static class LoginCodeDto{
        private String auth_code;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TempLoginDto {
        private String email;
    }
}



