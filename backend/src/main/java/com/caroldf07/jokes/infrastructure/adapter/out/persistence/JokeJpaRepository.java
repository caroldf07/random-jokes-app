package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeJpaRepository extends JpaRepository<JokeEntity, Long> {
}
