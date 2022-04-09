package dev.melik.auctionshortenedurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AuctionShortenedUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionShortenedUrlApplication.class, args);
    }

}
