package sample.repositories;

import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Этот класс позволяет абсрагироваться от работы с хранилищем данных (файлом данных)
 * и позволяет работать сервисам напрямую с объектами сущности (Student)
 */
public class StudentRepository {
    private FileWriter writer;
    String path = "students.txt";

    public StudentRepository(){
    }

    public Student getStudentByLogin(String login){
        final Student[] student = new Student[1];
        try {
            Files.lines(Paths.get(path)).forEach(s -> {
                String stLogin = s.split(",")[0].trim();
                if(stLogin.equals(login)) {
                    student[0] = new Student();
                    student[0].setLogin(s.split(",")[0].trim());
                    student[0].setLogin(s.split(",")[1].trim());
                    student[0].setLogin(s.split(",")[2].trim());
                    student[0].setLogin(s.split(",")[3].trim());
                }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return student[0];
    }


    public void addStudent(Student student) throws UserAlreadyExistsException {
        if(getStudentByLogin(student.getLogin())!=null)
            throw new UserAlreadyExistsException("Student with login " + student.getLogin() + " already exists");

        try {
            writer = new FileWriter(path, true);
            writer.write("\n " + student.getLogin()+ ", " + student.getPassword() + ", " +
                    student.getName() + ", " + student.getLastName());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
