package vom.spring.global.oauth.dto;

import lombok.*;

public class LoginResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetLoginDto{
        private Boolean isRegistered;
        private Long memberId;
        private String accessToken;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TempLoginDto {
        private String accessToken;
    }
}
