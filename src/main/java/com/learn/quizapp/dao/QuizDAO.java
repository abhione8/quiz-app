package com.learn.quizapp.dao;

import com.learn.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizDAO extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByTitle(String title);
}
