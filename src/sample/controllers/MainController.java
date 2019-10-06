package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sample.exceptions.UserAlreadyExistsException;
import sample.exceptions.UserNotFoundException;
import sample.models.Student;
import sample.models.Teacher;
import sample.services.StudentService;
import sample.services.StudyMaterialsService;
import sample.services.TeacherService;
import sample.services.TestQuestionService;


public class MainController {

    @FXML
    private Label warning;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label loginLabel;

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

    @FXML
    private Button stlist;

    @FXML
    private RadioButton logined, unlogined;

    private boolean asStudent, asTeacher;
    private StudentService studentService;
    private TeacherService teacherService;
    private ToggleGroup group;

    @FXML
    private Label edit;

    @FXML
    void initialize() {
        group = new ToggleGroup();
        unlogined.setToggleGroup(group);
        logined.setToggleGroup(group);

        teacherService = new TeacherService();
        studentService = new StudentService();

        logined.setOnAction(e ->{
            stlastname.setVisible(false);
            stname.setVisible(false);
        });

        unlogined.setOnAction(e ->{
            stlastname.setVisible(true);
            stname.setVisible(true);
        });

    }

    public void asStudent(){
        stlist.setVisible(true);
        loginbutton.setVisible(true);
        asStudent = true;
        asTeacher = false;
        tlogin.setVisible(false);
        tpassword.setVisible(false);

        stname.setVisible(true);
        stlastname.setVisible(true);
        stlogin.setVisible(true);
        stpassword.setVisible(true);

        logined.setVisible(true);
        unlogined.setVisible(true);
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

        logined.setVisible(false);
        unlogined.setVisible(false);
    }

    public void logIn() throws IOException {
        if(asStudent) {
            Student student = new Student();

            if (logined.isSelected()) {
                String login = stlogin.getText();
                String password = stpassword.getText();
                if (!(login.equals("") || password.equals(""))) {
                    Student st = studentService.getStudent(login);
                    if (st != null)
                        if (st.getPassword().equals(password)) {
                            loginbutton.getScene().getWindow().hide();
                            showStudentPage(st, studentService);
                        }
                }

            } else {

                String name = stname.getText();
                String lastName = stlastname.getText();
                String login = stlogin.getText();
                String password = stpassword.getText();
                if (!(name.equals("") || lastName.equals("") || login.equals("") || password.equals(""))) {
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
                    ((Stage) loginbutton.getScene().getWindow()).close();
                    this.showStudentPage(student, studentService);

                } catch (UserAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                    stlogin.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UserNotFoundException e) {
                    System.out.println(e.getMessage());
                    stname.setText("");
                    stlastname.setText("");
                }
            }
        }
        else {

            Teacher teacher = new Teacher();
            teacher.setLogin(tlogin.getText());
            teacher.setPassword(tpassword.getText());


            if(teacherService.authorized(teacher)) {
                try {
                    loginbutton.getScene().getWindow().hide();
                    showTeacherPage(teacher);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                tpassword.setText("");
            }


        }
    }

    private void showTeacherPage(Teacher teacher) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/teacherpage.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        TeacherPageController controller = (TeacherPageController)loader.getController();
        controller.initData(teacher, new TestQuestionService(), (Stage) loginbutton.getScene().getWindow());
        stage.showAndWait();
    }

    public void showStudentList() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/studentlist.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        StudentListController controller = (StudentListController) loader.getController();
        controller.initData(studentService, stname, stlastname);
        stage.showAndWait();
    }


    private void showStudentPage(Student st, StudentService studentService) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/studentpage.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        StudentPageController controller = (StudentPageController)loader.getController();
        controller.initData(st, studentService, new StudyMaterialsService(), (Stage) loginbutton.getScene().getWindow());
        stage.showAndWait();
    }


}
