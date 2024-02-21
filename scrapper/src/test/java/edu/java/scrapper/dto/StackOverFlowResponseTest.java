package edu.java.scrapper.dto;

import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StackOverFlowResponseTest {

    @Test
    void gettersAndSetters() {
        List<StackOverFlowQuestion> questions = Collections.singletonList(new StackOverFlowQuestion(123L, true, "Test Question"));
        StackOverFlowResponse response = new StackOverFlowResponse();
        response.setItems(questions);

        assertEquals(questions, response.getItems());

        response.setItems(null);
        assertNull(response.getItems());
    }

    @Test
    void constructorWithNullList() {
        StackOverFlowResponse response = new StackOverFlowResponse();
        assertNull(response.getItems());
    }


}
