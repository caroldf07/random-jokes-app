package com.caroldf07.jokes.domain.port.out;

import com.caroldf07.jokes.domain.model.Joke;

import java.util.List;
import java.util.Optional;

public interface JokeRepositoryPort {

    List<Joke> findAll();

    Optional<Joke> findById(Long id);

    long count();
}
