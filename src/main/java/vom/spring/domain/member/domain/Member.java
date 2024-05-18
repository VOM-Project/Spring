package vom.spring.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본 생성자 - protected면 같은 패키지 또는 자식클래스에서 사용 가능
@AllArgsConstructor(access = AccessLevel.PRIVATE) //모든 필드의 생성자 - private은 내부 클래스에서만 사용 가능
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birth;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
    private String profileImgUrl;
    private Integer vomVomCount; //수정 필요

    public void updateNicknameAndEmailAndProfileImg(String nickname, String email, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
    }
    public Member(String email) {
        this.email = email;
    }

    public void joinMember(String nickname, /*String profileImgUrl,*/ LocalDate birth , Region region) {
        this.nickname = nickname;
//        this.profileImgUrl = profileImgUrl;
        this.birth = birth;
        this.region = region;
    }
}
