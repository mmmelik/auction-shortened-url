package dev.melik.auctionshortenedurl.service.user;

import dev.melik.auctionshortenedurl.repository.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {

    private Long id;

    private String username;

    private String password;

    public static User fromEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    public UserEntity toEntity() {
        UserEntity entity=new UserEntity();
        entity.setId(id);
        entity.setUsername(username);
        entity.setPassword(password);
        return entity;
    }
}
