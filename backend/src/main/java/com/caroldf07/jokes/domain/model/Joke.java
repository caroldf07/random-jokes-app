package com.caroldf07.jokes.domain.model;

import java.util.Objects;

public class Joke {

    private final Long id;
    private final String setup;
    private final String punchline;
    private final String category;

    public Joke(Long id, String setup, String punchline, String category) {
        this.id = id;
        this.setup = setup;
        this.punchline = punchline;
        this.category = category;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joke joke = (Joke) o;
        return Objects.equals(id, joke.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Joke{id=" + id + ", setup='" + setup + "', punchline='" + punchline + "', category='" + category + "'}";
    }
}
