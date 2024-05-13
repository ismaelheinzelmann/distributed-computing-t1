package ine5418.ismael.matheus.user.service;

import ine5418.ismael.matheus.user.dto.request.LogInRequest;
import ine5418.ismael.matheus.user.dto.request.SignUpRequest;
import ine5418.ismael.matheus.user.entity.UserEntity;
import ine5418.ismael.matheus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;

    public void signup(SignUpRequest signUpRequest){
        Optional<UserEntity> signedUser = userRepository.findByUser(signUpRequest.getLogin());
        if (signedUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        UserEntity user = new UserEntity();
        user.setUser(signUpRequest.getLogin());
        user.setPassword(signUpRequest.getPassword());
        userRepository.save(user);
    }

    public Integer login(LogInRequest loginRequest){
        Optional<UserEntity> signedUser = userRepository.findByUserAndPassword(loginRequest.getLogin(), loginRequest.getPassword());
        if (signedUser.isPresent()) {
            return signedUser.get().getId();
        }
        throw new RuntimeException("User not found");
    }
}
