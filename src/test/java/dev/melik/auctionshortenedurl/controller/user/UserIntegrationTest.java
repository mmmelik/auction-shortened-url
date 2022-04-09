package dev.melik.auctionshortenedurl.controller.user;

import dev.melik.auctionshortenedurl.BaseIntegrationTest;
import dev.melik.auctionshortenedurl.repository.user.UserEntity;
import dev.melik.auctionshortenedurl.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    @Sql(scripts = "/clear-db.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_register_user(){
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setUsername("melik2");
        userRegisterRequest.setPassword("mmmelik");

        ResponseEntity<UserRegisterResponse> response=testRestTemplate
                .postForEntity("/user/signup",userRegisterRequest,UserRegisterResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isNotNull();

        Optional<UserEntity> customer=userJpaRepository.findById(response.getBody().getUserId());
        assertThat(customer).isPresent();
    }

    @Test
    @Sql(scripts = "/insert-users.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/clear-db.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_block_user_duplication(){
        UserRegisterRequest userRegisterRequest=new UserRegisterRequest();
        userRegisterRequest.setUsername("melik");
        userRegisterRequest.setPassword("mmmelik");

        ResponseEntity response=testRestTemplate
                .postForEntity("/user/signup",userRegisterRequest,null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);//todo:exception handling

    }
}