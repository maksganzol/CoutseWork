package sample.services;

import sample.exceptions.UserNotFoundException;
import sample.repositories.StudentRepository;
import sample.repositories.StudentMarkRepository;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;

import java.util.List;

/**
 * Этот класс занимаетсся регистрацией студнта и выдачей итогового документа.
 */
public class StudentService {
    private StudentRepository repository;
    private StudentMarkRepository markRepository;
    private Long startStudying = 0L;
    private Long finishStudying = 0L;

    public StudentService() {
        repository = new StudentRepository();
        markRepository = new StudentMarkRepository();
    }

    public Student getStudent(String login){
        return repository.getStudentByLogin(login);
    }

    public void addStudent(Student student) throws UserAlreadyExistsException, UserNotFoundException {
        if(repository.getStudentByLogin(student.getLogin())!=null)
            throw new UserAlreadyExistsException("Student with login " + student.getLogin() + " already exists");
        boolean isRegistred = false;
        for(Student st: repository.getAll()) {
            if (student.getLastName().trim().equals(st.getLastName()) && student.getName().trim().equals(st.getName())) {
                isRegistred = true;
                if(!st.getLogin().trim().equals(""))
                    throw new UserAlreadyExistsException("Student alredy registred");
            }
        }
        if(!isRegistred) throw new UserNotFoundException("There are no student with initials " + student.getName() + " " + student.getLastName());
        repository.logInStudent(student);
    }

    public void addStudentName(String name, String lastName) throws UserAlreadyExistsException {
        for(Student student: repository.getAll()){
            if(student.getLastName().trim().equals(lastName)&&student.getName().trim().equals(name))
                throw new UserAlreadyExistsException("Student with initials" + name + " " + lastName + "already exists");
        }
        repository.addStudent(new Student("", "", name, lastName));
    }

    public String getTotalStatement(Student student){
        for(Student st: markRepository.getAll()) {
            if(st.equals(student))
                return student.getName() + " " + student.getLastName() + " - total mark: " + student.getTotalMark() +
                        ". Total time: " + getTotalTime() + "mls";
        }
        return "Not found";
    }

    public Long getTotalTime() {
        return finishStudying - startStudying;
    }

    public void start(){
        startStudying = System.currentTimeMillis();
    }

    public void finish(){
        finishStudying = System.currentTimeMillis();
    }

    public void addStudentToJournal(Student student) {
        markRepository.addStudent(student);
    }

    public List<Student> getAll(){
        return repository.getAll();
    }

    public void updateStudent(Student student) {
        repository.updateStudent(student);
    }
}
