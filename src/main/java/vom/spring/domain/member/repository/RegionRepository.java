package vom.spring.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.member.domain.Region;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByName(String name);
}
