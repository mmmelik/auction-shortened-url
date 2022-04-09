package dev.melik.auctionshortenedurl.controller.shorten;

import dev.melik.auctionshortenedurl.service.shorten.Shorten;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShortenResponse {

    private Long id;
    private String shortened;

    public static ShortenResponse fromShorten(Shorten shorten) {
        return ShortenResponse.builder()
                .id(shorten.getId())
                .shortened(ShortenConstants.URL_PREFIX +shorten.getShortened())
                .build();
    }
}
