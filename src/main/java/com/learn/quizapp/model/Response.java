package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DEV_RESPONSE")
@SequenceGenerator(
        name = "base_seq_gen",
        sequenceName = "DEV_RESPONSE_SEQ",
        allocationSize = 1
)
@Getter
@Setter
public class Response extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String username;
    private String quizTitle;
    private int score;
}

