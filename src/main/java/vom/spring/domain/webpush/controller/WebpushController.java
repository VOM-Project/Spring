package vom.spring.domain.webpush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vom.spring.domain.webpush.dto.WebpushDto;
import vom.spring.domain.webpush.service.WebpushService;

import java.util.List;

@RestController
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

//    @PostMapping("/api/webpush/send")
//    public String sendNotification(
//            @RequestBody NotificationRequest request)
//    {
//        webpushService.sendNotification(request.getTargetToken(), request.getTitle(), request.getBody());
//        return "Notification sent successfully!";
//    }
//
//    @Getter
//    public static class NotificationRequest {
//        private String targetToken;
//        private String title;
//        private String body;
//
//        // Getters and Setters
//    }

}
