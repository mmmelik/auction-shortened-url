package dev.melik.auctionshortenedurl.controller.shorten;

import dev.melik.auctionshortenedurl.service.shorten.Shorten;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ShortenRequest {

    @NotBlank
    private String url;

    public Shorten toShorten() {
        return Shorten.builder()
                .raw(url)
                .build();
    }
}
