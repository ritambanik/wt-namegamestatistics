package com.willowtree.test.statistics.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Document("metrics")
public class Event {

    private String userId;
    private boolean isCorrect;
    private String questionId;
    private Date occuredAt;

    public Event() {
        occuredAt = Calendar.getInstance().getTime();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


    public String getUserId() {
        return userId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getQuestionId() {
        return questionId;
    }

    public Date getOccuredAt() {
        return occuredAt;
    }
}
