package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import com.caroldf07.jokes.domain.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryJokeRepositoryTest {

    private InMemoryJokeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryJokeRepository();
    }

    @Test
    @DisplayName("Should return all jokes on findAll")
    void shouldReturnAllJokesOnFindAll() {
        List<Joke> jokes = repository.findAll();

        assertThat(jokes).isNotEmpty();
        assertThat(jokes).hasSizeGreaterThan(0);
    }

    @Test
    @DisplayName("Should return joke by valid id")
    void shouldReturnJokeByValidId() {
        Optional<Joke> joke = repository.findById(1L);

        assertThat(joke).isPresent();
        assertThat(joke.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should return empty optional for nonexistent id")
    void shouldReturnEmptyOptionalForNonexistentId() {
        Optional<Joke> joke = repository.findById(999L);

        assertThat(joke).isEmpty();
    }

    @Test
    @DisplayName("Should return correct count")
    void shouldReturnCorrectCount() {
        long count = repository.count();
        List<Joke> all = repository.findAll();

        assertThat(count).isEqualTo(all.size());
    }

    @Test
    @DisplayName("Should return defensive copy on findAll")
    void shouldReturnDefensiveCopyOnFindAll() {
        List<Joke> jokes1 = repository.findAll();
        List<Joke> jokes2 = repository.findAll();

        assertThat(jokes1).isNotSameAs(jokes2);
    }

    @Test
    @DisplayName("Should have all jokes with non-null fields")
    void shouldHaveAllJokesWithNonNullFields() {
        List<Joke> jokes = repository.findAll();

        jokes.forEach(joke -> {
            assertThat(joke.getId()).isNotNull();
            assertThat(joke.getSetup()).isNotBlank();
            assertThat(joke.getPunchline()).isNotBlank();
            assertThat(joke.getCategory()).isNotBlank();
        });
    }
}
