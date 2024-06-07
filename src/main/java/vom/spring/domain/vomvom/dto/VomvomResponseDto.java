package vom.spring.domain.vomvom.dto;

import lombok.*;

import java.util.List;

public class VomvomResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetMembersDto {
        private List<MemberDto> memberDtoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MemberDto {
        private String nickname;
        private String profileUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AcceptVomvomDto {
        private boolean isVomvom;
    }
}
