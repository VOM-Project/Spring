package vom.spring.domain.homepy;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Homepy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homepageId;
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
