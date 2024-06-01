package vom.spring.global.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponse {
    private Boolean isRegistered;
    private Long memberId;
    private String accessToken;
}
