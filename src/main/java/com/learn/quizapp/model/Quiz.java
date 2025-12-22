package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="DEV_QUIZ")
@SequenceGenerator(
        name = "base_seq_gen",
        sequenceName = "DEV_QUIZ_SEQ",
        allocationSize = 1
)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany
    private List<Question> questions;
    @OneToMany(mappedBy = "quiz")
    private List<Response> responses;
}
