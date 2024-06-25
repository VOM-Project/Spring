package vom.spring.domain.webpush.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WebpushDto {
    private Long fromMemberId;
    private LocalDateTime createdAt;
    private Long webcamId;

    public WebpushDto(Long fromMemberId, LocalDateTime createdAt, Long webcamId) {
        this.fromMemberId = fromMemberId;
        this.createdAt = createdAt;
        this.webcamId = webcamId;
    }
}
