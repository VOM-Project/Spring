package vom.spring.domain.touchpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TouchpointService {
    private final TouchpointRepository touchpointRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public TouchpointService(TouchpointRepository touchpointRepository, MemberRepository memberRepository) {
        this.touchpointRepository = touchpointRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 터치포인트 조회
     */
    @Transactional
    public List<Touchpoint> getTouchpoints(Long member_id) {
//        Member member = memberRepository.findById(member_id).get();
        return touchpointRepository.findByToMember_Id(member_id);
    }

    /**
     * 터치포인트 보내기
     */
    @Transactional
    public void sendTouchpoint(Long from_member_id, Long to_member_id) {

        Member from_member = memberRepository.findById(from_member_id).get();
        Member to_member = memberRepository.findById(to_member_id).get();

        touchpointRepository.save(
                Touchpoint.builder()
                        .createdAt(LocalDateTime.now())
                        .fromMember(from_member)
                        .toMember(to_member)
                        .build()
        );
    }

}
