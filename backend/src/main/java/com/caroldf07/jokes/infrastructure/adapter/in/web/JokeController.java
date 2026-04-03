package com.caroldf07.jokes.infrastructure.adapter.in.web;

import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    private final GetRandomJokeUseCase getRandomJokeUseCase;
    private final GetAllJokesUseCase getAllJokesUseCase;

    public JokeController(GetRandomJokeUseCase getRandomJokeUseCase,
                          GetAllJokesUseCase getAllJokesUseCase) {
        this.getRandomJokeUseCase = getRandomJokeUseCase;
        this.getAllJokesUseCase = getAllJokesUseCase;
    }

    @GetMapping("/random")
    public ResponseEntity<JokeResponse> getRandomJoke() {
        JokeResponse response = new JokeResponse(getRandomJokeUseCase.getRandomJoke());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JokeResponse>> getAllJokes() {
        List<JokeResponse> responses = getAllJokesUseCase.getAllJokes()
                .stream()
                .map(JokeResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
