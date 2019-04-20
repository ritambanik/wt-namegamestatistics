package com.willowtree.test.statistics.data;

public class UserStatistics {

    private final String userId;
    private int numOfQuestions;
    private long numOfCorrectAttempts;
    private long numOfIncorrectAttempts;
    private double avgTimePerQuestion;

    public UserStatistics(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public long getNumOfCorrectAttempts() {
        return numOfCorrectAttempts;
    }

    public void setNumOfCorrectAttempts(long numOfCorrectAttempts) {
        this.numOfCorrectAttempts = numOfCorrectAttempts;
    }

    public long getNumOfIncorrectAttempts() {
        return numOfIncorrectAttempts;
    }

    public void setNumOfIncorrectAttempts(long numOfIncorrectAttempts) {
        this.numOfIncorrectAttempts = numOfIncorrectAttempts;
    }

    public double getAvgTimePerQuestion() {
        return avgTimePerQuestion;
    }

    public void setAvgTimePerQuestion(double avgTimePerQuestion) {
        this.avgTimePerQuestion = avgTimePerQuestion;
    }
}
