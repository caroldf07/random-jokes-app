package com.caroldf07.jokes.application.service;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import com.caroldf07.jokes.domain.port.in.SaveJokeUseCase;
import com.caroldf07.jokes.domain.port.out.ExternalJokeApiPort;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;

import java.util.List;

public class JokeService implements GetRandomJokeUseCase, GetAllJokesUseCase, SaveJokeUseCase {

    private final ExternalJokeApiPort externalJokeApiPort;
    private final JokeRepositoryPort jokeRepository;

    public JokeService(ExternalJokeApiPort externalJokeApiPort, JokeRepositoryPort jokeRepository) {
        this.externalJokeApiPort = externalJokeApiPort;
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Joke getRandomJoke() {
        return externalJokeApiPort.fetchRandomJoke();
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    @Override
    public Joke saveJoke(Joke joke) {
        return jokeRepository.save(joke);
    }
}
