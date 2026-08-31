package com.quizapp.model;

import java.util.List;

public class Question {

    private int question_id;
    private int quiz_id;
    private String question_text;

    // Options belonging to this question
    private List<Option> options;

    public Question() {
    }

    public Question(int question_id, int quiz_id, String question_text) {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
        this.question_text = question_text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    // GET OPTIONS
    public List<Option> getOptions() {
        return options;
    }

    // SET OPTIONS
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}