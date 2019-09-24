package sample.services;

import sample.repositories.StudyMaterialRepository;
import sample.repositories.TestQuestionRepository;
import sample.models.Question;

/**
 * Этот класс предоставляет ввод учебного материала,
 * тестовых вопросов и эталонных ответов к ним.
 */
public class MaterialInputService {

    private StudyMaterialRepository studyMaterialRepository;
    private TestQuestionRepository testQuestionRepository;

    public MaterialInputService() {
        studyMaterialRepository = new StudyMaterialRepository();
        testQuestionRepository = new TestQuestionRepository();
    }

    public void addQuestion(Question question){
        testQuestionRepository.addQuestion(question);
    }

    public void addPortionOfMaterial(String title, String content){
        studyMaterialRepository.addPortionOfMaterial(title, content);
    }
}
