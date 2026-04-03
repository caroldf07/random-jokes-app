package com.caroldf07.jokes.infrastructure.adapter.out.api;

import com.caroldf07.jokes.domain.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.hamcrest.Matchers.containsString;

class JokeApiAdapterTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private JokeApiAdapter jokeApiAdapter;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        jokeApiAdapter = new JokeApiAdapter(restTemplate, "https://v2.jokeapi.dev");
    }

    @Test
    @DisplayName("Should fetch and map joke from external API correctly")
    void shouldFetchAndMapJokeFromApi() {
        String responseBody = "{\"error\":false,\"category\":\"Programming\",\"type\":\"twopart\"," +
                "\"setup\":\"Why do Java developers wear glasses?\"," +
                "\"delivery\":\"Because they don't C#!\"," +
                "\"id\":26,\"safe\":true,\"lang\":\"en\"}";

        mockServer.expect(requestTo(containsString("/joke/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        Joke joke = jokeApiAdapter.fetchRandomJoke();

        mockServer.verify();
        assertThat(joke.getSetup()).isEqualTo("Why do Java developers wear glasses?");
        assertThat(joke.getPunchline()).isEqualTo("Because they don't C#!");
        assertThat(joke.getCategory()).isEqualTo("Programming");
        assertThat(joke.getExternalId()).isEqualTo(26);
        assertThat(joke.getId()).isNull();
    }

    @Test
    @DisplayName("Should throw exception when API returns error flag true")
    void shouldThrowExceptionWhenApiReturnsError() {
        String errorResponse = "{\"error\":true,\"message\":\"No jokes found\",\"causedBy\":[\"No matching joke found\"]}";

        mockServer.expect(requestTo(containsString("/joke/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(errorResponse, MediaType.APPLICATION_JSON));

        assertThatThrownBy(() -> jokeApiAdapter.fetchRandomJoke())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to fetch joke");
    }

    @Test
    @DisplayName("Should include safe-mode and twopart filters in request URL")
    void shouldIncludeSafeModeAndTwopartInUrl() {
        String responseBody = "{\"error\":false,\"category\":\"General\",\"type\":\"twopart\"," +
                "\"setup\":\"Test setup\",\"delivery\":\"Test delivery\",\"id\":1,\"safe\":true,\"lang\":\"en\"}";

        mockServer.expect(requestTo(containsString("type=twopart")))
                .andExpect(requestTo(containsString("safe-mode")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        jokeApiAdapter.fetchRandomJoke();

        mockServer.verify();
    }
}
