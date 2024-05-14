package vom.spring.domain.album;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import vom.spring.domain.homepy.Homepy;

@Repository
public class AlbumRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Album album) {
        em.persist(album);
    }

//    public Homepy findByHomepy(Integer homepyId) {
//        return em.find(Homepy.class, homepyId);
//    }
}