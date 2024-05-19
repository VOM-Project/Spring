package vom.spring.domain.touchpoint;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.member.domain.Member;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Touchpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member from_member;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member to_member;
}
