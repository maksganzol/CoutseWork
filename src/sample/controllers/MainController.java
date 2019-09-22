package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.exceptions.UserAlreadyExistsException;
import sample.models.Student;
import sample.models.Teacher;
import sample.services.StudentService;
import sample.services.TeacherService;


public class MainController {

    @FXML
    private Label warning;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginbutton;

    @FXML
    private TextField stlastname;

    @FXML
    private TextField stlogin;

    @FXML
    private TextField stname;

    @FXML
    private PasswordField stpassword;

    @FXML
    private TextField tlogin;

    @FXML
    private PasswordField tpassword;

    @FXML
    private Button asstudent;

    @FXML
    private Button asteacher;

    private boolean asStudent, asTeacher;
    private StudentService studentService;
    private TeacherService teacherService;

    @FXML
    void initialize() {

        teacherService = new TeacherService();
        studentService = new StudentService();

    }

    public void asStudent(){
        loginbutton.setVisible(true);
        asStudent = true;
        asTeacher = false;
        tlogin.setVisible(false);
        tpassword.setVisible(false);

        stname.setVisible(true);
        stlastname.setVisible(true);
        stlogin.setVisible(true);
        stpassword.setVisible(true);
    }

    public void asTeacher(){
        loginbutton.setVisible(true);
        asStudent = false;
        asTeacher = true;
        tlogin.setVisible(true);
        tpassword.setVisible(true);

        stname.setVisible(false);
        stlastname.setVisible(false);
        stlogin.setVisible(false);
        stpassword.setVisible(false);
    }

    public void logIn(){
        if(asStudent) {
            Student student = new Student();

            String name = stname.getText();
            String lastName = stlastname.getText();
            String login = stlogin.getText();
            String password = stpassword.getText();
            if(!(name.equals("")||lastName.equals("")||login.equals("")||password.equals(""))) {
                student.setName(name);
                student.setLastName(lastName);
                student.setLogin(login);
                student.setPassword(password);
            } else {
                warning.setText("Fields can't be empty");
                return;
            }

            try {
                studentService.addStudent(student);
                this.loginbutton.getScene().getWindow().hide();
                this.showStudentPage(student, studentService);

            } catch (UserAlreadyExistsException e) {
                System.out.println(e.getMessage());
                stlogin.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

            Teacher teacher = new Teacher();
            teacher.setLogin(tlogin.getText());
            teacher.setPassword(tpassword.getText());


            if(teacherService.authorized(teacher)) {
                try {
                    this.loginbutton.getScene().getWindow().hide();
                    this.showTeacherPage(teacher);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                tpassword.setText("");
            }


        }
    }

    private Stage showTeacherPage(Teacher teacher) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/teacherpage.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        TeacherPageController controller = (TeacherPageController)loader.getController();
        controller.initData(teacher);
        stage.showAndWait();
        return stage;
    }

    private Stage showStudentPage(Student student, StudentService service) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/studentpage.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        StudentPageController controller = (StudentPageController)loader.getController();
        controller.initData(student, service);
        stage.showAndWait();
        return stage;
    }
}
