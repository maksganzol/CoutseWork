package sample.services;

import sample.repositories.TestQuestionRepository;
import sample.models.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Этот класс предоставляет тестовые воросы и эталонные ответы к ним.
 */
public class TestQuestionService {
    private TestQuestionRepository repository;

    public TestQuestionService() {
        repository = new TestQuestionRepository();
    }

    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();
        Question question;
        while((question = repository.getQuestion())!=null){
            questions.add(question);
        }
        return questions;
    }

    public List<Question> getByTheme(String theme){
        List<Question> questions = new ArrayList<>();
        for(Question q: getAllQuestions())
            if(q.getTheme().equals(theme))
                questions.add(q);
        return questions;
    }


}
