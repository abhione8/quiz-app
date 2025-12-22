package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="DEV_USERS")
@SequenceGenerator(
        name = "base_seq_gen",
        sequenceName = "DEV_USERS_SEQ",
        allocationSize = 1
)
public class User extends  BaseEntity {

    private String username;
    private String password;
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Response> responses;

}
