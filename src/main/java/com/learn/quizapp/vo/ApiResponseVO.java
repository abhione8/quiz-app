package com.learn.quizapp.vo;

import lombok.Data;

@Data
public class ApiResponseVO<T> {
    private String message;
    private T data;

    public ApiResponseVO(String message, T data) {
        this.message = message;
        this.data = data;
    }
}

