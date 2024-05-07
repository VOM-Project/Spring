package vom.spring.domain.homepy;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Homepy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homepageId;

    private String greeting;

}
