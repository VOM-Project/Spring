package vom.spring.domain.touchpoint;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.member.domain.Member;

import java.time.LocalDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Touchpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "from_member_id", nullable = false)
    private Member fromMember;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "to_member_id", nullable = false)
    private Member toMember;
}
