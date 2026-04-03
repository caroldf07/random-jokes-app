package com.caroldf07.jokes.domain.port.in;

import com.caroldf07.jokes.domain.model.Joke;

public interface SaveJokeUseCase {

    Joke saveJoke(Joke joke);
}
