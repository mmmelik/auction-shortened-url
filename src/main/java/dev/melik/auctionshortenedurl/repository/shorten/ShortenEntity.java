package dev.melik.auctionshortenedurl.repository.shorten;

import dev.melik.auctionshortenedurl.repository.common.BaseEntity;
import dev.melik.auctionshortenedurl.repository.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "shorten")
public class ShortenEntity extends BaseEntity {

    @Column(nullable = false)
    private String raw;

    @Column(nullable = false, unique = true)
    private String shortened;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UserEntity user;

}
