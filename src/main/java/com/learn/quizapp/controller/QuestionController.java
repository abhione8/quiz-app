package com.learn.quizapp.controller;

import com.learn.quizapp.model.Question;
import com.learn.quizapp.model.QuestionCategory;
import com.learn.quizapp.service.QuestionService;
import com.learn.quizapp.vo.ApiResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("all")
    public ResponseEntity<ApiResponseVO<List<Question>>> getQuestions(){
        List<Question> questions =questionService.getAllQuestions();
        return ResponseEntity.ok(
                new ApiResponseVO<>("success", questions)
        );
    }

    @GetMapping("all/{category}")
    public ResponseEntity<ApiResponseVO<List<Question>>> getQuestionsByCategory(@PathVariable("category") QuestionCategory category){
        List<Question> questions = questionService.getQuestionsByCategory(category);
        return ResponseEntity.ok(
                new ApiResponseVO<>("success",questions)
        );
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponseVO<String>> saveQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return ResponseEntity.ok(
                new ApiResponseVO<>("success","Question added successfully.")
        );
    }
}
