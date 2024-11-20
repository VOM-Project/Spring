package vom.spring.domain.touchpoint;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TouchpointDto {
    private String fromMemberNickname;
    private LocalDateTime createdAt;
    private String fromMemberProfileImgUrl;

    public TouchpointDto(String fromMemberNickname, LocalDateTime createdAt, String fromMemberProfileImgUrl) {
        this.fromMemberNickname = fromMemberNickname;
        this.createdAt = createdAt;
        this.fromMemberProfileImgUrl = fromMemberProfileImgUrl;
    }
}
