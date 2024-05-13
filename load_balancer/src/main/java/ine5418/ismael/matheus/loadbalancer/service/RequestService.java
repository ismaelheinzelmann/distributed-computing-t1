package ine5418.ismael.matheus.loadbalancer.service;

import com.google.gson.Gson;
import ine5418.ismael.matheus.loadbalancer.dto.InstanceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RequestService {

    final BalanceService balanceService;

    public ResponseEntity<Object> redirectRequest(HttpServletRequest hsr, Object requestBody) {
        String[] url = hsr.getRequestURL().toString().replace("http://", "").split("/");
        String method = hsr.getMethod();

        StringBuilder urlBuilder = new StringBuilder();
        for (int i = 1; i < url.length; i++) {
            urlBuilder.append("/").append(url[i]);
        }
        InstanceDto nextInstance = balanceService.getNextInstance();
        String fullUrl = "http://" + nextInstance.getInstancePath() + urlBuilder;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(requestBody), headers);
        HttpMethod httpMethod = HttpMethod.valueOf(method);

        return restTemplate.exchange(fullUrl, httpMethod, requestEntity, Object.class);
    }
}
