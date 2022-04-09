package dev.melik.auctionshortenedurl.controller.shorten;

import dev.melik.auctionshortenedurl.service.shorten.Shorten;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ShortenedDetailResponse {

    private Long id;
    private String url;
    private String shortened;
    private LocalDateTime creationDate;

    public static ShortenedDetailResponse from(Shorten shorten) {
        return ShortenedDetailResponse.builder()
                .id(shorten.getId())
                .url(shorten.getRaw())
                .shortened(ShortenConstants.URL_PREFIX +shorten.getShortened())
                .creationDate(shorten.getCreationDate())
                .build();
    }

    public static List<ShortenedDetailResponse> from(List<Shorten> shortens) {
        return shortens.stream()
                .map(ShortenedDetailResponse::from)
                .collect(Collectors.toList());
    }
}
