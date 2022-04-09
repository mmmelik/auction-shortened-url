package dev.melik.auctionshortenedurl.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO{

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserEntity findUserById(Long id) {
        Optional<UserEntity> optionalUser=userJpaRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
        return optionalUser.get();
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userJpaRepository.save(userEntity);
    }

    @Override
    public boolean userExist(String username) {
        return userJpaRepository.existsByUsername(username);
    }

}
