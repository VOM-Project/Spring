package vom.spring.domain.homepy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;

@Service
public class HomepyService {

    private final HomepyRepository homepyRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public HomepyService(HomepyRepository homepyRepository, MemberRepository memberRepository) {
        this.homepyRepository = homepyRepository;
        this.memberRepository = memberRepository;
    }


    /**
     * 프로필 조회
     */
    @Transactional
    public HomepyResponseDto.ProfileDto getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return HomepyResponseDto.ProfileDto.builder()
                .profileImgUrl(member.getProfileImgUrl())
                .nickname(member.getNickname())
                .vomVomCount(member.getVomVomCount())
                .email(member.getEmail())
                .birth(member.getBirth())
                .region(member.getRegion().getName())
                .build();
    }

    /**
     * 인사말 조회
     */
    @Transactional
    public HomepyResponseDto.GreetingDto getGreeting(Long memberId) {
        Homepy homepy = homepyRepository.findByMember_id(memberId);
        return HomepyResponseDto.GreetingDto.builder()
                .greeting(homepy.getGreeting())
                .build();
    }

    /**
     * 인사말 변경
     */
    @Transactional
    public void setGreeting(Long memberId, String greeting) {
        Homepy homepy = homepyRepository.findByMember_id(memberId);
        homepy.setGreeting(greeting);
    }

}
