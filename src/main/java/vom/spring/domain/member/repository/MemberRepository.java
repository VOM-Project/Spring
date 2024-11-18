package vom.spring.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vom.spring.domain.member.domain.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<Member> findById(Long id);

    // ID로 닉네임 조회
    @Query("SELECT m.nickname FROM Member m WHERE m.id = :id")
    String findNicknameById(@Param("id") Long id);
}
