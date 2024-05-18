package vom.spring.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class MemberRequestDto {
    @Builder
    @Getter
    public static class CreateMemberDto {
        private Long memberId;
        private String nickname;
        private String region;
        private LocalDate birth;
        //프로필 사진 추가욤
    }

    @Builder
    @Getter
    public static class VerfyNicknameDto {
        private String nickname;
    }
}
