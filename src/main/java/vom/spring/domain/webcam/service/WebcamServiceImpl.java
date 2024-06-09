package vom.spring.domain.webcam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vom.spring.domain.member.domain.Member;
import vom.spring.domain.member.repository.MemberRepository;
import vom.spring.domain.webcam.domain.MemberWebcam;
import vom.spring.domain.webcam.domain.Status;
import vom.spring.domain.webcam.domain.Webcam;
import vom.spring.domain.webcam.dto.WebcamRequestDto;
import vom.spring.domain.webcam.dto.WebcamResponseDto;
import vom.spring.domain.webcam.repository.MemberWebcamRepository;
import vom.spring.domain.webcam.repository.WebcamRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebcamServiceImpl implements WebcamServcie{
    private final WebcamRepository webcamRepository;
    private final MemberRepository memberRepository;
    private final MemberWebcamRepository memberWebcamRepository;

    /**
     * 화상채팅 방 생성
     */
    @Transactional
    @Override
    public WebcamResponseDto.CreateWebcamDto createWebcamRoom(WebcamRequestDto.CreateWebcamDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member fromMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        //방 생성
        Webcam newWebcam = Webcam.builder().createdAt(LocalDateTime.now()).status(Status.ACTIVE).build();
        webcamRepository.save(newWebcam);
        //member-방 연결
        Member toMember = memberRepository.findById(request.getToMemberId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 멤버입니다"));
        MemberWebcam fromMemberWebcam = MemberWebcam.builder().member(fromMember).webcam(newWebcam).build();
        MemberWebcam toMemberWebcam = MemberWebcam.builder().member(toMember).webcam(newWebcam).build();
        memberWebcamRepository.save(fromMemberWebcam);
        memberWebcamRepository.save(toMemberWebcam);
        return WebcamResponseDto.CreateWebcamDto.builder().webcamId(newWebcam.getId()).build();
    }

    @Transactional
    @Override
    public void deleteWebcamRoom(WebcamRequestDto.DeleteWebcamDto request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName(); //현재 접속 유저 정보 가져오기
        Member fromMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않은 유저입니다"));
        //해당 방 찾기
        Webcam webcam = webcamRepository.findById(request.getRoomId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 방입니다"));
        //해당 방 관련 연관관계 삭제
        memberWebcamRepository.deleteByWebcam(webcam);
        //해당 방 삭제
        webcamRepository.deleteById(webcam.getId());
    }
}
