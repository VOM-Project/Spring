package vom.spring.domain.homepy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public class HomepyService {

    private final HomepyRepository homepyRepository;

    @Autowired
    public HomepyService(HomepyRepository homepyRepository) {
        this.homepyRepository = homepyRepository;
    }

    /**
     * 인사말 작성
     */
    @Transactional
    public String writeGreeting(User user, String greeting) {
        Homepy homepy = HomepyRepository.findByUser(user);
        homepy.setGreeting(greeting);
        homepyRepository.save(homepy);

        return greeting;
    }

}
