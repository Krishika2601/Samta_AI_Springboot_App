# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.12/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.12/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.12/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.12/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)



# Help

## Project Description

This Spring Boot application for random questions. The application fetches random questions from an external API, stores them in a database, and provides endpoints to answer the questions.

## API Endpoints

### Fetch Random Questions

- Endpoint: GET /questions/fetch-questions
- Description: This endpoint fetches 5 random questions from an external API and saves them in the database. It is recommended to call this endpoint before other endpoints to ensure a fresh set of questions.
- Response: "Random questions fetched and saved successfully."

### Play

- Endpoint: GET /questions/play
- Description: This endpoint retrieves a random question from the database to play. It provides the question_id and question_text to display to the user.
- Response:{
"question_id": <question_id>,
"question": <question_text>
}

### Next

- Endpoint: POST /questions/next
- Description: This endpoint allows the user to submit the answer to the current question and retrieves the next question. The question_id and answer should be provided in the request body.
- Request Body:

  ```
  
{
"question_id": <question_id>,
"answer": <answer>
}
  
  ```

- Response:
- If the answer is correct:
  ```
  {
    "correct_answer": <correct_answer>,
    "next_question": {
      "question_id": <question_id>,
      "question": <question_text>
    }
  }
  ```
- If the answer is incorrect:
  ```
  {
    "correct_answer": "Incorrect",
    "next_question": null
  }
  ```

## Error Handling

- If an error occurs while fetching questions from the external API, a RuntimeException will be thrown with an appropriate error message. It is recommended to handle this exception and provide an error response to the client.
- If a question with the specified question_id is not found in the database, a 404 Not Found response will be returned. It is important to handle this case and provide a meaningful error message to the user.
- If the request body for the Next endpoint is invalid or missing required fields, a 400 Bad Request response will be returned. It is necessary to validate the request body and handle this case gracefully.

## Getting Started

To start using the application, follow these steps:
1. Ensure that the application is running and the necessary dependencies are installed.
2. Call the /questions/fetch-questions endpoint to fetch random questions and populate the database.
3. Use the /questions/play endpoint to retrieve a question and display it to the user.
4. Allow the user to submit the answer using the /questions/next endpoint and display the result.
5. Repeat steps 3 and 4 for subsequent questions until the game is complete.
