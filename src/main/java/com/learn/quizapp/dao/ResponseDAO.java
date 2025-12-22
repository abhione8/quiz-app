package com.learn.quizapp.dao;

import com.learn.quizapp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseDAO extends JpaRepository<Response, Long> {
}
