package vom.spring.domain.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class AlbumDto {
    @Builder
    @AllArgsConstructor
    @Getter
    static public class Info {
        String name;
        LocalDateTime created_date;
        String img_url;

        public static AlbumDto.Info of(Album album) {
            return Info.builder()
                    .name(album.getName())
                    .created_date(album.getCreatedAt())
                    .img_url(album.getImg_url())
                    .build();
        }
    }
}
