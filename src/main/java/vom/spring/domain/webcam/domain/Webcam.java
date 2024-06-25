package vom.spring.domain.webcam.domain;

import jakarta.persistence.*;
import lombok.*;
import vom.spring.domain.homepy.Homepy;
import vom.spring.domain.webpush.domain.Webpush;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Webcam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "webcam", fetch = LAZY)
    private Webpush webpush;
}
