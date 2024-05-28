package vom.spring.global.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (preflight(request)) {
            return true;
        }
        try {
            //헤더에서 토큰 받아옴
            String accessToken = jwtTokenProvider.resolveAccessToken(request);
            if (accessToken == null) {
                log.info("token이 존재하지 않습니다");
                throw new IllegalStateException("token이 존재하지 않습니다:");
            }
            log.info("현재 accesstoken : " + accessToken);
            //토큰 확인되면  유저 정보 받아오고 authectication 객체 저장
            if (jwtTokenProvider.validateAccessToken(accessToken)) {//access token 검증
                setAuthentication(accessToken);
                log.info("인증 성공");
            }
        } catch (ExpiredJwtException e) {//만료기간 체크
            log.info("만료된 토큰입니다");
            throw new IllegalAccessException("만료된 토큰이요");
        } catch (SignatureException | UnsupportedJwtException e) { //기존서명확인불가&jwt 구조 문제
            log.info("잘못된 토큰입니다");
            throw new IllegalAccessException("잘못된 토큰이욤");
        }
        return true;
    }

    /**
     * http method인지 확인
     */
    public boolean preflight(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    public void setAuthentication(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
