package vom.spring.domain.touchpoint;

import jakarta.persistence.*;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.member.domain.Member;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Touchpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member member;
}
