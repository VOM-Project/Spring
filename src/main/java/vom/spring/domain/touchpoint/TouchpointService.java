package vom.spring.domain.touchpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TouchpointService {
    protected final TouchpointRepository touchpointRepository;

    @Transactional
    public List<Touchpoint> getTouchpoints(Long member_id) {
        return touchpointRepository.findByMember(member_id);
    }

    @Transactional
    public void sendTouchpoint(Long from_member_id, Long to_member_id) {
        touchpointRepository.save(
                Touchpoint.builder()
                        .createdAt(LocalDateTime.now())
                        .from_member(from_member_id)
                        .to_member(to_member_id)
                        .build()
        );
    }

}
