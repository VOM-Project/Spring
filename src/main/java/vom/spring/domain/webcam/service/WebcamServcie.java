package vom.spring.domain.webcam.service;

import vom.spring.domain.webcam.dto.WebcamRequestDto;
import vom.spring.domain.webcam.dto.WebcamResponseDto;

public interface WebcamServcie {
    WebcamResponseDto.CreateWebcamDto createWebcamRoom(WebcamRequestDto.CreateWebcamDto request);
    void deleteWebcamRoom(WebcamRequestDto.DeleteWebcamDto request);
}
