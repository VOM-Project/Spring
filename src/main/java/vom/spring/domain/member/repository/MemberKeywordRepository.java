package vom.spring.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.member.domain.MemberKeyword;

public interface MemberKeywordRepository extends JpaRepository<MemberKeyword, Long> {

}
