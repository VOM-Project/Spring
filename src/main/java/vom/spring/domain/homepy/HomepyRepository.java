package vom.spring.domain.homepy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vom.spring.domain.webpush.domain.Fcm;

@Repository
public class HomepyRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Homepy homepy) {
        em.persist(homepy);
    }

    public Homepy findById(Long homepyId) {
        return em.find(Homepy.class, homepyId);
    }

    public Homepy findByMember_id(Long member_id) {
        try {
            return em.createQuery("SELECT h FROM Homepy h WHERE h.member.id = :member_id", Homepy.class)
                    .setParameter("member_id", member_id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
