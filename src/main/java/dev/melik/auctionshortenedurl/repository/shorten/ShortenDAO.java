package dev.melik.auctionshortenedurl.repository.shorten;

import dev.melik.auctionshortenedurl.service.shorten.Shorten;

import java.util.List;

public interface ShortenDAO {

    ShortenEntity findById(Long id);
    ShortenEntity save(ShortenEntity shortenEntity);
    ShortenEntity getByShortened(String shortened);
    List<ShortenEntity> getByUserId(Long userId);
    void delete(ShortenEntity entity);
}
