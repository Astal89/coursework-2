package org.skypro.examinerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.examinerservice.domain.Question;
import org.skypro.examinerservice.exception.NotEnoughQuestionsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


class JavaQuestionServiceTest {

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new JavaQuestionService();
    }

    // работа с пустой коллекцией
    @Test
    void getAll_shouldReturnEmptyCollectionOnEmptyService() {
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void getRandomQuestion_shouldReturnNullOnEmptyService() {
        assertThat(questionService.getRandomQuestion()).isNull();
    }

    @Test
    void remove_shouldReturnNullOnEmptyService() {
        Question removed = questionService.remove(new Question("Q1", "A1"));

        assertThat(removed).isNull();
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void remove_shouldReturnQuestionOnNotEmptyService() {
        questionService.add(new Question("Q1", "A1"));
        questionService.add(new Question("Q2", "A2"));
        Question removed = questionService.remove(new Question("Q1", "A1"));

        assertThat(removed).isNotNull();
    }

    // дубликаты
    @Test
    void addByStrings_shouldNotAddDuplicate() {
        questionService.add("Q1", "A1");
        questionService.add("Q1", "A1");

        assertThat(questionService.getAll()).hasSize(1);
    }

    @Test
    void addByQuestion_shouldNotAddDuplicateOfEqualObject() {
        questionService.add(new Question("Q1", "A1"));
        questionService.add(new Question("Q1", "A1"));

        assertThat(questionService.getAll()).hasSize(1);
    }

    // одинаковые вопросы, но разные ответы
    @Test
    void add_shouldTreatSameQuestionDifferentAnswerAsDifferent() {
        questionService.add("Q1", "A1");
        questionService.add("Q1", "A2");

        assertThat(questionService.getAll()).hasSize(2);
    }

    @Test
    void add_shouldStoreDifferentQuestionsSeparately() {
        questionService.add("Q1", "A1");
        questionService.add("Q2", "A2");

        assertThat(questionService.getAll()).hasSize(2);
    }
}
