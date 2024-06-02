package vom.spring.domain.webpush;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vom.spring.domain.touchpoint.Touchpoint;
import vom.spring.domain.touchpoint.TouchpointDto;

import java.util.List;

@Repository
public class WebpushRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Webpush webpush) {
        em.persist(webpush);
    }

    public List<WebpushDto> findFromMemberIdsByToMemberId(Long toMemberId) {
        return em.createQuery(
                        "select new WebpushDto(w.fromMember.id, w.createdAt) " +
                                "from Webpush w where w.toMember.id = :toMemberId",
                        WebpushDto.class)
                .setParameter("toMemberId", toMemberId)
                .getResultList();
    }
}
