package dev.melik.auctionshortenedurl.controller.shorten;

import dev.melik.auctionshortenedurl.service.shorten.ShortenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShortenController {

    private final ShortenService shortenService;

    @PostMapping("/user/{userId}/url/create")
    public ResponseEntity<ShortenResponse> shortenURL(@PathVariable Long userId, @RequestBody @Valid ShortenRequest shortenRequest){
        return new ResponseEntity<>(ShortenResponse.fromShorten(shortenService.shortenURL(userId,shortenRequest.toShorten())), HttpStatus.CREATED);
    }

    @GetMapping("/s/{shortened}")
    public ResponseEntity<Void> goToURL(@PathVariable String shortened){
        String url=shortenService.getByShortened(shortened).getRaw();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(url))
                .build();
    }

    @GetMapping("/user/{userId}/url/list")
    public ResponseEntity<List<ShortenedDetailResponse>> getUserURLs(@PathVariable Long userId){
        return new ResponseEntity<>(ShortenedDetailResponse.from(shortenService.getByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/url/detail/{urlId}")
    public ResponseEntity<ShortenedDetailResponse> getDetails(@PathVariable Long userId, @PathVariable Long urlId){
        return new ResponseEntity<>(ShortenedDetailResponse.from(shortenService.getById(urlId,userId)),HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/url/detail/{urlId}")
    public void deleteURL(@PathVariable Long userId, @PathVariable Long urlId){
        shortenService.delete(urlId,userId);
    }
}
