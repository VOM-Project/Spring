package vom.spring.domain.webcam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.webcam.domain.MemberWebcam;
import vom.spring.domain.webcam.domain.Webcam;

import java.util.List;
import java.util.Optional;

public interface MemberWebcamRepository extends JpaRepository<MemberWebcam, Long> {
    void deleteByWebcam(Webcam webcam);
    List<Optional<MemberWebcam>> findByWebcam(Webcam webcam);
}
