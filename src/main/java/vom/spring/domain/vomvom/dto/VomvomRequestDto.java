package vom.spring.domain.vomvom.dto;

import lombok.*;

public class VomvomRequestDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ReqeustVomvomDto {
        private Long toMemberId;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AcceptVomvomDto {
        private String nickname;
    }

}
