package vom.spring.domain.vomvom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.vomvom.domain.Vomvom;

import java.util.List;
import java.util.Optional;

public interface VomvomRepository extends JpaRepository<Vomvom,Long> {
    List<Vomvom> findByToMember(Member toMember);
    Optional<Vomvom> findByFromMemberAndToMember(Member fromMember, Member toMember);
    List<Vomvom> findByFromMemberOrToMember(Member member1, Member member2);
}
