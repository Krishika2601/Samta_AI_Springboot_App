package com.samta.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

@Service
public class QuestionService {
    private static final String RANDOM_QUESTIONS_API = "https://jservice.io/api/random?count=5";

    private final RestTemplate restTemplate;
    private final QuestionRepository questionRepository;

    public QuestionService(RestTemplate restTemplate, QuestionRepository questionRepository) {
        this.restTemplate = restTemplate;
        this.questionRepository = questionRepository;
    }

    public List<Question> fetchAndSaveRandomQuestions() {
        // Fetch random questions from the API
        ResponseEntity<QuestionApiResponse[]> response = restTemplate.getForEntity(RANDOM_QUESTIONS_API, QuestionApiResponse[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            // Map the API response to Question entities
            QuestionApiResponse[] questionApiResponses = response.getBody();
            List<Question> questions = Arrays.stream(questionApiResponses)
                    .map(this::mapToQuestionEntity)
                    .collect(Collectors.toList());
            // Save the questions to the database
            return questionRepository.saveAll(questions);
        } else {
            // Throw an exception if failed to fetch questions from the API
            throw new RuntimeException("Failed to fetch questions from the API.");
        }
    }

    private Question mapToQuestionEntity(QuestionApiResponse questionApiResponse) {
        // Map the API response to a Question entity
        Question question = new Question();
        question.setQuestionId(questionApiResponse.getId());
        question.setQuestion(questionApiResponse.getQuestion());
        question.setAnswer(questionApiResponse.getAnswer());
        return question;
    }
}
