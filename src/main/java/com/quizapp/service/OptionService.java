package com.quizapp.service;

import com.quizapp.dao.OptionDAO;
import com.quizapp.model.Option;

import java.sql.SQLException;
import java.util.List;

public class OptionService {
    private final OptionDAO optionDAO;
    public OptionService() throws SQLException {
        optionDAO = new OptionDAO();
    }

    // ADD OPTION
    public boolean addOption(Option option) {
        return optionDAO.addOption(option);
    }

    // GET OPTIONS BY QUESTION ID
    public List<Option> getOptionsByQuestionId(int question_id) {
        return optionDAO.getOptionsByQuestionId(question_id);
    }

    // GET OPTION BY ID
    public Option getOptionById(int option_id) {
        return optionDAO.getOptionById(option_id);
    }

    // DELETE OPTIONS
    public boolean deleteOptionsByQuestionId(int question_id) {
        return optionDAO.deleteOptionsByQuestionId(question_id);
    }
}