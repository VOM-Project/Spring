package vom.spring.domain.webcam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.webcam.domain.Webcam;

import java.util.Optional;

public interface WebcamRepository extends JpaRepository<Webcam, Long> {

    Optional<Webcam> findById(Long webcamId);

    void deleteById(Long webcamId);
}
