package ine5418.ismael.matheus.user.controller;

import ine5418.ismael.matheus.user.dto.request.LogInRequest;
import ine5418.ismael.matheus.user.dto.request.SignUpRequest;
import ine5418.ismael.matheus.user.dto.response.FailedResponse;
import ine5418.ismael.matheus.user.dto.response.Response;
import ine5418.ismael.matheus.user.dto.response.SuccessfulResponse;
import ine5418.ismael.matheus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/**")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Response> signup(@RequestBody SignUpRequest signUpRequest) {
        try{
            userService.signup(signUpRequest);
            return new ResponseEntity<>(new SuccessfulResponse("User " + signUpRequest.getLogin() + " successfully signed up."), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Response> login(@RequestBody LogInRequest signUpRequest) {
        try{
            return new ResponseEntity<>(new SuccessfulResponse(userService.login(signUpRequest).toString()), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
