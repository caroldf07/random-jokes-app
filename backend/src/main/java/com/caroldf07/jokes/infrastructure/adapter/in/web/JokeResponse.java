package com.caroldf07.jokes.infrastructure.adapter.in.web;

import com.caroldf07.jokes.domain.model.Joke;

public class JokeResponse {

    private final Long id;
    private final String setup;
    private final String punchline;
    private final String category;

    public JokeResponse(Joke joke) {
        this.id = joke.getId();
        this.setup = joke.getSetup();
        this.punchline = joke.getPunchline();
        this.category = joke.getCategory();
    }

    public Long getId() {
        return id;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public String getCategory() {
        return category;
    }
}
