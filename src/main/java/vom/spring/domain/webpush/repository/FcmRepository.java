package vom.spring.domain.webpush.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vom.spring.domain.webpush.domain.Fcm;

@Repository
public class FcmRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Fcm fcm) {
        em.persist(fcm);
    }

    public Fcm findByMember_id(Long member_id) {
        try {
            return em.createQuery("SELECT f FROM Fcm f WHERE f.member.id = :member_id", Fcm.class)
                    .setParameter("member_id", member_id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
