package vom.spring.domain.webpush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vom.spring.domain.touchpoint.TouchpointDto;
import vom.spring.domain.touchpoint.TouchpointService;

import java.util.List;

@Controller
public class WebpushController {
    private WebpushService webpushService;
    @Autowired
    public WebpushController(WebpushService webpushService) {
        this.webpushService = webpushService;
    }

    @GetMapping(value = "/api/webpush/{member-id}")
    public ResponseEntity<List<WebpushDto>> getWebpushes(
            @PathVariable("member-id") Long memberId
    ) {
        return new ResponseEntity<>(webpushService.getWebpushes(memberId), HttpStatus.OK);
    }

}
