package vom.spring.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //WebRTC 관련
    //signal로 요청이 들어왔을 때, 아래의 signalingSocketHandler가 동작하도록 registry에 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint( "/signaling") // webSokcet 접속시 endpoint 설정
                .setAllowedOriginPatterns("*")
                .withSockJS();// 브라우저에서 WebSocket 을 지원하지 않는 경우에 대안으로 어플리케이션의 코드를 변경할 필요 없이 런타임에 필요할 때 대체하기 위해 설정
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // broker url 설정
        config.setApplicationDestinationPrefixes("/app"); // send url 설정
    }
}