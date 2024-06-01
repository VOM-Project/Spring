package vom.spring.domain.touchpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vom.spring.domain.album.Album;
import vom.spring.domain.album.AlbumDto;
import vom.spring.domain.member.domain.Member;

import java.time.LocalDateTime;

public class TouchpointDto {
    private Long fromMemberId;
    private LocalDateTime createdAt;
    private String fromMemberProfileImgUrl;

    public TouchpointDto(Long fromMemberId, LocalDateTime createdAt, String fromMemberProfileImgUrl) {
        this.fromMemberId = fromMemberId;
        this.createdAt = createdAt;
        this.fromMemberProfileImgUrl = fromMemberProfileImgUrl;
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

    public String getFromMemberProfileImgUrl() {
        return fromMemberProfileImgUrl;
    }

    public void setFromMemberProfileImgUrl(String fromMemberProfileUrl) {
        this.fromMemberProfileImgUrl = fromMemberProfileUrl;
    }
}
