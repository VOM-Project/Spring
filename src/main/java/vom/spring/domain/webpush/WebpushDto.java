package vom.spring.domain.webpush;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WebpushDto {
    private Long fromMemberId;
    private LocalDateTime createdAt;
//    private Long webcamId;

    public WebpushDto(Long fromMemberId, LocalDateTime createdAt) {
        this.fromMemberId = fromMemberId;
        this.createdAt = createdAt;
//        this.webcamId = webcamId;
    }
}
