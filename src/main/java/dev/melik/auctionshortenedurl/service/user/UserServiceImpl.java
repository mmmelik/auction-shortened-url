package dev.melik.auctionshortenedurl.service.user;

import dev.melik.auctionshortenedurl.repository.user.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

    @Override
    public User signUp(User user) {
        if (userDAO.userExist(user.getUsername())){
            throw new RuntimeException("User "+ user.getUsername() + " exist.");
        }

        return User.fromEntity(userDAO.saveUser(user.toEntity()));
    }


}
