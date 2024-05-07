package vom.spring.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    //이미 생성된 사용자인지 확인하기 위한 용도
    Optional<Member> findByEmailAndProvider(String email, String provider);

    Optional<Member> findByEmail(String email);
}
