package org.skypro.examinerservice.service;

import org.skypro.examinerservice.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getQuestions(int amount);
}
