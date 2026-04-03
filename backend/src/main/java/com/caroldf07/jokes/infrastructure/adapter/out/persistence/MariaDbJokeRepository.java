package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.out.JokeRepositoryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MariaDbJokeRepository implements JokeRepositoryPort {

    private final JokeJpaRepository jpaRepository;

    public MariaDbJokeRepository(JokeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Joke save(Joke joke) {
        JokeEntity entity = new JokeEntity(joke.getSetup(), joke.getPunchline(), joke.getCategory(), joke.getExternalId());
        JokeEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Joke> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Joke> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private Joke toDomain(JokeEntity entity) {
        return new Joke(entity.getId(), entity.getExternalId(), entity.getSetup(), entity.getPunchline(), entity.getCategory());
    }
}
