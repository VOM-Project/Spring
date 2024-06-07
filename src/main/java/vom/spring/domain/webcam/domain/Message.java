package vom.spring.domain.webcam.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL) //null이 아닌 것들은 표시 x
public class Message {
    @Enumerated(EnumType.STRING)
    private Type type;
    private String webcamId;
    private String sender;
    private Object offer;
    private Object answer;
    private Object ice;
}
