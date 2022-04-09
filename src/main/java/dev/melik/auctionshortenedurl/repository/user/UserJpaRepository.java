package dev.melik.auctionshortenedurl.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByUsername(String username);

}
