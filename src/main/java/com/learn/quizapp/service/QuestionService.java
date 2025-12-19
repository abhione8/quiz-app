package com.learn.quizapp.service;

import com.learn.quizapp.dao.QuestionDAO;
import com.learn.quizapp.model.Question;
import com.learn.quizapp.model.QuestionCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO  questionDAO;

    public List<Question> getAllQuestions() {
        return questionDAO.findAll();
    }

    public List<Question> getQuestionsByCategory(QuestionCategory category) {
        return questionDAO.findByCategory(category);
    }

    @Transactional
    public void addQuestion(Question question) {
        questionDAO.save(question);
    }
}
