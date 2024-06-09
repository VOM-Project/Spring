package vom.spring.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vom.spring.global.jwt.JwtTokenProvider;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StompPreHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private String token;
    private Long memberId = 0L;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
            StompCommand command = headerAccessor.getCommand();
            if (command.equals(StompCommand.UNSUBSCRIBE) || command.equals(StompCommand.MESSAGE) || command.equals(StompCommand.CONNECTED) || command.equals(StompCommand.SEND))
                return message;
            else if (command.equals(StompCommand.ERROR)) {
                log.error("메시지 에러");
                throw new MessageDeliveryException("error");
            }
            if (authorizationHeader == null) {
                log.info("chat header가 없는 요청입니다.");
                throw new MalformedJwtException("jwt");
            }
            // 토큰 추출
            token = "";
            String authorizationHeaderStr = authorizationHeader.replace("[","").replace("]","");
            if (authorizationHeaderStr.startsWith("Bearer ")) {
                token = authorizationHeaderStr.replace("Bearer ", "");
            } else {
                log.error("Authorization 헤더 형식이 틀립니다. : {}", authorizationHeader);
                throw new MalformedJwtException("jwt");
            }
            //memberId 추출
            try {
                memberId = jwtTokenProvider.getMemberId(token);

            } catch (Exception e) {
                throw new MalformedJwtException("stomp_jwt");
            }
            //토큰 유효검증
            if (jwtTokenProvider.validateAccessToken(token)) {//access token 검증
                setAuthentication(message, headerAccessor);
                log.info("인증 성공");
            }
        } catch (Exception e) {
            log.error("JWT에러");
            throw new MalformedJwtException("jwt");
        }
        return message;
    }

    /**
     * Security ContextHolder에 인증 정보 담아두기
     */
    private void setAuthentication(Message<?> message, StompHeaderAccessor headerAccessor) {
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberId, null, List.of(new SimpleGrantedAuthority(MemberRole.USER.name())));
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        headerAccessor.setUser(authentication);
    }
}
