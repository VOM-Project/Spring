package vom.spring.domain.webcam.dto;

import lombok.*;

public class WebcamResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateWebcamDto {
        private Long webcamId; //화상채팅 방생성 id
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetRemoteMemberDto {
        private Long memberId;
    }
}
