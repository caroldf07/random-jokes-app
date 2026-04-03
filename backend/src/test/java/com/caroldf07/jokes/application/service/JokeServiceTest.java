package com.caroldf07.jokes.application.service;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.out.ExternalJokeApiPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JokeServiceTest {

    @Mock
    private ExternalJokeApiPort externalJokeApiPort;

    @Mock
    private JokeRepositoryPort jokeRepository;

    private JokeService jokeService;

    private Joke joke1;
    private Joke joke2;

    @BeforeEach
    void setUp() {
        jokeService = new JokeService(externalJokeApiPort, jokeRepository);
        joke1 = new Joke(1L, 10, "Por que o livro estava triste?", "Tinha muitos problemas!", "Português");
        joke2 = new Joke(2L, 11, "O que o zero disse ao oito?", "Que cinto bonito!", "Português");
    }

    // --- getRandomJoke ---

    @Test
    @DisplayName("Should return joke from external API")
    void shouldReturnJokeFromExternalApi() {
        when(externalJokeApiPort.fetchRandomJoke()).thenReturn(joke1);

        Joke result = jokeService.getRandomJoke();

        assertThat(result).isEqualTo(joke1);
        verify(externalJokeApiPort).fetchRandomJoke();
        verifyNoInteractions(jokeRepository);
    }

    @Test
    @DisplayName("Should propagate exception when external API fails")
    void shouldPropagateExceptionWhenExternalApiFails() {
        when(externalJokeApiPort.fetchRandomJoke())
                .thenThrow(new RuntimeException("API unavailable"));

        assertThatThrownBy(() -> jokeService.getRandomJoke())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("API unavailable");
    }

    // --- getAllJokes ---

    @Test
    @DisplayName("Should return all saved jokes from repository")
    void shouldReturnAllSavedJokes() {
        List<Joke> expected = Arrays.asList(joke1, joke2);
        when(jokeRepository.findAll()).thenReturn(expected);

        List<Joke> result = jokeService.getAllJokes();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(joke1, joke2);
    }

    @Test
    @DisplayName("Should return empty list when no jokes saved")
    void shouldReturnEmptyListWhenNoJokesSaved() {
        when(jokeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Joke> result = jokeService.getAllJokes();

        assertThat(result).isEmpty();
    }

    // --- saveJoke ---

    @Test
    @DisplayName("Should save joke via repository and return saved instance")
    void shouldSaveJokeViaRepository() {
        Joke unsaved = new Joke(null, 42, "Setup?", "Punchline!", "Categoria");
        Joke saved = new Joke(1L, 42, "Setup?", "Punchline!", "Categoria");
        when(jokeRepository.save(unsaved)).thenReturn(saved);

        Joke result = jokeService.saveJoke(unsaved);

        assertThat(result.getId()).isEqualTo(1L);
        verify(jokeRepository).save(unsaved);
    }

    @Test
    @DisplayName("Should delegate save to repository only once")
    void shouldDelegateSaveToRepositoryOnce() {
        Joke joke = new Joke(null, "Setup", "Punchline", "Cat");
        when(jokeRepository.save(any())).thenReturn(new Joke(99L, "Setup", "Punchline", "Cat"));

        jokeService.saveJoke(joke);

        verify(jokeRepository, times(1)).save(joke);
        verifyNoInteractions(externalJokeApiPort);
    }
}
