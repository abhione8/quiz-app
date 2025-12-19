package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="DEV_QUESTION")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String answer;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @Enumerated(EnumType.STRING)
    private QuestionDifficultyLevel difficultyLevel;
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;
}
