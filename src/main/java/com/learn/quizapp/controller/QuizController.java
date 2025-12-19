package com.learn.quizapp.controller;

import com.learn.quizapp.vo.QuestionVO;
import com.learn.quizapp.vo.QuizVO;
import com.learn.quizapp.service.QuizService;
import com.learn.quizapp.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController("quiz")
public class QuizController {

    @Autowired
    QuizService  quizService;

    @PostMapping("create")
    public String createQuiz(@RequestBody QuizVO quizVO){
        quizService.createQuiz(quizVO);
        return "Quiz created";
    }

    @GetMapping("get/{id}")
    public List<QuestionVO> getQuiz(@PathVariable("id") Long id){
        return quizService.getQuestions(id);
    }

    @PostMapping("submit/{id}")
    public String submit(@PathVariable Long id, @RequestBody List<ResponseVO> response){
        return quizService.calculateScore(id,response);
    }
}
