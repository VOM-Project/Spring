package vom.spring.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    private Integer age;
    private String region;
    private String profileImgUrl;
    private Integer vomVomCount;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String provider; //google인지..
    private String providerId; //소셜 로그인 성공시 부여되는 고유한 id
//    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(String nickname, String email, String profileImgUrl, String provider, String providerId) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.role = Role.USER;
        this.provider = provider;
        this.providerId = providerId;
    }
    public Member updateNicknameAndEmailAndProfileImg(String nickname, String email, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        return this;
    }

    public static Member of(String nickname, String email, String profileImgUrl, String provider, String providerId) {
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .profileImgUrl(profileImgUrl)
                .provider(provider)
                .providerId(providerId)
                .build();
    }

    public Member updateProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getMemberRoleValue() {
        return this.role.getValue();
    }
}
