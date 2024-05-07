package vom.spring.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import vom.spring.member.Member;
import vom.spring.member.MemberRepository;

//로그인한 유저 반환함 => 추후 api 구현시 로그인된 유저 정보 갖고 올때 필요
@Component
@RequiredArgsConstructor
public class OAuthLoginMemberUtil {
    private final MemberRepository memberRepository;
    //현재 인증된 사용자의 oauth2authenticationtoken을 가져와 사용자의 이메일 추출
    //email을 통해 db에서 해당 member 반환
    public Member getGoogleLoginMember() {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String email = authenticationToken.getPrincipal().getAttributes().get("email").toString();
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다"));
    }
}
