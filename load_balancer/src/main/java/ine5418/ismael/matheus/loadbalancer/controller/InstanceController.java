package ine5418.ismael.matheus.loadbalancer.controller;

import ine5418.ismael.matheus.loadbalancer.dto.request.AddInstanceRequest;
import ine5418.ismael.matheus.loadbalancer.dto.request.RemoveInstanceRequest;
import ine5418.ismael.matheus.loadbalancer.dto.response.RedirectsResponse;
import ine5418.ismael.matheus.loadbalancer.dto.response.Response;
import ine5418.ismael.matheus.loadbalancer.service.BalanceService;
import ine5418.ismael.matheus.loadbalancer.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/**")
@RequiredArgsConstructor
public class InstanceController {
    final RequestService requestService;
    final BalanceService balanceService;

    @CrossOrigin
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    public ResponseEntity<Object> redirect(HttpServletRequest hsr, @RequestBody Object body) {
        return requestService.redirectRequest(hsr, body);
    }

    @PostMapping("addinstance")
    public ResponseEntity<Response> addGateway(@RequestBody AddInstanceRequest addInstanceRequest) {
            return new ResponseEntity<>(balanceService.addInstance(addInstanceRequest), HttpStatus.OK);
    }

    @GetMapping("getinstances")
    public ResponseEntity<RedirectsResponse> getGateways() {
        return new ResponseEntity<>(balanceService.getInstances(), HttpStatus.OK);
    }

    @DeleteMapping("removeinstance")
    public ResponseEntity<Response> removeInstance(@RequestBody RemoveInstanceRequest removeInstanceRequest){
        return new ResponseEntity<>(balanceService.removeRedirect(removeInstanceRequest.getInstancePath()), HttpStatus.OK);
    }
}
