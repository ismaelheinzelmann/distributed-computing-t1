package ine5418.ismael.matheus.gateway.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    final RedirectService redirectService;

    public ResponseEntity<Object> redirectRequest(HttpServletRequest hsr, Object requestBody) {
        String[] url = hsr.getRequestURL().toString().replace("http://", "").split("/");
        String method = hsr.getMethod();

        StringBuilder urlBuilder = new StringBuilder();
        for (int i = 2; i < url.length; i++) {
            urlBuilder.append("/").append(url[i]);
        }
        String fullUrl = "http://" + redirectService.getRedirect(url[1]).getRedirect() + urlBuilder;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        HttpEntity<String> requestEntity = new HttpEntity<>(gson.toJson(requestBody), headers);
        HttpMethod httpMethod = HttpMethod.valueOf(method);

        return restTemplate.exchange(fullUrl, httpMethod, requestEntity, Object.class);
    }
}
