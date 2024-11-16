package vom.spring.domain.webpush.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vom.spring.domain.webpush.dto.ApiResponseWrapper;
import vom.spring.domain.webpush.service.FcmService;
import vom.spring.domain.webpush.domain.SuccessCode;

import java.io.IOException;

@Slf4j
@Controller
public class FcmController {
    private FcmService fcmService;
    @Autowired
    public FcmController(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping(value = "/api/fcm/{member-id}")
    public ResponseEntity<HttpStatus> setFcmToken(
            @PathVariable("member-id") Long member_id,
            @RequestParam String fcmToken
    ) {
        fcmService.setFcmToken(fcmToken, member_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 웹 푸시 서비스 테스트용
    @PostMapping("/api/v2/fcm/send/{member_id}")
    public ResponseEntity<ApiResponseWrapper<Object>> pushMessage(
            @PathVariable("member_id") Long memberId
    ) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다. ");

        int result = fcmService.sendMessageTo(memberId);

        ApiResponseWrapper<Object> arw = ApiResponseWrapper
                .builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(arw, HttpStatus.OK);
    }

    // 웹 푸시 메세지 내용 테스트용
    @PostMapping("/api/v3/fcm/send/{member_id}")
    public ResponseEntity<ApiResponseWrapper<Object>> sendPushMessage(
            @PathVariable("member_id") Long memberId
    ) throws IOException {
        log.debug("[+] 푸시 메시지를 전송합니다. ");

        int result = fcmService.sendMessageTo(memberId);

        ApiResponseWrapper<Object> arw = ApiResponseWrapper
                .builder()
                .result(result)
                .resultCode(SuccessCode.SELECT_SUCCESS.getStatus())
                .resultMsg(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();

        return new ResponseEntity<>(arw, HttpStatus.OK);
    }
}
