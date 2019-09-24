package sample.services;

import sample.repositories.StudentRepository;
import sample.repositories.StudentMarkRepository;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;

/**
 * Этот класс занимаетсся регисрацией студнта и выдачей итогового документа.
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

    public void addStudent(Student student) throws UserAlreadyExistsException {
        repository.addStudent(student);
    }

    public String getTotalStatement(Student student){
        for(Student st: markRepository.getAll()) {
            if(st.equals(student))
                return student.getName() + " " + student.getLastName() + " - total mark: " + student.getTotalMark() +
                        ". Total time: " + getTotalTime() + "mls";
        }
        return "Not found";
    }

    private Long getTotalTime() {
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
}
