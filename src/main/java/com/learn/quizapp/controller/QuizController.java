package com.learn.quizapp.controller;

import com.learn.quizapp.vo.ApiResponseVO;
import com.learn.quizapp.vo.QuestionVO;
import com.learn.quizapp.vo.QuizVO;
import com.learn.quizapp.service.QuizService;
import com.learn.quizapp.vo.ResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService  quizService;

    @GetMapping("all")
    public ResponseEntity<ApiResponseVO<List<QuizVO>>> getAllQuiz(){
        List<QuizVO> quizes = quizService.getAllQuizes();
        return ResponseEntity.ok(
                new ApiResponseVO<>("success",quizes)
        );
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponseVO<String>> createQuiz(@RequestBody QuizVO quizVO){
        if(quizService.checkDuplicateTitle(quizVO.getTitle())){
            return ResponseEntity.ok(
                    new ApiResponseVO<>("error","Title already exists.")
            );
        }
        quizService.createQuiz(quizVO);
        return ResponseEntity.ok(
                new ApiResponseVO<>("success","Quiz created successfully.")
        );
    }

    @GetMapping("get/{title}")
    public ResponseEntity<ApiResponseVO<List<QuestionVO>>> getQuiz(@PathVariable("title") String title){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success",quizService.getQuestions(title))
        );
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<ApiResponseVO<String>> submit(@PathVariable Long id, @RequestBody List<ResponseVO> response, HttpServletRequest req){
        return ResponseEntity.ok(
                new ApiResponseVO<>("success",quizService.calculateScore(id,response,req.getUserPrincipal().getName()))
        );
    }
}
