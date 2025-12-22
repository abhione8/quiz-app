package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "DEV_QUESTION")
@Getter
@Setter
@SequenceGenerator(
        name = "base_seq_gen",
        sequenceName = "DEV_QUESTION_SEQ",
        allocationSize = 1
)
public class Question extends BaseEntity{

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
