package com.learn.quizapp.dao;

import com.learn.quizapp.model.Question;
import com.learn.quizapp.model.QuestionCategory;
import com.learn.quizapp.model.QuestionDifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionDAO extends JpaRepository<Question,Long> {

    List<Question> findByCategory(QuestionCategory category);

    List<Question> findByCategoryAndDifficultyLevel(QuestionCategory questionCategory, QuestionDifficultyLevel questionDifficultyLevel);
}
