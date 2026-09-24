package org.skypro.examinerservice.controller;

import org.skypro.examinerservice.exception.NotEnoughQuestionsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JavaQuestionControllerAdvice {

    @ExceptionHandler(NotEnoughQuestionsException.class)
    public ResponseEntity<String> handleNotEnoughQuestions(NotEnoughQuestionsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}