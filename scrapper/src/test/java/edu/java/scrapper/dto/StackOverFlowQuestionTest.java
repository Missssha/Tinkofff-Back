package edu.java.scrapper.dto;

import edu.java.dto.GitHubRepository;
import edu.java.dto.StackOverFlowQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StackOverFlowQuestionTest {

    @Mock
    private StackOverFlowQuestion stackOverFlowQuestion;

    @Test
    void gettersAndSetters() {
        stackOverFlowQuestion = new StackOverFlowQuestion(123L, true, "Test Question");

        assertEquals(123L, stackOverFlowQuestion.getQuestionId());
        assertTrue(stackOverFlowQuestion.isAnswered());
        assertEquals("Test Question", stackOverFlowQuestion.getTitle());

        stackOverFlowQuestion.setQuestionId(456L);
        stackOverFlowQuestion.setAnswered(false);
        stackOverFlowQuestion.setTitle("Updated Title");

        assertEquals(456L, stackOverFlowQuestion.getQuestionId());
        assertFalse(stackOverFlowQuestion.isAnswered());
        assertEquals("Updated Title", stackOverFlowQuestion.getTitle());
    }

    @Test
    void equalsAndHashCode() {
        StackOverFlowQuestion question1 = new StackOverFlowQuestion(123L, true, "Test Question");
        StackOverFlowQuestion question2 = new StackOverFlowQuestion(456L, false, "Different Title");


        assertNotEquals(question1, question2);
        assertNotEquals(question1.hashCode(), question2.hashCode());
    }

}
