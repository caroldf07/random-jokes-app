package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import com.caroldf07.jokes.domain.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MariaDbJokeRepositoryTest {

    @Autowired
    private JokeJpaRepository jpaRepository;

    private MariaDbJokeRepository repository;

    @BeforeEach
    void setUp() {
        jpaRepository.deleteAll();
        repository = new MariaDbJokeRepository(jpaRepository);
    }

    @Test
    @DisplayName("Should save a joke and return it with generated id")
    void shouldSaveJokeAndReturnWithGeneratedId() {
        Joke joke = new Joke(null, 42, "Setup da piada?", "Punchline da piada!", "Português");

        Joke saved = repository.save(joke);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getSetup()).isEqualTo("Setup da piada?");
        assertThat(saved.getPunchline()).isEqualTo("Punchline da piada!");
        assertThat(saved.getCategory()).isEqualTo("Português");
        assertThat(saved.getExternalId()).isEqualTo(42);
    }

    @Test
    @DisplayName("Should find saved joke by id")
    void shouldFindSavedJokeById() {
        Joke saved = repository.save(new Joke(null, "Setup?", "Punchline!", "Cat"));

        Optional<Joke> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getSetup()).isEqualTo("Setup?");
    }

    @Test
    @DisplayName("Should return empty optional for nonexistent id")
    void shouldReturnEmptyForNonexistentId() {
        Optional<Joke> found = repository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should return all saved jokes")
    void shouldReturnAllSavedJokes() {
        repository.save(new Joke(null, "Setup 1?", "Punchline 1!", "Cat"));
        repository.save(new Joke(null, "Setup 2?", "Punchline 2!", "Cat"));

        List<Joke> all = repository.findAll();

        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("Should return correct count")
    void shouldReturnCorrectCount() {
        repository.save(new Joke(null, "Setup?", "Punchline!", "Cat"));
        repository.save(new Joke(null, "Setup 2?", "Punchline 2!", "Cat"));

        long count = repository.count();

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return empty list when no jokes saved")
    void shouldReturnEmptyWhenNothingSaved() {
        List<Joke> all = repository.findAll();

        assertThat(all).isEmpty();
    }
}
