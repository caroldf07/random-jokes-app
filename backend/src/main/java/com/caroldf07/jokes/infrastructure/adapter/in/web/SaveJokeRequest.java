package com.caroldf07.jokes.infrastructure.adapter.in.web;

public class SaveJokeRequest {

    private String setup;
    private String punchline;
    private String category;

    public SaveJokeRequest() {
    }

    public SaveJokeRequest(String setup, String punchline, String category) {
        this.setup = setup;
        this.punchline = punchline;
        this.category = category;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getPunchline() {
        return punchline;
    }

    public void setPunchline(String punchline) {
        this.punchline = punchline;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
