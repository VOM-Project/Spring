package vom.spring.domain.webpush;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
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


        System.out.println(fcmToken);
        System.out.println(member_id);
        fcmRepository.save(
                Fcm.builder()
                        .createdAt(LocalDateTime.now())
                        .fcmToken(fcmToken)
                        .member(member)
                        .build()
        );
    }

    /**
     * 푸시 메시지 처리를 수행하는 비즈니스 로직
     *
//     * @param fcmSendDto 모바일에서 전달받은 Object
     * @return 성공(1), 실패(0)
     */
//    public int sendMessageTo(FcmSendDto fcmSendDto) throws IOException {
    public int sendMessageTo(Long memberId) throws IOException {

        System.out.println("1111111111");

//        String message = makeMessage(fcmSendDto);
        String message = makeMessage(memberId);
        System.out.println("22222222");
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity entity = new HttpEntity<>(message, headers);

        String API_URL = "https://fcm.googleapis.com/v1/projects/vomvom-fd09b/messages:send";
//        String API_URL = "https://fcm.googleapis.com/fcm/send";
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
            System.out.println(response.getStatusCode());
            return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
        } catch (Exception e) {
            log.error("[-] FCM 전송 오류 :: " + e.getMessage());
//            log.error("[-] 오류 발생 토큰 :: [" + fcmSendDto.getToken() + "]");
//            log.error("[-] 오류 발생 메시지 :: [" + fcmSendDto.getBody() + "]");
            return 0;
        }
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
     *
     * playground에서 발급받은 토큰
     *
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {

        System.out.println("woooooooooooooooowwwww");
        String firebaseConfigPath = "firebase/vomvom-fd09b-firebase-adminsdk-ghtjs-0070b39a4e.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/firebase.messaging"));

        googleCredentials.refreshIfExpired();

        System.out.println(googleCredentials.getAccessToken());

        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
     *
//     * @param fcmSendDto FcmSendDto
     * @return String
     */
//    private String makeMessage(FcmSendDto fcmSendDto) throws JsonProcessingException {
    private String makeMessage(Long memberId) throws JsonProcessingException {

        ObjectMapper om = new ObjectMapper();
        Fcm fcm = fcmRepository.findByMember_id(memberId);

        System.out.println(fcm.getId());

        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(fcm.getFcmToken())
                        .notification(FcmMessageDto.Notification.builder()
                                .title("테스트")
                                .body("테스트입니다용")
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return om.writeValueAsString(fcmMessageDto);
    }
}
