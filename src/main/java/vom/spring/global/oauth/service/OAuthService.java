package vom.spring.global.oauth.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import vom.spring.global.jwt.JwtTokenProvider;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.global.oauth.dto.LoginRequestDto;
import vom.spring.global.oauth.dto.LoginResponseDto;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponseDto.GetLoginDto socialLogin(String code, String registrationId) {
        //로그인 시도
        //해당 이메일로 유저 조회
        //이미 있는 유저이면 true담아서 보내고
        //아니면 false 담아 보낸 후 회원가입 시도
//        System.out.println("인가code = " + code);
//        System.out.println("registrationId = " + registrationId);
        String accessToken = getAccessToken(code, registrationId);
//        System.out.println("accessToken = " +accessToken);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
//        System.out.println("userResourceNode = " + userResourceNode);
        //유저정보 출력
        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        boolean isRegistered = memberRepository.existsByEmail(email);
        //회원가입이 되어있지 않은경우
        if (!isRegistered) {
            Member newMember = new Member(email);
            return LoginResponseDto.GetLoginDto.builder()
                    .isRegistered(false)
                    .memberId(newMember.getId())
                    .build();
        }
        //회원가입이 되어있는 경우
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Not Exist User"));
        return LoginResponseDto.GetLoginDto.builder()
                .isRegistered(true)
                .memberId(member.getId())
                .build();
//        System.out.println("id = " + id);
//        System.out.println("email = " + email);
//        System.out.println("nickname = " + nickname);
    }

    @Transactional
    public LoginResponseDto.GetLoginDto front_socialLogin(LoginRequestDto.LoginCodeDto request, String registrationId) {
        //로그인 시도
        //해당 이메일로 유저 조회
        //이미 있는 유저이면 true담아서 보내고
        //아니면 false 담아 보낸 후 회원가입 시도
//        System.out.println("인가code = " + code);
//        System.out.println("registrationId = " + registrationId);
        String accessToken = getAccessToken(request.getAuth_code(), registrationId);
//        System.out.println("accessToken = " +accessToken);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
//        System.out.println("userResourceNode = " + userResourceNode);
        //유저정보 출력
        String id = userResourceNode.get("id").asText();
        String email = userResourceNode.get("email").asText();
        String nickname = userResourceNode.get("name").asText();
        boolean isRegistered = memberRepository.existsByEmail(email);
        //회원가입이 되어있지 않은경우
        if (!isRegistered) {
            Member newMember = new Member(email);
            String token = issueToken(newMember);
            return LoginResponseDto.GetLoginDto.builder()
                    .isRegistered(false)
                    .memberId(newMember.getId())
                    .accessToken(token)
                    .build();
        }
        //회원가입이 되어있는 경우
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Not Exist User"));
        String token = issueToken(member);
        return LoginResponseDto.GetLoginDto.builder()
                .isRegistered(true)
                .memberId(member.getId())
                .accessToken(token)
                .build();
//        System.out.println("id = " + id);
//        System.out.println("email = " + email);
//        System.out.println("nickname = " + nickname);
    }

    //google access token 발급
    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = responseNode.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    //유저정보 받기
    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2." + registrationId + ".resource-uri");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }

    /**
     * vom access token 발급
     */
    private String issueToken(Member member) {
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getEmail());
        return accessToken;
    }
    /**
     * access token 갱신 추가
     */

    /**
     * 사용자 탈퇴 추가
     */

    /**
     * 임시 로그인
     */
    public LoginResponseDto.TempLoginDto tempLogin(LoginRequestDto.TempLoginDto request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 member입니다"));
        String accessToken = issueToken(member);
        return LoginResponseDto.TempLoginDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
