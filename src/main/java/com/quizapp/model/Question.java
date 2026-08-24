package com.quizapp.model;

public class Question {

    private int question_id;
    private int quiz_id;
    private String question_text;

    public Question() {
    }

    public Question(int question_id,int quiz_id,String question_text) {
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
}