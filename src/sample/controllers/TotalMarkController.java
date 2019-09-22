

package sample.controllers;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import sample.models.Student;
        import sample.services.StudentService;


public class TotalMarkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button finish;

    @FXML
    private Label label;


    @FXML
    void initialize() {


    }

    public void initData(StudentService service, Student student){

        label.setText(service.getTotalStatement(student));
    }

}
