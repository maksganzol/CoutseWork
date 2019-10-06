package sample.services;

import sample.repositories.StudentRepository;
import sample.repositories.TeacherRepository;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;
import sample.models.Teacher;

import java.util.List;

public class TeacherService {
    private TeacherRepository repository;
    private StudentRepository studentRepository;

    public TeacherService(){
        repository = new TeacherRepository();
        studentRepository = new StudentRepository();
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
        return teacher.getPassword().equals(t.getPassword());
    }

    public List<Student> getAllStudents(){
        return studentRepository.getAll();
    }

    public Student getStudent(String login){
        for(Student st: studentRepository.getAll()) {
            if (st.getLogin().equals(login))
                return st;
        }
        return null;
    }
}
