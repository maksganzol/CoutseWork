package sample.services;

import sample.dao.StudyMaterialDao;
import sample.dao.TestQuestionDao;
import sample.models.Question;

/**
 * Этот класс предоставляет ввод учебного материала,
 * тестовых вопросов и эталонных ответов к ним.
 */
public class MaterialInputService {

    private StudyMaterialDao studyMaterialDao;
    private TestQuestionDao testQuestionDao;

    public MaterialInputService() {
        studyMaterialDao = new StudyMaterialDao();
        testQuestionDao = new TestQuestionDao();
    }

    public void addQuestion(Question question){
        testQuestionDao.addQuestion(question);
    }

    public void addPortionOfMaterial(String title, String content){
        studyMaterialDao.addPortionOfMaterial(title, content);
    }
}
