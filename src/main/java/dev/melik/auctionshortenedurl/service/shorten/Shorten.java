package dev.melik.auctionshortenedurl.service.shorten;

import dev.melik.auctionshortenedurl.repository.shorten.ShortenEntity;
import dev.melik.auctionshortenedurl.service.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class Shorten {

    private Long id;
    private String raw;
    private String shortened;
    private User user;
    private LocalDateTime creationDate;

    public static Shorten fromEntity(ShortenEntity shortenEntity) {
        return Shorten.builder()
                .id(shortenEntity.getId())
                .raw(shortenEntity.getRaw())
                .shortened(shortenEntity.getShortened())
                .user(User.fromEntity(shortenEntity.getUser()))
                .creationDate(shortenEntity.getCreationDate())
                .build();
    }

    public static List<Shorten> fromEntity(List<ShortenEntity> entities) {
        return entities.stream()
                .map(Shorten::fromEntity)
                .collect(Collectors.toList());
    }



    public ShortenEntity toEntity() {
        ShortenEntity shortenEntity=new ShortenEntity();
        shortenEntity.setId(id);
        shortenEntity.setRaw(raw);
        shortenEntity.setShortened(shortened);
        shortenEntity.setUser(user.toEntity());
        return shortenEntity;
    }

    public static String generateShortenedString(int len){
        final char[] lettersNumbers="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder s= new StringBuilder();
        Random random=new Random();
        while (s.length()<len){
            s.append(lettersNumbers[random.nextInt(lettersNumbers.length)]);
        }
        return s.toString();
    }
}
