package com.caroldf07.jokes.domain.port.out;

import com.caroldf07.jokes.domain.model.Joke;

public interface ExternalJokeApiPort {

    Joke fetchRandomJoke();
}
