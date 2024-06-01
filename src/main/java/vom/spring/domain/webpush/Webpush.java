package vom.spring.domain.webpush;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.webcam.domain.Status;
import vom.spring.domain.webcam.domain.Webcam;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Webpush {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "from_member_id", nullable = false)
    private Member fromMember;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "to_member_id", nullable = false)
    private Member toMember;

//    @OneToOne
//    @JoinColumn(referencedColumnName = "id", name = "webcam_id", nullable = false)
//    private Webcam webcam;
}
