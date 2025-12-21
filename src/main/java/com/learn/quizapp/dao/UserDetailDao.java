package com.learn.quizapp.dao;

import com.learn.quizapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailDao extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
}
