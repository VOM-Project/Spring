package vom.spring.domain.webpush.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import vom.spring.domain.webcam.domain.MemberWebcam;
import vom.spring.domain.webcam.domain.Webcam;
import vom.spring.domain.webpush.domain.Webpush;
import vom.spring.domain.webpush.dto.WebpushDto;

import java.util.List;

@Repository
public class WebpushRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Webpush webpush) {
        em.persist(webpush);
    }

    public List<WebpushDto> findByToMemberId(Long toMemberId) {
        return em.createQuery(
                        "select new WebpushDto(w.fromMember.id, w.createdAt, w.webcam.id) " +
                                "from Webpush w where w.toMember.id = :toMemberId",
                        WebpushDto.class)
                .setParameter("toMemberId", toMemberId)
                .getResultList();
    }

    public Webpush findByWebcamId(Long webcamId) {
        return em.createQuery("SELECT w FROM Webpush w WHERE w.webcam.id = :webcamId", Webpush.class)
                .setParameter("webcamId", webcamId)
                .getSingleResult();
    }

    public void delete(Webpush webpush) {
        if (em.contains(webpush)) {
            em.remove(webpush);
        } else {
            Webpush mergedWebpush = em.merge(webpush); // 비영속 상태라면 병합 후 삭제
            em.remove(mergedWebpush);
        }
    }
}
