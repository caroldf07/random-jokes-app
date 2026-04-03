package com.caroldf07.jokes.domain.port.in;

import com.caroldf07.jokes.domain.model.Joke;

import java.util.List;

public interface GetAllJokesUseCase {

    List<Joke> getAllJokes();
}
