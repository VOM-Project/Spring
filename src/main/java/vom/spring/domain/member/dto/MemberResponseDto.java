package vom.spring.domain.member.dto;

import lombok.*;

import java.time.LocalDate;

public class MemberResponseDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateDto {
        private Long memberId;
        private String nickname;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class VerifyNicknameDto {
        private boolean isExisted;
    }
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetMemberDto {
        private boolean isExisted;
        private Long findMemberId;
        private String nickname;
        private String profileImgUrl;
        private String email;
        private LocalDate birth;
        private String region;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ChangeNicknameDto {
        private boolean isAvailable;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UploadImageDto {
        private String profileImgUrl;
    }

}
