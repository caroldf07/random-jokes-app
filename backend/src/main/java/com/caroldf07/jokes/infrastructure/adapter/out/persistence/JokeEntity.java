package com.caroldf07.jokes.infrastructure.adapter.out.persistence;

import javax.persistence.*;

@Entity
@Table(name = "jokes")
public class JokeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String setup;

    @Column(nullable = false, length = 500)
    private String punchline;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(name = "external_id")
    private Integer externalId;

    public JokeEntity() {
    }

    public JokeEntity(String setup, String punchline, String category, Integer externalId) {
        this.setup = setup;
        this.punchline = punchline;
        this.category = category;
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public String getCategory() {
        return category;
    }

    public Integer getExternalId() {
        return externalId;
    }
}
