package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryJokeRepository implements JokeRepositoryPort {

    private final List<Joke> jokes;

    public InMemoryJokeRepository() {
        this.jokes = new ArrayList<>();
        loadJokes();
    }

    private void loadJokes() {
        jokes.add(new Joke(1L, "Por que o livro de matemática estava triste?", "Porque tinha muitos problemas!", "Português"));
        jokes.add(new Joke(2L, "O que o zero disse para o oito?", "Que cinto bonito!", "Português"));
        jokes.add(new Joke(3L, "Por que o computador foi ao médico?", "Porque estava com vírus!", "Tecnologia"));
        jokes.add(new Joke(4L, "O que o pato disse para a pata?", "Nada, pato não fala!", "Português"));
        jokes.add(new Joke(5L, "Por que o esqueleto não briga?", "Porque não tem estômago para isso!", "Português"));
        jokes.add(new Joke(6L, "O que o mar disse para a praia?", "Nada, só deu uma onda!", "Português"));
        jokes.add(new Joke(7L, "Por que o professor foi à praia?", "Para corrigir as ondas!", "Português"));
        jokes.add(new Joke(8L, "Why don't scientists trust atoms?", "Because they make up everything!", "English"));
        jokes.add(new Joke(9L, "What do you call a fake noodle?", "An impasta!", "English"));
        jokes.add(new Joke(10L, "Why did the scarecrow win an award?", "Because he was outstanding in his field!", "English"));
        jokes.add(new Joke(11L, "O que é um pontinho preto no teto?", "Uma mosca de capacete!", "Português"));
        jokes.add(new Joke(12L, "Por que o iPhone não usa óculos?", "Porque já tem Siri!", "Tecnologia"));
        jokes.add(new Joke(13L, "O que o Java disse para o Python?", "Você não tem tipo!", "Tecnologia"));
        jokes.add(new Joke(14L, "Por que o programador usa óculos escuros?", "Porque não suporta Java sem sol!", "Tecnologia"));
        jokes.add(new Joke(15L, "Como o programador dorme?", "Ele fecha os olhos e faz um loop!", "Tecnologia"));
    }

    @Override
    public List<Joke> findAll() {
        return new ArrayList<>(jokes);
    }

    @Override
    public Optional<Joke> findById(Long id) {
        return jokes.stream()
                .filter(joke -> joke.getId().equals(id))
                .findFirst();
    }

    @Override
    public long count() {
        return jokes.size();
    }
}
