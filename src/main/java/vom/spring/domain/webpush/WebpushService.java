package vom.spring.domain.webpush;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.touchpoint.Touchpoint;
import vom.spring.domain.touchpoint.TouchpointDto;
import vom.spring.domain.touchpoint.TouchpointRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebpushService {

    private final WebpushRepository webpushRepository;
    private final MemberRepository memberRepository;
//    private final WebcamRepository webcamRepository;

    @Autowired
    public WebpushService(WebpushRepository webpushRepository, MemberRepository memberRepository) {
        this.webpushRepository = webpushRepository;
        this.memberRepository = memberRepository;
    }
    /**
     * 웹푸쉬 생성
     */
    @Transactional
    public void createWebpush(Long from_member_id, Long to_member_id, Long webcam_id) {

        Member fromMember = memberRepository.findById(from_member_id).get();
        Member toMember = memberRepository.findById(to_member_id).get();

        webpushRepository.save(
                Touchpoint.builder()
                        .createdAt(LocalDateTime.now())
                        .fromMember(fromMember)
                        .toMember(toMember)
                        .build()
        );
    }

    /**
     * 웹푸쉬 조회
     */
    @Transactional
    public List<WebpushDto> getWebpushes(Long member_id) {
        return webpushRepository.findFromMemberIdsByToMemberId(member_id);
    }


}
