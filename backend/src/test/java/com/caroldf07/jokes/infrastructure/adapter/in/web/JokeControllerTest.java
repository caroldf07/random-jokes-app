package com.caroldf07.jokes.infrastructure.adapter.in.web;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JokeControllerTest {

    @Mock
    private GetRandomJokeUseCase getRandomJokeUseCase;

    @Mock
    private GetAllJokesUseCase getAllJokesUseCase;

    private JokeController jokeController;

    private Joke joke1;
    private Joke joke2;

    @BeforeEach
    void setUp() {
        jokeController = new JokeController(getRandomJokeUseCase, getAllJokesUseCase);
        joke1 = new Joke(1L, "Por que o livro estava triste?", "Tinha muitos problemas!", "Português");
        joke2 = new Joke(2L, "O que o zero disse ao oito?", "Que cinto bonito!", "Português");
    }

    @Test
    @DisplayName("Should return 200 with a random joke")
    void shouldReturn200WithRandomJoke() {
        when(getRandomJokeUseCase.getRandomJoke()).thenReturn(joke1);

        ResponseEntity<JokeResponse> response = jokeController.getRandomJoke();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getSetup()).isEqualTo("Por que o livro estava triste?");
        assertThat(response.getBody().getPunchline()).isEqualTo("Tinha muitos problemas!");
        assertThat(response.getBody().getCategory()).isEqualTo("Português");
    }

    @Test
    @DisplayName("Should return 200 with all jokes")
    void shouldReturn200WithAllJokes() {
        when(getAllJokesUseCase.getAllJokes()).thenReturn(Arrays.asList(joke1, joke2));

        ResponseEntity<List<JokeResponse>> response = jokeController.getAllJokes();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    @DisplayName("Should return all jokes with correct mapping")
    void shouldReturnAllJokesWithCorrectMapping() {
        when(getAllJokesUseCase.getAllJokes()).thenReturn(Arrays.asList(joke1, joke2));

        ResponseEntity<List<JokeResponse>> response = jokeController.getAllJokes();

        assertThat(response.getBody()).isNotNull();
        JokeResponse first = response.getBody().get(0);
        assertThat(first.getId()).isEqualTo(1L);
        assertThat(first.getSetup()).isEqualTo("Por que o livro estava triste?");
    }
}
