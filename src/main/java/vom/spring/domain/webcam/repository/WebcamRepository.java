package vom.spring.domain.webcam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.webcam.domain.Webcam;

public interface WebcamRepository extends JpaRepository<Webcam, Long> {
}
