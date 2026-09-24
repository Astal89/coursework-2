package org.skypro.examinerservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.examinerservice.domain.Question;
import org.skypro.examinerservice.exception.NotEnoughQuestionsException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceTest {

    private static final Question Q1 = new Question("Q1", "A1");
    private static final Question Q2 = new Question("Q2", "A2");
    private static final Question Q3 = new Question("Q3", "A3");

    @Mock
    private JavaQuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    // вернуть точное количество запрошенных вопросов
    @Test
    void getQuestions_shouldReturnExactAmountWhenAmountLessThanSize() {
        when(questionService.getAll()).thenReturn(List.of(Q1, Q2, Q3));

        Collection<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2);
    }

    @Test
    void getQuestions_shouldReturnAllWhenAmountEqualsSize() {
        when(questionService.getAll()).thenReturn(List.of(Q1, Q2, Q3));

        Collection<Question> result = examinerService.getQuestions(3);

        assertThat(result).containsExactlyInAnyOrder(Q1, Q2, Q3);
    }

    // выбросить исключение если запрошено больше вопросов чем есть
    @Test
    void getQuestions_shouldThrowWhenAmountGreaterThanSize() {
        when(questionService.getAll()).thenReturn(List.of(Q1, Q2, Q3));
        assertThatThrownBy(() -> examinerService.getQuestions(5))
                .isInstanceOf(NotEnoughQuestionsException.class);
    }

    // запрошено 0 вопросов
    @Test
    void getQuestions_shouldThrowWhenAmountIsZero() {
        when(questionService.getAll()).thenReturn(List.of(Q1));

        assertThatThrownBy(() -> examinerService.getQuestions(0))
                .isInstanceOf(NotEnoughQuestionsException.class);
    }

    // запрошено отрицательное количество вопросов
    @Test
    void getQuestions_shouldThrowWhenAmountIsLessThanZero() {
        when(questionService.getAll()).thenReturn(List.of(Q1));

        assertThatThrownBy(() -> examinerService.getQuestions(-1))
                .isInstanceOf(NotEnoughQuestionsException.class);
    }
}
