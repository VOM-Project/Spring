package vom.spring.domain.homepy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomepyController {
    private HomepyService homepyService;
    @Autowired
    public HomepyController(HomepyService homepyService) {
        this.homepyService = homepyService;
    }

    // 프로필 조회
    @GetMapping(value = "/api/homepy/{member-id}/profile")
    public ResponseEntity<?> getProfile(
            @PathVariable("member-id") Long memberId
    ) {
        return new ResponseEntity<>(homepyService.getProfile(memberId), HttpStatus.OK);
    }

    // 인사말 조회
    @GetMapping(value = "/api/homepy/{member-id}/greeting")
    public ResponseEntity<?> getGreeting(
            @PathVariable("member-id") Long memberId
    ) {
        return new ResponseEntity<>(homepyService.getGreeting(memberId), HttpStatus.OK);
    }

    // 인사말 변경
    @PostMapping(value = "/api/homepy/{member-id}/greeting")
    public ResponseEntity<HttpStatus> setGreeting(
            @PathVariable("member-id") Long memberId,
            @RequestParam String greeting
    ) {
        homepyService.setGreeting(memberId, greeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
