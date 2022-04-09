package dev.melik.auctionshortenedurl.controller.user;

import dev.melik.auctionshortenedurl.service.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRegisterRequest {

    @NotBlank
    private String username;

    @NotEmpty
    private String password;

    public User toUser() {
        return User.builder()
                .username(username)
                .password(encryptPassword(password))
                .build();
    }

    private String encryptPassword(String raw){
        return new BCryptPasswordEncoder().encode(raw);
    }
}
