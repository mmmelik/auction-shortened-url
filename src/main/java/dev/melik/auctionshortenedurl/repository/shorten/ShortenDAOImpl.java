package dev.melik.auctionshortenedurl.repository.shorten;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortenDAOImpl implements ShortenDAO {

    private final ShortenJpaRepository shortenJpaRepository;

    @Override
    public ShortenEntity findById(Long id) {
        Optional<ShortenEntity> optionalShorten = shortenJpaRepository.findById(id);
        if (optionalShorten.isEmpty()){
            throw new RuntimeException("Shortened url with id "+id+" not found.");
        }
        return optionalShorten.get();
    }

    @Override
    public ShortenEntity save(ShortenEntity shortenEntity) {
        return shortenJpaRepository.save(shortenEntity);
    }

    @Override
    public ShortenEntity getByShortened(String shortened) {
        Optional<ShortenEntity> optionalShortened= shortenJpaRepository.findByShortened(shortened);
        if (optionalShortened.isEmpty()){
            throw new RuntimeException("The shortened url does not exist.");
        }
        return optionalShortened.get();
    }

    @Override
    public List<ShortenEntity> getByUserId(Long userId) {
        return shortenJpaRepository.findByUser_Id(userId);
    }

    @Override
    public void delete(ShortenEntity  entity) {
        shortenJpaRepository.delete(entity);
    }
}
