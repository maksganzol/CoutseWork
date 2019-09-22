package sample.services;

import sample.dao.StudentDao;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;

/**
 * Этот класс занимаетсся регисрацией студнта и выдачей итогового документа.
 */
public class StudentService {
    private StudentDao dao;

    public StudentService() {
        dao = new StudentDao();
    }

    public Student getStudent(String login){
        return dao.getStudentByLogin(login);
    }

    public void addStudent(Student student) throws UserAlreadyExistsException {
        dao.addStudent(student);
    }

    public String getTotalStatement(Student student){
        return student.getName() + " " + student.getLastName() + " - total mark: " + student.getTotalMark();
    }


}
