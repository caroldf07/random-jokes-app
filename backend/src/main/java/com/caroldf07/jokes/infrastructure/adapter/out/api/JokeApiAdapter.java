package com.caroldf07.jokes.infrastructure.adapter.out.api;

import com.caroldf07.jokes.domain.model.Joke;
import com.caroldf07.jokes.domain.port.out.ExternalJokeApiPort;
import org.springframework.web.client.RestTemplate;

public class JokeApiAdapter implements ExternalJokeApiPort {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public JokeApiAdapter(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public Joke fetchRandomJoke() {
        String url = baseUrl + "/joke/Any?type=twopart&safe-mode";
        JokeApiResponse response = restTemplate.getForObject(url, JokeApiResponse.class);

        if (response == null || response.isError()) {
            throw new RuntimeException("Failed to fetch joke from external API");
        }

        return new Joke(null, response.getId(), response.getSetup(), response.getDelivery(), response.getCategory());
    }
}
