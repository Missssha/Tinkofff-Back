package edu.java.scrapper.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({WireMockExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StackOverFlowControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getQuestionById_success() {
        // WireMock stubbing
        String mockResponse = "{\"items\": [{\"id\": 123, \"title\": \"Test Question\"}]}";
        stubFor(get(urlEqualTo("/questions/456"))
            .withQueryParam("order", equalTo("desc"))
            .withQueryParam("sort", equalTo("activity"))
            .withQueryParam("site", equalTo("stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(mockResponse)));

        // Perform the request
        StackOverFlowResponse response = restTemplate.getForObject("/questions/456", StackOverFlowResponse.class);

        // Verify the response
        assertNotNull(response);
        List<StackOverFlowQuestion> questions = response.getItems();
        assertEquals(1, questions.size());
        assertEquals("Test Question", questions.get(0).getTitle());
    }

    // Add more tests for error scenarios, edge cases, and additional controller methods
}
