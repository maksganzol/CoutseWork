package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.Question;
import sample.models.Student;
import sample.services.StudentService;
import sample.services.TestQuestionService;

public class QuestionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label question;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private Button startbutton;

    @FXML
    private Button finish;

    private  ToggleGroup group;
    private TestQuestionService service;
    private List<Question> questions;
    private Question currentQuestion;
    private int count = 0;
    private Student student;


    @FXML
    void answer() {
        radio1.setVisible(true);
        radio2.setVisible(true);
        radio3.setVisible(true);

        if(isCorrectAnsw(radio1, radio2, radio3)) student.increaseMark();
        nextQuestion();
        startbutton.setText("След. вопрос");

        radio1.setSelected(false);
        radio2.setSelected(false);
        radio3.setSelected(false);
    }

    @FXML
    void initialize() {
        group = new ToggleGroup();

        radio1.setToggleGroup(group);
        radio2.setToggleGroup(group);
        radio3.setToggleGroup(group);

        radio1.setVisible(false);
        radio2.setVisible(false);
        radio3.setVisible(false);

        startbutton.setText("Начать тестирование");
    }

    public void initData(Student student, String theme, StudentService studentService) {
        this.student = student;
        service = new TestQuestionService();
        questions = service.getByTheme(theme);
        currentQuestion = new Question();
    }

    public boolean isCorrectAnsw(RadioButton... button){ //Проверяет верный ли ответ среди всех радиобаттонов
        boolean t, q;
        for(RadioButton radio: button) {
            t = currentQuestion.isCorrectAnswer(radio.getText());
            q = radio.isSelected();
            if (currentQuestion.isCorrectAnswer(radio.getText())&& radio.isSelected())
                return true;
        }
        return false;
    }

    public void nextQuestion(){

        if(questions.size()>count) {
            setQuestionAndAnswers();
            count++;
        } else {
            finish.setVisible(true);
            radio1.setVisible(false);
            radio2.setVisible(false);
            radio3.setVisible(false);
            startbutton.setVisible(false);
            question.setText("");
        }

    }

    public void setQuestionAndAnswers(){
        currentQuestion = questions.get(count);
        String questText = currentQuestion.getQuestion();
        question.setText(questText);

        setRandomAnswerLocation();
    }
    //Этот метод генерирует случаное расположение ответов (3 отличных друг от друга числа от 0 до 2)
    private void setRandomAnswerLocation(){
        int i1 = (int)(Math.random()*3);
        int i2;
        do i2=(int)(Math.random()*3);
        while(i2 == i1);
        int i3;
        do i3 = (int) (Math.random()*3);
        while ((i3==i1 || i3==i2));
        radio1.setText(currentQuestion.getAnswer(i1));
        radio2.setText(currentQuestion.getAnswer(i2));
        radio3.setText(currentQuestion.getAnswer(i3));

    }

    public void finish(){
        startbutton.getScene().getWindow().hide();
        System.out.println(student);
    }


}
