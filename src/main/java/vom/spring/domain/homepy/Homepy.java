package vom.spring.domain.homepy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vom.spring.domain.member.domain.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Homepy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String greeting;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
