package sample.dao;

import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;
import sample.models.Teacher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TeacherDao {

    private FileWriter writer;
    private String path = "teachers.txt";

    public TeacherDao(){

    }

    public Teacher getTeacherByLogin(String login){
        final Teacher[] teacher = new Teacher[1];
        try {
            Files.lines(Paths.get(path)).forEach(s -> {
                if(login.equals(s.split(",")[0])) {
                    teacher[0] = new Teacher();
                    teacher[0].setLogin(s.split(",")[0].trim());
                    teacher[0].setPassword(s.split(",")[1].trim());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacher[0];

    }

    public void addTeacher(Teacher teacher) throws UserAlreadyExistsException {
        if(getTeacherByLogin(teacher.getLogin())!=null)
            throw new UserAlreadyExistsException("Teacher with login " + teacher.getLogin() + " already exists");

        try {
            writer = new FileWriter(path, true);
            writer.write("\n" + teacher.getLogin()+ "," + teacher.getPassword());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
