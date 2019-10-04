package sample.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import sample.models.Question;
import sample.services.TestQuestionService;


public class QuestionWatchingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button finish;

    @FXML
    private TextArea questionss;
    private TestQuestionService service;


    @FXML
    void initialize() {

    }

    public void initData(TestQuestionService service){
        this.service = service;
        String questions = "";
        for(Question q: service.getAllQuestions()){
            questions += q.getQuestion() + " Ответ: " + q.getCorrectAnswer() + "\n";
        }
        questionss.setText(questions);
    }


}
