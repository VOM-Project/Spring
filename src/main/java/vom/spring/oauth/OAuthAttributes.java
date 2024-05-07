package vom.spring.oauth;

import vom.spring.member.Member;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
//oAuth2User.getAttributes()를 통해 받아온 정보들은 수정할 수 없는 map
//따라서 필요한 정보만을 추출해서 제공하기 위해 만든 클래스
//사용자 정보를 담을 클래스
public enum OAuthAttributes {

    GOOGLE("google", (attributes) ->{
        return Member.of(
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                (String) attributes.get("picture"),
                "google",
                "google_" + attributes.get("sub")
        );
    });

    private final String registrationId;
    private final Function<Map<String, Object>, Member> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, Member> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    //provider가 일치하는 경우에만 apply 호출해서 google member 반환
    //추후 다양한 소셜로그인 추가하면 관리가 필요하기 때문에!! 필요함
    public static Member extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow()
                .of.apply(attributes);
    }
}
