//package vom.spring.oauth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final OAuthService oAuthService;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
//        http
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                //request 인증, 인가 설정
//                .authorizeRequests(request -> request.requestMatchers(
//                        new AntPathRequestMatcher("/", "/oauth2/**"),
//                        new AntPathRequestMatcher("/swagger-ui/**","/v3/api-docs/**")
//                ).permitAll() //로그인은 다 접근 가능
//                         .anyRequest().authenticated()) //그외엔 다 인증 필요
//                //oauth2 기반 인증 설정
//                .oauth2Login(oAuth2LoginConfigurer -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
//                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
//                        oAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuthService))
//                );
//                return http.build();
//    }
//
//}
