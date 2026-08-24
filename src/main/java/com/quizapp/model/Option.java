package com.quizapp.model;

public class Option {
    private int option_id;
    private int question_id;
    private String option_text;
    private boolean is_correct;

    public Option() {
    }
    public Option(int option_id,int question_id,String option_text,boolean is_correct) {
        this.option_id = option_id;
        this.question_id = question_id;
        this.option_text = option_text;
        this.is_correct = is_correct;
    }

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }


    public String getOption_text() {
        return option_text;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }


    public boolean isIs_correct() {
        return is_correct;
    }

    public void setIs_correct(boolean is_correct) {
        this.is_correct = is_correct;
    }
}