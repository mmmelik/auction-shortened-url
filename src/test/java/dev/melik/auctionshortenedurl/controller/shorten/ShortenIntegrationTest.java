package dev.melik.auctionshortenedurl.controller.shorten;

import dev.melik.auctionshortenedurl.BaseIntegrationTest;
import dev.melik.auctionshortenedurl.repository.shorten.ShortenEntity;
import dev.melik.auctionshortenedurl.repository.shorten.ShortenJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ShortenIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ShortenJpaRepository shortenJpaRepository;


    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_generate_short_url(){
        ShortenRequest shortenRequest=new ShortenRequest();
        shortenRequest.setUrl("http://google.com");

        ResponseEntity<ShortenResponse> response=testRestTemplate
                .postForEntity("/user/1/url/create",shortenRequest,ShortenResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getShortened()).isNotNull();

        Optional<ShortenEntity> optional=shortenJpaRepository.findById(response.getBody().getId());
        assertThat(optional).isPresent();
        assertThat(optional.get().getRaw()).isEqualTo("http://google.com");
        assertThat(optional.get().getShortened().substring(optional.get().getShortened().lastIndexOf("/")+1))
                .hasSize(8);

        assertThat(optional.get().getUser().getId()).isEqualTo(1L);

    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-urls.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_delete_url(){

        ResponseEntity response=testRestTemplate
                .exchange("/user/1/url/detail/5", HttpMethod.DELETE, HttpEntity.EMPTY,ResponseEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(shortenJpaRepository.findById(5L)).isEmpty();
    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-urls.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_block_unauthorized_delete_url(){

        ResponseEntity<Void> response=testRestTemplate
                .exchange("/user/1/url/detail/3", HttpMethod.DELETE, HttpEntity.EMPTY,Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(shortenJpaRepository.findById(5L)).isPresent();
    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-urls.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_url_details_list(){

        ResponseEntity<ShortenedDetailResponse[]> response=testRestTemplate
                .getForEntity("/user/2/url/list",ShortenedDetailResponse[].class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody())
                .extracting("id","url","shortened","creationDate")
                .containsExactly(
                        tuple(2L,"http://google.com",ShortenConstants.URL_PREFIX+"asdasdaa", LocalDateTime.of(2022,2,13,0,0)),
                        tuple(3L,"http://facebook.com",ShortenConstants.URL_PREFIX+"abcabcab", LocalDateTime.of(2022,2,13,0,0))
                );

    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-urls.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_retrieve_url_details(){

        ResponseEntity<ShortenedDetailResponse> response=testRestTemplate
                .getForEntity("/user/1/url/detail/1",ShortenedDetailResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody())
                .extracting("id","url","shortened","creationDate")
                .containsExactly(1L,"http://google.com",ShortenConstants.URL_PREFIX+"a1b2c3d4", LocalDateTime.of(2022,2,13,0,0));

    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-urls.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_not_retrieve_unauthorized_url_details(){

        ResponseEntity<ShortenedDetailResponse> response=testRestTemplate
                .getForEntity("/user/1/url/detail/2",ShortenedDetailResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);//todo:exception handling.

        assertThat(shortenJpaRepository.findById(2L)).isPresent();
    }

}