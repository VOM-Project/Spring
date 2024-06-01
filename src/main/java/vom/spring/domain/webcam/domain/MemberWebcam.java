package vom.spring.domain.webcam.domain;

import jakarta.persistence.*;
import lombok.*;
import vom.spring.domain.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberWebcam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //list로 해야하나,,

    @JoinColumn(name = "webcam_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Webcam webcam;
}
