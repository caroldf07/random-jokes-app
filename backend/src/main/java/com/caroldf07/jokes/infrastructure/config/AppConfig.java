package com.caroldf07.jokes.infrastructure.config;

import com.caroldf07.jokes.application.service.JokeService;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import com.caroldf07.jokes.domain.port.in.SaveJokeUseCase;
import com.caroldf07.jokes.domain.port.out.ExternalJokeApiPort;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;
import com.caroldf07.jokes.infrastructure.adapter.out.api.JokeApiAdapter;
import com.caroldf07.jokes.infrastructure.adapter.out.persistence.JokeJpaRepository;
import com.caroldf07.jokes.infrastructure.adapter.out.persistence.MariaDbJokeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value("${jokes.api.base-url}")
    private String jokeApiBaseUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExternalJokeApiPort externalJokeApiPort(RestTemplate restTemplate) {
        return new JokeApiAdapter(restTemplate, jokeApiBaseUrl);
    }

    @Bean
    public JokeRepositoryPort jokeRepositoryPort(JokeJpaRepository jpaRepository) {
        return new MariaDbJokeRepository(jpaRepository);
    }

    @Bean
    public JokeService jokeService(ExternalJokeApiPort externalJokeApiPort,
                                   JokeRepositoryPort jokeRepositoryPort) {
        return new JokeService(externalJokeApiPort, jokeRepositoryPort);
    }

    @Bean
    public GetRandomJokeUseCase getRandomJokeUseCase(JokeService jokeService) {
        return jokeService;
    }

    @Bean
    public GetAllJokesUseCase getAllJokesUseCase(JokeService jokeService) {
        return jokeService;
    }

    @Bean
    public SaveJokeUseCase saveJokeUseCase(JokeService jokeService) {
        return jokeService;
    }
}
