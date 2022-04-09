package dev.melik.auctionshortenedurl.repository.shorten;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortenJpaRepository extends JpaRepository<ShortenEntity,Long> {

    @Query("select s from ShortenEntity s where s.user.id = ?1")
    List<ShortenEntity> findByUser_Id(Long id);

    @Query("select s from ShortenEntity s where s.shortened = ?1")
    Optional<ShortenEntity> findByShortened(String shortened);

}
