package vom.spring.domain.webpush.service;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.webcam.domain.Webcam;
import vom.spring.domain.webpush.domain.Webpush;
import vom.spring.domain.webpush.dto.WebpushDto;
import vom.spring.domain.webpush.repository.WebpushRepository;

import java.util.List;

@Service
public class WebpushService {

    private final WebpushRepository webpushRepository;
    private final MemberRepository memberRepository;
//    private final WebcamRepository webcamRepository;

    @Autowired
    private FcmService fcmService;

    @Autowired
    public WebpushService(WebpushRepository webpushRepository, MemberRepository memberRepository) {
        this.webpushRepository = webpushRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 웹푸쉬 조회
     */
    public List<WebpushDto> getWebpushes(Long toMemberId) {
        return webpushRepository.findByToMemberId(toMemberId);
    }

    public void createWebpush(Member fromMember, Member toMember, Webcam webcam) {
        System.out.println("Enter createWebpush");
        webpushRepository.save(
                Webpush.builder()
                        .createdAt(LocalDateTime.now())
                        .fromMember(fromMember)
                        .toMember(toMember)
                        .webcam(webcam)
                        .build());
        try {
            fcmService.sendMessageTo(toMember.getId());
        } catch (IOException e) {
            System.err.println("FCM 메시지 전송 중 오류 발생: " + e.getMessage());
        }
        System.out.println("Finish createWebpush");
    }

    public void deleteWebpushByWebcamId(Long webcamId) {
        Webpush webpush = webpushRepository.findByWebcamId(webcamId);
        if (webpush != null) {
            webpushRepository.delete(webpush);
        }
    }

    /**
     * FCM
     */
//    private static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
//    private static final String SERVER_KEY = ""; // Firebase Console에서 확인한 서버 키
//
//    public void sendNotification(String targetToken, String title, String body) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "key=" + SERVER_KEY);
//        headers.set("Content-Type", "application/json");
//
//        Map<String, Object> notification = new HashMap<>();
//        notification.put("title", title);
//        notification.put("body", body);
//
//        Map<String, Object> message = new HashMap<>();
//        message.put("to", targetToken);
//        message.put("notification", notification);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(message, headers);

//        ResponseEntity<String> response = restTemplate.exchange(FCM_API_URL, HttpMethod.POST, entity, String.class);
//        System.out.println("FCM Response: " + response.getBody());

//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(FCM_API_URL, entity, String.class);
//            System.out.println("Response: " + response.getBody());
//        } catch (HttpClientErrorException e) {
//            System.err.println("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
//        }
//    }


}
