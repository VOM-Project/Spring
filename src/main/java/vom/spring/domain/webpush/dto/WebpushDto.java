package vom.spring.domain.webpush.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

@Getter @Setter
public class WebpushDto {
    private String fromMemberNickname;
    private LocalDateTime createdAt;
    private Long webcamId;

    public WebpushDto(String fromMemberNickname, LocalDateTime createdAt, Long webcamId) {
        this.fromMemberNickname = fromMemberNickname;
        this.createdAt = createdAt;
        this.webcamId = webcamId;
    }
}
