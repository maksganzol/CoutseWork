package sample.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.Material;
import sample.models.Question;
import sample.models.Student;
import sample.services.StudentService;
import sample.services.StudyMaterialsService;


public class StudentPageController {

    @FXML
    private Label total;
    @FXML
    private Label hello;

    @FXML
    private Label totalMark;

    @FXML
    private Label pages;

    @FXML
    private TextArea area;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startbutton;

    @FXML
    private Label titleLabel;

    private Student student;

    private StudyMaterialsService service;
    private StudentService studentService;

    @FXML
    void initialize() {
        service = new StudyMaterialsService();
    }

    public void initData(Student student, StudentService service){
        hello.setText("Hello, " + student.getName());
        this.student = student;
        this.studentService = service;
    }

    public void startStudying() throws IOException {
        startbutton.setText("След. раздел");
        if(service.isLastPortionInMaterial()) {
            showTotalPage(student, studentService);
        } else {
            String portion = service.getPortionOfMaterial();
            String title = service.getCurrentTitle();
            titleLabel.setText(title);
            if (service.isLastPortionInTopic(portion)) {
                try {
                    tests(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            area.setText(portion);
        }
    }

    public Stage tests(String theme) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/questionpage.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        QuestionController controller = (QuestionController)loader.getController();
        controller.initData(student, theme, studentService);//проблема с отображеним окна
        stage.showAndWait();
        return stage;
    }

    private void showTotalPage(Student student, StudentService service) throws IOException {
        area.setVisible(false);
        startbutton.setVisible(false);
        total.setText(studentService.getTotalStatement(student));
    }


}
