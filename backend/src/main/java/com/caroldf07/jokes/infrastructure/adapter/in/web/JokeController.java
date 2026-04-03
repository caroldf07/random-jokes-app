package com.caroldf07.jokes.infrastructure.adapter.in.web;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.in.GetAllJokesUseCase;
import com.caroldf07.jokes.domain.port.in.GetRandomJokeUseCase;
import com.caroldf07.jokes.domain.port.in.SaveJokeUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    private final GetRandomJokeUseCase getRandomJokeUseCase;
    private final GetAllJokesUseCase getAllJokesUseCase;
    private final SaveJokeUseCase saveJokeUseCase;

    public JokeController(GetRandomJokeUseCase getRandomJokeUseCase,
                          GetAllJokesUseCase getAllJokesUseCase,
                          SaveJokeUseCase saveJokeUseCase) {
        this.getRandomJokeUseCase = getRandomJokeUseCase;
        this.getAllJokesUseCase = getAllJokesUseCase;
        this.saveJokeUseCase = saveJokeUseCase;
    }

    @GetMapping("/random")
    public ResponseEntity<JokeResponse> getRandomJoke() {
        return ResponseEntity.ok(new JokeResponse(getRandomJokeUseCase.getRandomJoke()));
    }

    @GetMapping
    public ResponseEntity<List<JokeResponse>> getAllJokes() {
        List<JokeResponse> responses = getAllJokesUseCase.getAllJokes()
                .stream()
                .map(JokeResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<JokeResponse> saveJoke(@RequestBody SaveJokeRequest request) {
        Joke joke = new Joke(null, request.getSetup(), request.getPunchline(), request.getCategory());
        Joke saved = saveJokeUseCase.saveJoke(joke);
        return ResponseEntity.status(HttpStatus.CREATED).body(new JokeResponse(saved));
    }
}
