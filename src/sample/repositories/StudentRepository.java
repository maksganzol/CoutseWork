package sample.repositories;

import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                String[] data = s.split(",");
                String stLogin = data[0].trim();
                if(stLogin.equals(login)) {
                    student[0] = new Student();
                    student[0].setLogin(data[0].trim());
                    student[0].setPassword(data[1].trim());
                    student[0].setName(data[2].trim());
                    student[0].setLastName(data[3].trim());
                    student[0].setTotalMark(Integer.parseInt(data[4].trim()));
                    student[0].setTotalTime(Long.parseLong(data[5].trim()));
                    for(int i = 6; i < data.length; i++)
                        student[0].addStudiedTopic(data[i].trim());
                }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return student[0];
    }


    public void addStudent(Student student) {
        try {
            writer = new FileWriter(path, true);
            String data = "";
            if(Files.lines(Paths.get(path)).count()!=0)
                data = "\n ";
            data += student.getLogin()+ ", " + student.getPassword() + ", " +
                    student.getName() + ", " + student.getLastName() + ", " + student.getTotalMark() + ", " + student.getTotalTime();

            for(String topic: student.getStudiedTopics()){
                data += ", " + topic;
            }

            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInStudent(Student student){
        List<Student> students = getAll();
        students.forEach(st -> {
            if(st.getName().equals(student.getName())&&st.getLastName().equals(student.getLastName())){
                st.setLogin(student.getLogin());
                st.setPassword(student.getPassword());
            }
        });
        try {
            writer = new FileWriter(path);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        students.forEach(this::addStudent);
    }

    public void updateStudent(Student student){
        List<Student> students = getAll();
        students.forEach(st -> {
            if(st.equals(student)) {
                st.setTotalMark(student.getTotalMark());
                st.setStudiedTopics(student.getStudiedTopics());
                st.setTotalTime(student.getTotalTime());
            }
        });
        try {
        writer = new FileWriter(path);
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        students.forEach(this::addStudent);
    }

    public List<Student> getAll(){
        List<Student> students = new ArrayList<>();
        try {
            Files.lines(Paths.get(path)).forEach(s -> {
                String[] param = s.split(",");
                Student st  = new Student(param[0].trim(), param[1].trim(), param[2].trim(), param[3].trim());
                st.setTotalMark(Integer.parseInt(param[4].trim()));
                st.setTotalTime(Long.parseLong(param[5].trim()));
                for(int i = 6; i < param.length; i++)
                    st.addStudiedTopic(param[i].trim());
                students.add(st);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}
