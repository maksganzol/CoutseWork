package sample.services;

import sample.repositories.StudentMarkRepository;
import sample.repositories.TeacherRepository;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;
import sample.models.Teacher;

import java.util.List;

public class TeacherService {
    private TeacherRepository repository;
    private StudentMarkRepository studentMarkRepository;

    public TeacherService(){
        repository = new TeacherRepository();
        studentMarkRepository = new StudentMarkRepository();
    }

    public Teacher getTeacher(String login){
        return repository.getTeacherByLogin(login);
    }
    public void addTeacher(Teacher teacher) throws UserAlreadyExistsException {
        repository.addTeacher(teacher);
    }
    public boolean authorized(Teacher teacher){
        Teacher t = repository.getTeacherByLogin(teacher.getLogin());
        if(t==null) return false;
        boolean auth = teacher.getPassword().equals(t.getPassword());
        return auth;
    }

    public List<Student> getAllStudents(){
        return studentMarkRepository.getAll();
    }

    public Student getStudent(String login){
        for(Student st: studentMarkRepository.getAll()) {
            if (st.getLogin().equals(login))
                return st;
        }
        return null;
    }
}
