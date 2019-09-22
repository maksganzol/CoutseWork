
package sample.controllers;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import sample.models.Question;
        import sample.models.Teacher;
        import sample.services.MaterialInputService;
        import sample.services.TeacherService;


public class MaterialInputPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMaterial;

    @FXML
    private Button addQuest;

    @FXML
    private TextField answ2;

    @FXML
    private TextField answ1;

    @FXML
    private TextArea area;

    @FXML
    private TextField correctAnsw;

    @FXML
    private Label hello;

    @FXML
    private TextField materialTitle;

    @FXML
    private Label pages;

    @FXML
    private TextField questField;

    @FXML
    private TextField questTitle;

    private MaterialInputService service;

    @FXML
    void initialize() {
        service = new MaterialInputService();
    }

    public void addPortion(){
        String title = materialTitle.getText();
        String content = area.getText();
        if(!(title.equals("")||content.equals(""))) {
            service.addPortionOfMaterial(title, content);
            area.setText("");
            materialTitle.setText("");
        }

    }

    public void addQuestion(){
        String correctAnsw = this.correctAnsw.getText();
        String answ1 = this.answ1.getText();
        String answ2 = this.answ2.getText();
        String question = questField.getText();
        String theme = questTitle.getText();
        if(!(correctAnsw.equals("")||answ1.equals("")||answ2.equals("")||question.equals(""))) {
            service.addQuestion(new Question(question, correctAnsw, answ1, answ2, theme));
            this.correctAnsw.setText("");
            this.answ1.setText("");
            this.answ2.setText("");
            this.questField.setText("");
            this.questTitle.setText("");
        }

    }

}
