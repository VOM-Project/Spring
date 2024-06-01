package vom.spring.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MemberRequestDto {
    @Builder
    @Getter
    public static class CreateMemberDto {
        private Long memberId;
        private String nickname;
        private String region;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate birth;
        private List<String> keyword;
    }

    @Builder
    @Getter
    public static class VerfyNicknameDto {
        private String nickname;
    }

    @Builder
    @Getter
    public static class ChangeNicknameDto {
        private String nickname;
    }
}
