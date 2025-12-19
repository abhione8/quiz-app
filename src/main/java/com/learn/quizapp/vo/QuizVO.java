package com.learn.quizapp.vo;

import lombok.Data;

@Data
public class QuizVO {
    String title;
    String difficultyLevel;
    String category;
    int numberOfQuestions;
}
