package vom.spring.domain.touchpoint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vom.spring.domain.album.Album;

@Repository
public class TouchpointRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Touchpoint touchpoint) {
        em.persist(touchpoint);
    }

    public Touchpoint findById(Long id) {
        return em.find(Touchpoint.class, id);
    }
}
