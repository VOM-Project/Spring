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
import org.springframework.web.bind.annotation.*;
import vom.spring.domain.webcam.domain.Message;
import vom.spring.domain.webcam.dto.WebcamRequestDto;
import vom.spring.domain.webcam.dto.WebcamResponseDto;
import vom.spring.domain.webcam.service.WebcamServcie;
import vom.spring.domain.webpush.service.FcmService;

import java.io.IOException;

@Tag(name = "화상채팅(시그널링) API", description = "화상채팅 API 명세서")
@RestController
@RequiredArgsConstructor
@Slf4j
public class WebcamController {
    private final WebcamServcie webcamServcie;
    private final FcmService fcmService;
//    private final SimpMessagingTemplate messagingTemplate;
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
     * 방 입장 후 상대방 memberId 받기
     */
    @Operation(summary = "화상채팅 상대방 정보를 조회합니다", description = "상대방의 memberId를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/api/webcam/{webcamId}/remote")
    public ResponseEntity<WebcamResponseDto.GetRemoteMemberDto> getRemoteMember(@PathVariable(value = "webcamId") Long webcamId) {
        WebcamResponseDto.GetRemoteMemberDto response = webcamServcie.getRemoteMemberId(webcamId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * offer 정보를 주고받기 - step 5에서 offer를 받고 구독하고 있는 client들에게 전송
     */
    @MessageMapping("/peer/offer/{webcamId}") //해당 경로로 메시지가 날아오면 해당 메서드 실행해서 리턴, /app/~~이런식으로 전달된다
    @SendTo("/topic/peer/offer/{webcamId}")
    public Message PeerHandleOffer(Message message, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("offer 메세지 왔다, sender: {}, 전달할 webcamId는: {}", message.getSender(),message.getWebcamId());
//        messagingTemplate.convertAndSend("/topic/peer/offer/" +message.getWebcamId(), message);
        return message;
    }

    /**
     * iceCandidate 정보를 주고 받기 위한 websocket
     */
    @MessageMapping("/peer/iceCandidate/{webcamId}")
    @SendTo("/topic/peer/iceCandidate/{webcamId}")
    public Message PeerHandleIceCandidate(Message message, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("[ICECANDIDATE] sender: {}, candidate 정보: {}, 전달할 webcamId: {}", message.getSender(), message.getIce(), message.getWebcamId());
//        messagingTemplate.convertAndSend("/topic/peer/iceCandidate/" + message.getWebcamId(), message);
        return message;
    }

    /**
     * answer 정보 주고받기
     */
    @MessageMapping("/peer/answer/{webcamId}")
    @SendTo("/topic/peer/answer/{webcamId}")
    public Message PeerHandleAnswer(Message message, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("[ANSWER] sender: {}, 전달할 곳 : {} ", message.getSender(), message.getAnswer());
//        messagingTemplate.convertAndSend("/topic/peer/answer/" + message.getWebcamId(), message);
        return message;
    }

    /**
     * leave 정보 주고받기
     */
    @MessageMapping("/peer/leaveRoom/{webcamId}")
    @SendTo("/topic/peer/leaveRoom/{webcamId}")
    public Message PeerHandleLeave(Message message, @DestinationVariable(value = "webcamId") String webcamId) {
        log.info("[LEAVE] sender: {}, 전달할 곳 : {} ", message.getSender(), message.getWebcamId());
//        messagingTemplate.convertAndSend("/topic/peer/answer/" + message.getWebcamId(), message);
        return message;
    }

    /**
     * 방 삭제
     */
    @Operation(summary = "화상채팅 방을 삭제합니다", description = "화상채팅 방을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 삭제했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 삭제하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "채팅 방을 찾지 못했습니다",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/api/webcam")
    public ResponseEntity<Void> deleteWebcamRoom(@RequestBody WebcamRequestDto.DeleteWebcamDto request) {
        System.out.print("방id: " + request.getRoomId());
        webcamServcie.deleteWebcamRoom(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
