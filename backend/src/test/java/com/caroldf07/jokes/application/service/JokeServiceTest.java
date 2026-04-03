package com.caroldf07.jokes.application.service;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokeServiceTest {

    @Mock
    private JokeRepositoryPort jokeRepository;

    private JokeService jokeService;

    private Joke joke1;
    private Joke joke2;

    @BeforeEach
    void setUp() {
        jokeService = new JokeService(jokeRepository);
        joke1 = new Joke(1L, "Por que o livro estava triste?", "Tinha muitos problemas!", "Português");
        joke2 = new Joke(2L, "O que o zero disse ao oito?", "Que cinto bonito!", "Português");
    }

    @Test
    @DisplayName("Should return a random joke when jokes are available")
    void shouldReturnRandomJokeWhenJokesAreAvailable() {
        when(jokeRepository.findAll()).thenReturn(Arrays.asList(joke1, joke2));

        Joke result = jokeService.getRandomJoke();

        assertThat(result).isIn(joke1, joke2);
    }

    @Test
    @DisplayName("Should throw exception when no jokes available")
    void shouldThrowExceptionWhenNoJokesAvailable() {
        when(jokeRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> jokeService.getRandomJoke())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("No jokes available");
    }

    @Test
    @DisplayName("Should return single joke when only one exists")
    void shouldReturnSingleJokeWhenOnlyOneExists() {
        when(jokeRepository.findAll()).thenReturn(Collections.singletonList(joke1));

        Joke result = jokeService.getRandomJoke();

        assertThat(result).isEqualTo(joke1);
    }

    @Test
    @DisplayName("Should return all jokes")
    void shouldReturnAllJokes() {
        List<Joke> expectedJokes = Arrays.asList(joke1, joke2);
        when(jokeRepository.findAll()).thenReturn(expectedJokes);

        List<Joke> result = jokeService.getAllJokes();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(joke1, joke2);
    }

    @Test
    @DisplayName("Should return empty list when no jokes exist")
    void shouldReturnEmptyListWhenNoJokesExist() {
        when(jokeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Joke> result = jokeService.getAllJokes();

        assertThat(result).isEmpty();
    }
}
