package org.skypro.examinerservice.service;

import org.skypro.examinerservice.domain.Question;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new HashSet<>();
    private Random random = new Random();

    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    public Question add(Question question) {
        questions.add(question);
        return question;
    }

    public Question remove(Question question) {
        if (questions.remove(question)) {
            return question;
        }
        return null;
    }

    public Collection<Question> getAll() {
        return Collections.unmodifiableSet(questions);
    }

    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        List<Question> list = new ArrayList<>(questions);
        return list.get(random.nextInt(list.size()));
    }
}
