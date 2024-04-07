package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class StackOverFlowQuestion {
    @JsonProperty("question_id")
    private Long questionId;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    private String title;

    @JsonProperty("last_activity_date")
    private Long lastActivity;

    @JsonProperty("answer_count")
    private Long answerCount;

    private Long commentCount;

    public Timestamp getLastActivityAsTimestamp() {

        return Timestamp.valueOf(Instant.ofEpochMilli(
                lastActivity * Integer.parseInt(System.getenv("SecondToMilliSecond")))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime());
    }
}
