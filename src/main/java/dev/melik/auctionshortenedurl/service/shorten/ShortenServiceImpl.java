package dev.melik.auctionshortenedurl.service.shorten;

import dev.melik.auctionshortenedurl.repository.shorten.ShortenDAO;
import dev.melik.auctionshortenedurl.repository.user.UserDAO;
import dev.melik.auctionshortenedurl.service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortenServiceImpl implements ShortenService{

    private final UserDAO userDAO;
    private final ShortenDAO shortenDAO;

    @Override
    public Shorten getById(Long id, Long userId) {
        Shorten shorten=Shorten.fromEntity(shortenDAO.findById(id));
        if (shorten.getUser().getId()!=userId){
            throw new RuntimeException("Not authorized");
        }
        return shorten;
    }

    @Override
    public Shorten shortenURL(Long userId, Shorten shorten) {
        User user=User.fromEntity(userDAO.findUserById(userId));
        shorten.setUser(user);
        shorten.setShortened(Shorten.generateShortenedString(8));

        return Shorten.fromEntity(shortenDAO.save(shorten.toEntity()));
    }

    @Override
    public Shorten getByShortened(String shortened) {
        return Shorten.fromEntity(shortenDAO.getByShortened(shortened));
    }

    @Override
    public List<Shorten> getByUserId(Long userId) {
        return Shorten.fromEntity(shortenDAO.getByUserId(userId));
    }

    @Override
    public void delete(Long id, Long userId) {
        Shorten shorten=getById(id,userId);
        shortenDAO.delete(shorten.toEntity());
    }
}
