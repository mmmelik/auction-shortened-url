package dev.melik.auctionshortenedurl.controller.user;

import dev.melik.auctionshortenedurl.service.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {

    private Long userId;

    public static UserRegisterResponse fromUser(User user) {
        return UserRegisterResponse.builder()
                .userId(user.getId())
                .build();
    }
}
