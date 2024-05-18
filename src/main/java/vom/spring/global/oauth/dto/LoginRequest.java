package vom.spring.global.oauth.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Builder
public class LoginRequest {
    private String auth_code;
}
