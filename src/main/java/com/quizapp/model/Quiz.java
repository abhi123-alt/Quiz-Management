package com.quizapp.model;
import java.sql.Timestamp;
public class Quiz {
    private int quiz_id;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private int time_limit;
    private int created_by;
    private Timestamp created_at;

    public Quiz() {
    }

    public Quiz(int quiz_id,
                String title,
                String description,
                String category,
                String difficulty,
                int time_limit,
                int created_by,
                Timestamp created_at) {

        this.quiz_id = quiz_id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.time_limit = time_limit;
        this.created_by = created_by;
        this.created_at = created_at;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime_limit() {
        return time_limit;
    }

    public void setTime_limit(int time_limit) {
        this.time_limit = time_limit;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}