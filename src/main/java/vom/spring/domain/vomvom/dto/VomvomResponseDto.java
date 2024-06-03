package vom.spring.domain.vomvom.dto;

import lombok.*;

import java.util.List;

public class VomvomResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class GetMembersDto {
        private List<MemberDto> memberDtoList;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class MemberDto {
        private String nickname;
        private String profileUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class AcceptVomvomDto {
        private boolean isVomvom;
    }
}
