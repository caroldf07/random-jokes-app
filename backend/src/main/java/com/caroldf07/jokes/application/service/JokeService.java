package com.caroldf07.jokes.application.service;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;

import java.util.List;
import java.util.Random;

public class JokeService implements GetRandomJokeUseCase, GetAllJokesUseCase {

    private final JokeRepositoryPort jokeRepository;
    private final Random random;

    public JokeService(JokeRepositoryPort jokeRepository) {
        this.jokeRepository = jokeRepository;
        this.random = new Random();
    }

    @Override
    public Joke getRandomJoke() {
        List<Joke> jokes = jokeRepository.findAll();
        if (jokes.isEmpty()) {
            throw new IllegalStateException("No jokes available");
        }
        int index = random.nextInt(jokes.size());
        return jokes.get(index);
    }

    @Override
    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }
}
