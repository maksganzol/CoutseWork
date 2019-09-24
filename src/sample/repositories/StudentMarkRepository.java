package sample.repositories;

import sample.models.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudentMarkRepository {
    private FileWriter fileWriter;
    private String path = "students_marks.txt";
    public StudentMarkRepository(){
    }

    public void addStudent(Student student){
        try {
            fileWriter = new FileWriter(path, true);
            fileWriter.write("\n" + student.getLogin() + "," + student.getName() + "," + student.getLastName() + "," + student.getTotalMark());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAll(){
        List<Student> students = new ArrayList<>();
        try {
            Files.lines(Paths.get(path)).forEach(s -> {
                Student student = new Student();
                student.setLogin(s.split(",")[0]);
                student.setName(s.split(",")[1]);
                student.setLastName(s.split(",")[2]);
                student.setTotalMark(Integer.parseInt(s.split(",")[3]));
                students.add(student);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }
}
