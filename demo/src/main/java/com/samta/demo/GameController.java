package com.samta.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class GameController {
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    public GameController(QuestionService questionService, QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    /**
     * API endpoint to fetch a random question to play.
     *
     * @return ResponseEntity with the question and its ID.
     */
    @GetMapping("/play")
    public ResponseEntity<Map<String, Object>> play() {
        try {
            List<Question> questions = questionService.fetchAndSaveRandomQuestions();
            if (!questions.isEmpty()) {
                Question question = questions.get(0);
                Map<String, Object> response = new HashMap<>();
                response.put("question_id", question.getId());
                response.put("question", question.getQuestion());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * API endpoint to evaluate the answer for a given question and provide the next question.
     *
     * @param payload Request body containing the question ID and the answer.
     * @return ResponseEntity with the evaluation result and the next question if available.
     */
    @PostMapping("/next")
    public ResponseEntity<Map<String, Object>> next(@RequestBody Map<String, Object> payload) {
        try {
            Long questionId = Long.parseLong(payload.get("question_id").toString());
            String answer = payload.get("answer").toString();

            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (optionalQuestion.isPresent()) {
                Question question = optionalQuestion.get();
                Map<String, Object> response = new HashMap<>();

                if (question.getAnswer().equalsIgnoreCase(answer)) {
                    response.put("correct_answer", question.getAnswer());

                    List<Question> questions = questionService.fetchAndSaveRandomQuestions();
                    if (!questions.isEmpty()) {
                        Question nextQuestion = questions.get(0);
                        response.put("next_question", createQuestionResponse(nextQuestion));
                    } else {
                        response.put("next_question", null);
                    }
                } else {
                    response.put("correct_answer", "Incorrect");
                    response.put("next_question", null);
                }

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * API endpoint to manually fetch and save random questions from the external API.
     *
     * @return ResponseEntity with a success message.
     */
    @GetMapping("/fetch-questions")
    public ResponseEntity<String> fetchQuestions() {
        try {
            List<Question> questions = questionService.fetchAndSaveRandomQuestions();
            return ResponseEntity.ok("Random questions fetched and saved successfully.");
        } catch (Exception e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Helper method to create a response map for a given question.
     *
     * @param question The question object.
     * @return The response map containing the question and its ID.
     */
    

    private Map<String, Object> createQuestionResponse(Question question) {
        Map<String, Object> questionResponse = new HashMap<>();
        questionResponse.put("question_id", question.getId());
        questionResponse.put("question", question.getQuestion());
        return questionResponse;
    }
}
