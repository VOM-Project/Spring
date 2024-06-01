package vom.spring.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import vom.spring.domain.touchpoint.Touchpoint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본 생성자 - protected면 같은 패키지 또는 자식클래스에서 사용 가능
@AllArgsConstructor(access = AccessLevel.PRIVATE) //모든 필드의 생성자 - private은 내부 클래스에서만 사용 가능
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
    private String profileImgName;
    private String profileImgUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Integer vomVomCount; //수정 필요

    public void updateNicknameAndEmailAndProfileImg(String nickname, String email, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }
    public Member(String email) {
        this.email = email;
    }

    public void joinMember(String nickname, LocalDate birth , Region region) {
        this.nickname = nickname;
        this.birth = birth;
        this.region = region;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImg(String profileImgName, String profileImgUrl) {
        this.profileImgName = profileImgName;
        this.profileImgUrl = profileImgUrl;
    }
}
