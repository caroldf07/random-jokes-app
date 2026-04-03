package com.caroldf07.jokes.infrastructure.config;

import com.caroldf07.jokes.application.service.JokeService;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;
import com.caroldf07.jokes.infrastructure.adapter.out.persistence.InMemoryJokeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JokeRepositoryPort jokeRepositoryPort() {
        return new InMemoryJokeRepository();
    }

    @Bean
    public JokeService jokeService(JokeRepositoryPort jokeRepositoryPort) {
        return new JokeService(jokeRepositoryPort);
    }

    @Bean
    public GetRandomJokeUseCase getRandomJokeUseCase(JokeService jokeService) {
        return jokeService;
    }

    @Bean
    public GetAllJokesUseCase getAllJokesUseCase(JokeService jokeService) {
        return jokeService;
    }
}
