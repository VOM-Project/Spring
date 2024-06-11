package vom.spring.domain.webcam.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vom.spring.domain.webcam.domain.Message;
import vom.spring.domain.webcam.dto.WebcamRequestDto;
import vom.spring.domain.webcam.dto.WebcamResponseDto;
import vom.spring.domain.webcam.service.WebcamServcie;
import vom.spring.domain.webpush.service.FcmService;

import java.io.IOException;

@Tag(name = "화상채팅(시그널링) API", description = "유저 API 명세서")
@RestController
@RequiredArgsConstructor
@Slf4j
//db에 webcam 업데이트 로직 필요
public class WebcamController {
    private final WebcamServcie webcamServcie;
    private final SimpMessagingTemplate messagingTemplate;
    private final FcmService fcmService;
    /**
     * 방 생성
     */
    @Operation(summary = "화상채팅 방을 생성합니다", description = "원하는 memberId와 화상채팅 방을 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/api/webcam")
    public ResponseEntity<WebcamResponseDto.CreateWebcamDto> createWebcamRoom(@RequestBody WebcamRequestDto.CreateWebcamDto request) throws IOException {
        WebcamResponseDto.CreateWebcamDto response = webcamServcie.createWebcamRoom(request);
        fcmService.sendMessageTo(request.getToMemberId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * offer 정보를 주고받기 - step 5에서 offer를 받고 구독하고 있는 client들에게 전송
     */
    @MessageMapping("/peer/offer/{webcamId}") //해당 경로로 메시지가 날아오면 해당 메서드 실행해서 리턴, /app/~~이런식으로 전달된다
    //camKey : 각 요청하는 캠의 key , roomId : 룸 아이디 =>룸 id를 webcam id로 수정
    public void PeerHandleOffer(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        log.info("offer받은 webcamId:"+ message.getWebcamId()+"offer받은 sender:"+ message.getSender());
        messagingTemplate.convertAndSend("/topic/peer/offer/" +message.getWebcamId(), message);
    }

    /**
     * iceCandidate 정보를 주고 받기 위한 websocket
     */
    @MessageMapping("/peer/iceCandidate/{webcamId}")
    public void PeerHandleIceCandidate(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        log.info("ice받은 webcamId:"+ message.getWebcamId()+"ice받은 sender:"+ message.getSender());
        messagingTemplate.convertAndSend("/topic/peer/iceCandidate/" + message.getWebcamId(), message);
    }

    /**
     * answer 정보 주고받기
     */
    @MessageMapping("/peer/answer/{webcamId}")
    public void PeerHandleAnswer(@Payload Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor ) {
        log.info("answer받은 webcamId:"+ message.getWebcamId()+"answer받은 sender:"+ message.getSender());
        messagingTemplate.convertAndSend("/topic/peer/answer/" + message.getWebcamId(), message);
    }

//    /**
//     * camKey 를 받기위해 신호를 보내는 webSocket
//     */
//    @Operation(summary = "camKey를 받기 위해 신호를 보냄", description = "client의 camKey 정보를 주고 받기 위해 신호를 보냅니다",
//            responses = {
//                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
//                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            })
//    @MessageMapping("/call/key")
//    @SendTo("/topic/call/key")
//    public String callKey(@Payload String message) {
//        log.info("[Key] : {}", message);
//        return message;
//    }
//
//    /**
//     * 자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
//     */
//    @Operation(summary = "camKey를 모든 연결된 세션에 보냄", description = "client의 camKey를 연결된 모든 peer에게 보냅니다",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "계정 생성 완료"),
//                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            })
//    @MessageMapping("/send/key")
//    @SendTo("/topic/send/key")
//    public String sendKey(@Payload String message) {
//        return message;
//    }
}
