package vom.spring.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Member> findById(Long id);
}
