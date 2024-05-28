package vom.spring.domain.touchpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vom.spring.domain.homepy.HomepyService;

import java.util.List;

@Controller
public class TouchpointController {

    private TouchpointService touchpointService;
    @Autowired
    public TouchpointController(TouchpointService touchpointService) {
        this.touchpointService = touchpointService;
    }

    @GetMapping(value = "/api/touchpoint/{member-id}")
    public ResponseEntity<List<TouchpointDto>> getTouchpoints(
            @PathVariable("member-id") Long memberId
    ) {
        return new ResponseEntity<>(touchpointService.getTouchpoints(memberId), HttpStatus.OK);
    }

    @PostMapping(value = "/api/touchpoint/{to-member-id}")
    public ResponseEntity<HttpStatus> sendTouchpoint(
            @PathVariable("to-member-id") Long to_member_id,
            @RequestParam Long from_member_id
    ) {
        touchpointService.sendTouchpoint(from_member_id, to_member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
