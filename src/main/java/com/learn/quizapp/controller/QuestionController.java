package com.learn.quizapp.controller;

import com.learn.quizapp.model.Question;
import com.learn.quizapp.model.QuestionCategory;
import com.learn.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("all")
    public List<Question> getQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("all/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable("category") QuestionCategory category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public String saveQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return "Question added successfully";
    }
}
