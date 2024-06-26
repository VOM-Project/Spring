package vom.spring.domain.vomvom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import vom.spring.domain.vomvom.dto.VomvomRequestDto;
import vom.spring.domain.vomvom.dto.VomvomResponseDto;
import vom.spring.domain.vomvom.service.VomvomService;

@RequiredArgsConstructor
@RestController
@Tag(name = "Vomvom API", description = "Vomvom(친구) API 명세서")
@RequestMapping("/api/vomvom")
public class VomvomController {
    private final VomvomService vomvomService;

    /**
     * 봄봄 요청
     */
    @Operation(summary = "봄봄 요청을 합니다", description = "봄봄 요청을 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/request")
    public ResponseEntity<Void> requestVomvom(@RequestBody VomvomRequestDto.ReqeustVomvomDto request) {
        vomvomService.RequestVomvom(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 나에게 온 봄봄 요청 조회
     */
    @Operation(summary = "나에게 온 봄봄 요청을 조회합니다", description = "봄봄 요청을 조회 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/request")
    public ResponseEntity<VomvomResponseDto.GetMembersDto> getRequestVomvom() {
        VomvomResponseDto.GetMembersDto getMembersDto = vomvomService.getRequest();
        return ResponseEntity.status(HttpStatus.OK).body(getMembersDto);
    }
    /**
     * 봄봄 신청 수락
     */
    @Operation(summary = "봄봄 신청을 수락합니다", description = "봄봄 신청을 수락 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/accept")
    public ResponseEntity<VomvomResponseDto.AcceptVomvomDto> acceptRequestVomvom(@RequestBody VomvomRequestDto.AcceptVomvomDto request) {
        VomvomResponseDto.AcceptVomvomDto response = vomvomService.acceptRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 봄봄 조회
     */
    @Operation(summary = "봄봄을 조회합니다", description = "봄봄을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("")
    public ResponseEntity<VomvomResponseDto.GetMembersDto> getVomvoms() {
        VomvomResponseDto.GetMembersDto getMembersDto = vomvomService.getVomvom();
        return ResponseEntity.status(HttpStatus.OK).body(getMembersDto);
    }

    /**
     * 봄봄 수 조회
     */
//    @Operation(summary = "봄봄 수를 조회합니다", description = "봄봄을 조회합니다",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "화상채팅 방을 생성했습니다."),
//                    @ApiResponse(responseCode = "400", description = "채팅 방을 생성하지 못했습니다.",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "409", description = "올바르지 않은 닉네임, 올바르지 않은 이메일",
//                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            })
//    @GetMapping("")
//    public ResponseEntity<VomvomResponseDto.GetMembersDto> getVomvoms() {
//        VomvomResponseDto.GetMembersDto getMembersDto = vomvomService.getVomvom();
//        return ResponseEntity.status(HttpStatus.OK).body(getMembersDto);
//    }
}
