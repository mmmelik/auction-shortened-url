package dev.melik.auctionshortenedurl.repository.user;


public interface UserDAO {

    UserEntity findUserById(Long id);

    UserEntity saveUser(UserEntity userEntity);

    boolean userExist(String username);
}
