package vom.spring.domain.vomvom.domain;

import jakarta.persistence.*;
import lombok.*;
import vom.spring.domain.member.domain.Member;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vomvom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private  Member toMember;

    private boolean isVomvom;

    public void updateIsVomvom() {
        this.isVomvom = true;
    }
}
