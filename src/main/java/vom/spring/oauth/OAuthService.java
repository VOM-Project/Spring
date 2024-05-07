package vom.spring.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import vom.spring.member.Member;
import vom.spring.member.MemberRepository;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
//OAuth2UserService 타입 상속 받음 => oauth 로그인 성공시 db에 저장하는 oauthservice 생성
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    @Override
    //유저를 불러오면 판단해야 하기 때문에 loadUser 메서드 재정의
    //로그인 인증이 실패 되어도 동작하는 서비스의 지장을 주면 안되기 때문에 로그인 실패 관련 Exception throw
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService(); //사용자 정보를 가져와야하기 때문에 대리자 생성
        OAuth2User oAuth2User = delegate.loadUser(userRequest);//oauth 서비스에서 가져온 유저 정보를 담고있음

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); //oauth 서비스 이름
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //oauth2 로그인 진행시 키가 되는 필드값(pk)
        Map<String, Object> attributes = oAuth2User.getAttributes();//oauth 서비스의 유저 정보들

        Member member = OAuthAttributes.extract(registrationId, attributes); //registration id에 따라 유저 정보를 통해 공통된 member 객체를 만듦
        member.updateProvider(registrationId);
        member = saveOrUpdate(member);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, member, registrationId);

        //로그인 유저 리턴
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getMemberRoleValue())),
                customAttribute,
                userNameAttributeName);
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, Member member, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("nickname", member.getNickname());
        customAttribute.put("email", member.getEmail());
        customAttribute.put("picture", member.getProfileImgUrl());
        return customAttribute;
    }

    private Member saveOrUpdate(Member member) {
       Member newMember = memberRepository.findByEmailAndProvider(member.getEmail(), member.getProvider())
                .map(m -> m.updateNicknameAndEmailAndProfileImg(member.getNickname(), member.getEmail(), member.getProfileImgUrl()))
                .orElse(Member.of(member.getNickname(),member.getEmail(),member.getProfileImgUrl(),
                        member.getProvider(), member.getProviderId()));
        return memberRepository.save(newMember);
    }
}
