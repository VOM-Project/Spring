package vom.spring.domain.album;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import vom.spring.domain.homepy.Homepy;

import java.util.List;

@Repository
public class AlbumRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Album album) {
        em.persist(album);
    }

    public Album findById(Long id) {
        return em.find(Album.class, id);
    }

    public List<Album> findByHomepy_id(Long homepy_id) {
        return em.createQuery("select a from Album a where a.homepy.id = :homepy_id and a.deletedAt is null", Album.class)
                .setParameter("homepy_id", homepy_id)
                .getResultList();
    }

//    public Homepy findByHomepy(Integer homepyId) {
//        return em.find(Homepy.class, homepyId);
//    }
}