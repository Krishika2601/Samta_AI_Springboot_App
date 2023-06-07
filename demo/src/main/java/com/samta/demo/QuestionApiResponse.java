package com.samta.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionApiResponse {
    private String id;
    private String question;
    private String answer;

    public QuestionApiResponse(
            @JsonProperty("id") String id,
            @JsonProperty("question") String question,
            @JsonProperty("answer") String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
