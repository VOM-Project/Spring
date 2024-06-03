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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vom.spring.domain.webcam.dto.WebcamRequestDto;
import vom.spring.domain.webcam.dto.WebcamResponseDto;
import vom.spring.domain.webcam.service.WebcamServcie;

@Tag(name = "화상채팅(시그널링) API", description = "유저 API 명세서")
@RestController
@RequiredArgsConstructor
@Slf4j
//db에 webcam 업데이트 로직 필요
public class WebcamController {
    private final WebcamServcie webcamServcie;
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
    public ResponseEntity<WebcamResponseDto.CreateWebcamDto> createWebcamRoom(@RequestBody WebcamRequestDto.CreateWebcamDto request) {
        WebcamResponseDto.CreateWebcamDto response = webcamServcie.createWebcamRoom(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * offer 정보를 주고받기
     */
    @Operation(summary = "offer 정보 주고받기", description = "clietn(peer)와 offer 정보를 주고 받습니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @MessageMapping("/peer/offer/{camKey}/{roomId}")
    @SendTo("/topic/peer/offer/{camKey}/{roomId}") //camKey : 각 요청하는 캠의 key , roomId : 룸 아이디 =>룸 id를 webcam id로 수정
    public String PeerHandleOffer(@Payload String offer, @DestinationVariable(value = "roomId") String roomId,
                                  @DestinationVariable(value = "camKey") String camKey) {
        log.info("[OFFER] {} : {}", camKey, offer);
        return offer;
    }

    /**
     * iceCandidate 정보를 주고 받기 위한 websocket
     */
    @Operation(summary = "iceCandidate 정보 주고받기", description = "clietn(peer)와 iceCandidate 정보를 주고 받습니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @MessageMapping("/peer/iceCandidate/{camKey}/{roomId}")
    @SendTo("/topic/peer/iceCandidate/{camKey}/{roomId}")
    public String PeerHandleIceCandidate(@Payload String candidate, @DestinationVariable(value = "roomId") String roomId,
                                         @DestinationVariable(value = "camKey") String camKey) {
        log.info("[ICECANDIDATE] {} : {}", camKey, candidate);
        return candidate;
    }

    /**
     * answer 정보 주고받기
     */
    @Operation(summary = "answer 정보 주고받기", description = "clietn(peer)와 answer 정보를 주고 받습니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @MessageMapping("/peer/answer/{camKey}/{roomId}")
    @SendTo("/topic/peer/answer/{camKey}/{roomId}")
    public String PeerHandleAnswer(@Payload String answer, @DestinationVariable(value = "roomId") String roomId,
                                   @DestinationVariable(value = "camKey") String camKey) {
        log.info("[ANSWER] {} : {}", camKey, answer);
        return answer;
    }

    /**
     * camKey 를 받기위해 신호를 보내는 webSocket
     */
    @Operation(summary = "camKey를 받기 위해 신호를 보냄", description = "client의 camKey 정보를 주고 받기 위해 신호를 보냅니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @MessageMapping("/call/key")
    @SendTo("/topic/call/key")
    public String callKey(@Payload String message) {
        log.info("[Key] : {}", message);
        return message;
    }

    /**
     * 자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
     */
    @Operation(summary = "camKey를 모든 연결된 세션에 보냄", description = "client의 camKey를 연결된 모든 peer에게 보냅니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "계정 생성 완료"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않은 직업, 존재하지 않은 주소",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @MessageMapping("/send/key")
    @SendTo("/topic/send/key")
    public String sendKey(@Payload String message) {
        return message;
    }
}
