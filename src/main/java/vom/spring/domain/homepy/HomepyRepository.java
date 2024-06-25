package vom.spring.domain.homepy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vom.spring.domain.member.domain.Member;

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
        return em.find(Homepy.class, member_id);
    }
}
