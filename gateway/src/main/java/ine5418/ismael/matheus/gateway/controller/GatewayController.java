package ine5418.ismael.matheus.gateway.controller;

import com.google.gson.JsonObject;
import ine5418.ismael.matheus.gateway.dto.RedirectDTO;
import ine5418.ismael.matheus.gateway.dto.request.AddGatewayRequest;
import ine5418.ismael.matheus.gateway.dto.response.RedirectsResponse;
import ine5418.ismael.matheus.gateway.dto.response.Response;
import ine5418.ismael.matheus.gateway.dto.response.SuccessfulResponse;
import ine5418.ismael.matheus.gateway.service.RedirectService;
import ine5418.ismael.matheus.gateway.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/**")
@RequiredArgsConstructor
public class GatewayController {
    final RequestService requestService;
    final RedirectService redirectService;

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Object> redirect(HttpServletRequest hsr, @RequestBody Object body) {
        return requestService.redirectRequest(hsr, body);
    }

    @PostMapping("addgateway")
    public ResponseEntity<Response> addGateway(@RequestBody AddGatewayRequest addGatewayRequest) {
            return new ResponseEntity<>(redirectService.addRedirect(addGatewayRequest), HttpStatus.OK);
    }

    @GetMapping("getgateways")
    public ResponseEntity<RedirectsResponse> getGateways() {
        return new ResponseEntity<>(redirectService.getRedirects(), HttpStatus.OK);
    }
}
