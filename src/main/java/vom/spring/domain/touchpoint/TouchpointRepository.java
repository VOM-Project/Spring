package vom.spring.domain.touchpoint;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vom.spring.domain.album.Album;

import java.util.List;

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

    @Query("select t from Touchpoint t where t.to_member_id = :member_id")
    List<Touchpoint> findByMember(@Param("member_id") Long id);

}
