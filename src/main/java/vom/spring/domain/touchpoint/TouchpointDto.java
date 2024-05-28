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

    public TouchpointDto(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }

    public Long getFromMemberId() {
        return fromMemberId;
    }

    public void setFromMemberId(Long fromMemberId) {
        this.fromMemberId = fromMemberId;
    }
}
