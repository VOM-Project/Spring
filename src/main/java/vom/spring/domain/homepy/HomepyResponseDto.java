package vom.spring.domain.homepy;

import lombok.*;
import vom.spring.domain.member.domain.Region;

import java.time.LocalDate;

public class HomepyResponseDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProfileDto {
        private String profileImgUrl;
        private String nickname;
        private Integer vomVomCount;
        private String email;
        private LocalDate birth;
        private String region;
    }
}
