package dev.melik.auctionshortenedurl.service.shorten;


import java.util.List;

public interface ShortenService {
    Shorten getById(Long id,Long userId);
    Shorten shortenURL(Long userId, Shorten shorten);
    Shorten getByShortened(String shortened);
    List<Shorten> getByUserId(Long userId);
    void delete(Long id, Long userId);
}
