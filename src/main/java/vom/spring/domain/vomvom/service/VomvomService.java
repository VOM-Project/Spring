package vom.spring.domain.vomvom.service;

import vom.spring.domain.vomvom.dto.VomvomRequestDto;
import vom.spring.domain.vomvom.dto.VomvomResponseDto;

public interface VomvomService {

    void RequestVomvom (VomvomRequestDto.ReqeustVomvomDto request);
    VomvomResponseDto.GetMembersDto getRequest();
    VomvomResponseDto.AcceptVomvomDto acceptRequest (VomvomRequestDto.AcceptVomvomDto request);
    VomvomResponseDto.GetMembersDto getVomvom ();
}
