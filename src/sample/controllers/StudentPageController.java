package sample.controllers;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label themes;

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
    private Button prev;
    @FXML
    private Button start;
    @FXML
    private Button back;
    @FXML
    private Button totalButton;
    @FXML
    private ListView<String> listView;

    @FXML
    private Label titleLabel;

    private Student student;

    private StudyMaterialsService service;
    private StudentService studentService;
    private String title;
    private List<String> titlesList;
    private boolean logOut = false;
    private Stage prevStage;


    @FXML
    void initialize() {

    }

    public void start(){
        if(logOut) {
            start.getScene().getWindow().hide();
            studentService.finish();
            student.addTotalTime(studentService.getTotalTime());
            studentService.updateStudent(student);
            System.out.println(student.getTotalTime());
        }
        else {
            listView.setVisible(true);
            area.setVisible(true);
            studentService.start();
            logOut = true;
            start.setText("Выйти");
            if(student.getStudiedTopics().size()>=service.getAllTitles().size()) {
                themes.setText("Вы прошли все темы, теперь вы можете получить общую оценку");
                totalButton.setVisible(true);
            }
            else themes.setText("Темы");
        }
    }

    void initData(Student student, StudentService service, StudyMaterialsService studyService, Stage window){
        prevStage = window;
        ((Stage)start.getScene().getWindow()).setOnCloseRequest(Event::consume);
        hello.setText("Привет, " + student.getName());
        this.student = student;
        this.studentService = service;
        this.service = studyService;
        titlesList = studyService.getAllTitles();
        student.getStudiedTopics().forEach(title ->{
            titlesList.remove(title); //Удаляем из списка темы, которые студент уже прошел
        });
        ObservableList<String> titles = FXCollections.observableArrayList(titlesList);
        listView.setItems(titles);
        // получаем модель выбора элементов
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            title = newValue;
            titleLabel.setText(title);
            this.service.resetCounter();
            try {
                startStudying();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void startStudying() throws IOException {
        startbutton.setVisible(true);
        prev.setVisible(true);
        startbutton.setText("След. раздел");
            titleLabel.setText(title);
            if (service.isLastPortionInMaterial()) {
                //Делам недоступным материал
                area.setVisible(false);
                titleLabel.setText("Пройдите тест");
                //Удаляем данную тему из списка
                titlesList.remove(title);
                //Пускаем тесты
                try {
                    tests(title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Настраиваем обновленый список тем
                ObservableList<String> titles = FXCollections.observableArrayList(titlesList);
                listView.setItems(titles);
                //Возвращаем материал
                area.setVisible(true);
                titleLabel.setText(title);
                if(student.getStudiedTopics().size()>=service.getAllTitles().size()) {
                    themes.setText("Вы прошли все темы, теперь вы можете получить общую оценку");
                    startbutton.setVisible(false);
                    prev.setVisible(false);
                    totalButton.setVisible(true);
                }

            }
            area.setText(service.getPortionOfMaterial(title));
            if(service.isLastPortionInMaterial())
                startbutton.setText("К тестам");

    }

    public void prev(){
        area.setText(service.getPrevPortion(title));
        startbutton.setText("Cлед. раздел");
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

    public void total() throws IOException {
        area.setVisible(false);
        listView.setVisible(false);
        hello.setText("Общее время: " + student.getTotalTime());
        total.setText(studentService.getTotalStatement(student));
        themes.setText("");
    }

    public void back(){
        startbutton.getScene().getWindow().hide();
        prevStage.show();
    }

}
