package org.skypro.examinerservice.service;

import org.skypro.examinerservice.domain.Question;
import org.skypro.examinerservice.exception.NotEnoughQuestionsException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Random random = new Random();
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        List<Question> all = new ArrayList<>(questionService.getAll());
        if (amount > all.size() || amount <= 0) {
            throw new NotEnoughQuestionsException(
                    "Запрошено " + amount + " вопросов, а доступно только " + all.size());
        }
        Collections.shuffle(all, random);
        return all.subList(0, amount);
    }
}
