package vom.spring.domain.homepy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HomepyService {

    private final HomepyRepository homepyRepository;

    @Autowired
    public HomepyService(HomepyRepository homepyRepository) {
        this.homepyRepository = homepyRepository;
    }

    /**
     * 인사말 조회
     */
    @Transactional
    public String getGreeting(Long homepyId) {
        Homepy homepy = homepyRepository.findById(homepyId);
        return homepy.getGreeting();
    }

    /**
     * 인사말 변경
     */
    @Transactional
    public void setGreeting(Long homepyId, String greeting) {
        Homepy homepy = homepyRepository.findById(homepyId);
        homepy.setGreeting(greeting);
    }

}
