package vom.spring.domain.webpush.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.webpush.dto.FcmMessageDto;
import vom.spring.domain.webpush.domain.Fcm;
import vom.spring.domain.webpush.repository.FcmRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FcmService {
    private final FcmRepository fcmRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public FcmService(FcmRepository fcmRepository, MemberRepository memberRepository) {
        this.fcmRepository = fcmRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Fcm Token 저장
     */
    @Transactional
    public void setFcmToken(String fcmToken, Long member_id) {

        Member member = memberRepository.findById(member_id).get();
        Fcm fcm = fcmRepository.findByMember_id(member_id);

        if (fcm == null) {
            fcmRepository.save(
                    Fcm.builder()
                            .createdAt(LocalDateTime.now())
                            .fcmToken(fcmToken)
                            .member(member)
                            .build()
            );
        } else {
            fcm.setFcmToken(fcmToken);
        }
    }

    /**
     * 푸시 메시지 전송
     *
     * @return 성공(1), 실패(0)
     */
    public int sendMessageTo(Long memberId) throws IOException {
        try {
            Fcm fcm = fcmRepository.findByMember_id(memberId);

            if (fcm == null) {
                throw new NoSuchElementException("해당 멤버가 알림 설정을 하지 않았습니다.");
            }

            String message = makeMessage(fcm);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getAccessToken());

            HttpEntity entity = new HttpEntity<>(message, headers);

            String API_URL = "https://fcm.googleapis.com/v1/projects/vomvom-fd09b/messages:send";

            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
            System.out.println("Response: " + response.getBody());
            System.out.println("Status Code: " + response.getStatusCode());
            return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
        } catch (Exception e) {
            System.err.println("[-] FCM 전송 오류 :: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰 발급
     *
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {

        String firebaseConfigPath = "firebase/vomvom-fd09b-firebase-adminsdk-ghtjs-0070b39a4e.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform",
                        "https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();

        System.out.println(googleCredentials.getAccessToken());

        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 내용 생성
     *
     * @return String
     */
    private String makeMessage(Fcm fcm) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();

        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(fcm.getFcmToken())
                        .notification(FcmMessageDto.Notification.builder()
                                .title("VOM")
                                .body("화상 채팅 요청 알림  클릭하여 접속!")
                                .image("https://cdn-icons-png.flaticon.com/512/1762/1762755.png")
                                .build()
                        ).build()).build();

        return om.writeValueAsString(fcmMessageDto);
    }
}