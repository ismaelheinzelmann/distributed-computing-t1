package ine5418.ismael.matheus.gateway.service;

import ine5418.ismael.matheus.gateway.dto.RedirectDTO;
import ine5418.ismael.matheus.gateway.dto.request.AddGatewayRequest;
import ine5418.ismael.matheus.gateway.dto.response.FailedResponse;
import ine5418.ismael.matheus.gateway.dto.response.RedirectsResponse;
import ine5418.ismael.matheus.gateway.dto.response.Response;
import ine5418.ismael.matheus.gateway.dto.response.SuccessfulResponse;
import ine5418.ismael.matheus.gateway.entity.RedirectEntity;
import ine5418.ismael.matheus.gateway.repository.RedirectRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedirectService {
    final RedirectRepository redirectRepository;

    public Response addRedirect(AddGatewayRequest addGatewayRequest){
        if (redirectRepository.findByPath(addGatewayRequest.getPath()).isPresent()) {
            return new FailedResponse("Redirect already exists");
        }
        RedirectEntity redirectEntity = new RedirectEntity();
        redirectEntity.setPath(addGatewayRequest.getPath());
        redirectEntity.setRedirect(addGatewayRequest.getRedirect());
        redirectRepository.save(redirectEntity);
        return new SuccessfulResponse("Gateway " + addGatewayRequest.getPath() + " successfully added.");
    }

    public RedirectDTO getRedirect(String path){
        Optional<RedirectEntity> redirectEntity = redirectRepository.findByPath(path);
        if (redirectEntity.isPresent()) {
            return new RedirectDTO(redirectEntity.get().getPath(), redirectEntity.get().getRedirect());
        }
        throw new RuntimeException("Redirect not found");
    }

    public RedirectsResponse getRedirects(){
        RedirectsResponse redirectsResponse = new RedirectsResponse();
        Iterable<RedirectEntity> redirectList = redirectRepository.findAll();
        List<RedirectDTO> redirectDTOList = new ArrayList<>();
        for (RedirectEntity redirect : redirectList) {
            RedirectDTO redirectDTO = new RedirectDTO();
            redirectDTO.setPath(redirect.getPath());
            redirectDTO.setRedirect(redirect.getRedirect());
            redirectDTOList.add(redirectDTO);
        }
        redirectsResponse.setRedirects(redirectDTOList);
        return redirectsResponse;
    }

    public Response removeRedirect(String path){
        if (redirectRepository.findByPath(path).isEmpty()) {
            return new FailedResponse("Redirect not found");
        }
        redirectRepository.delete(redirectRepository.findByPath(path).get());
        return new SuccessfulResponse("Gateway " + path + " successfully removed.");
    }

}
