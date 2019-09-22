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
import sample.models.Question;
import sample.models.Student;
import sample.services.StudentService;
import sample.services.StudyMaterialsService;


public class StudentPageController {

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

    private Student student;

    private StudyMaterialsService service;
    private List<String> themeSet;
    private int count = 0; //Считает номер темы
    private int currentPortion = 0;
    private int countOfPortion = 0;
    private boolean isLast = false;
    private StudentService studentService;

    @FXML
    void initialize() {
        service = new StudyMaterialsService();
        themeSet = new ArrayList<>(service.getThemeSet());
    }

    public void initData(Student student, StudentService service){
        hello.setText("Hello, " + student.getName());
        this.student = student;
        this.studentService = service;
    }

    public void startStudying() throws IOException {
        if (!isLast) {
            //pages.setText(service.getQuantityReadPages());//некорректно работает
            if (count < service.getStudyMaterials().getSize()) {
                currentPortion = service.getNumberOfPortion();
                countOfPortion = service.getCountOfPortion(themeSet.get(count));
                area.setText(service.getPortionOfMaterial(themeSet.get(count)));
            }
            if ((currentPortion == 1) && count != 0) {//если все темы прочитанны, то пускаем вопросы
                //if(isLast)  startbutton.getScene().getWindow().hide();
                tests(themeSet.get(count - 1));
            }
            if (count == themeSet.size())
                isLast = true;
            if ((currentPortion == countOfPortion) && (count <= themeSet.size() + 1)) {//если все темы прочитанны, то меняем счетчиk темы
                count++;
            }
            startbutton.setText("След. раздел");
        } else {
            showTotalPage(student, studentService);
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

    private Stage showTotalPage(Student student, StudentService service) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/totalmark.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        TotalMarkController controller = (TotalMarkController) loader.getController();
        controller.initData(service, student);
        stage.showAndWait();
        return stage;
    }
//TODO доделать переход на страницу с оценкой


}
