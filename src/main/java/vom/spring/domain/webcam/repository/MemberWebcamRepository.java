package vom.spring.domain.webcam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vom.spring.domain.webcam.domain.MemberWebcam;

public interface MemberWebcamRepository extends JpaRepository<MemberWebcam, Long> {
    
}
