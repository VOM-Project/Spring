package vom.spring.domain.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class AlbumDTO {
    @Builder
    @AllArgsConstructor
    @Getter
    static public class Info {
        String name;
        LocalDateTime created_date;

        public static AlbumDTO.Info of(Album album) {
            return Info.builder()
                    .name(album.getName())
                    .created_date(album.getCreatedAt())
                    .build();
        }
    }
}
