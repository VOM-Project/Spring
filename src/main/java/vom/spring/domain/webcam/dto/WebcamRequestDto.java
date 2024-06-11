package vom.spring.domain.webcam.dto;

import lombok.*;

public class WebcamRequestDto {
    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateWebcamDto {
        private Long toMemberId; //화상 채팅 요청받은 유저의 id
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DeleteWebcamDto {
        private Long roomId;
    }
}
