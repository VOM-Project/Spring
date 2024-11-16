package vom.spring.domain.webpush.service;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private FcmService fcmService;

    @Autowired
    public WebpushService(WebpushRepository webpushRepository, MemberRepository memberRepository) {
        this.webpushRepository = webpushRepository;
    }

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
}
