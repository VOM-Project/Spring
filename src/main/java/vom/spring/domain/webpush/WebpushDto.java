package vom.spring.domain.webpush;

import java.time.LocalDateTime;

public class WebpushDto {
    private Long fromMemberId;
    private LocalDateTime createdAt;
//    private Long webcamId;

    public WebpushDto(Long fromMemberId, LocalDateTime createdAt) {
        this.fromMemberId = fromMemberId;
        this.createdAt = createdAt;
//        this.webcamId = webcamId;
    }

    public Long getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public Long getWebcamId() {
//        return webcamId;
//    }
//
//    public void setFromMemberProfileImgUrl(String fromMemberProfileUrl) {
//        this.webcamId = webcamId;
//    }
}
