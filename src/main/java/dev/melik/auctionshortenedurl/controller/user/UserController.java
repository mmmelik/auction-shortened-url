package dev.melik.auctionshortenedurl.controller.user;

import dev.melik.auctionshortenedurl.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<UserRegisterResponse> signup(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(UserRegisterResponse.fromUser(userService.signUp(userRegisterRequest.toUser())),HttpStatus.CREATED);
    }
}
