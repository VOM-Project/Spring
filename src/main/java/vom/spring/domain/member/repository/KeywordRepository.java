package vom.spring.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.member.domain.Keyword;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    Optional<Keyword> findByName(String name);
}
