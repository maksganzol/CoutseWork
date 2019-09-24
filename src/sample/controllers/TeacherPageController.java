
package sample.controllers;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import sample.models.Student;
        import sample.models.Teacher;
        import sample.services.TeacherService;


public class TeacherPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button all;

    @FXML
    private Label hello;

    @FXML
    private TextField initials;

    @FXML
    private Button one;

    @FXML
    private Label pages;

    @FXML
    private TextArea students;

    private TeacherService service;

    @FXML
    void initialize() {
        service = new TeacherService();
    }

    public void initData(Teacher teacher) {
    }

    public void getAll(){
        for(Student student: service.getAllStudents())
            students.setText(students.getText() + "\n" + student.getName() + " " + student.getLastName() + ": total mark " + student.getTotalMark());
    }

    public void getByLogin(){
        String login;
        Student student = null;
        if((login=initials.getText())!=null)
            student = service.getStudent(login);

        if(student!=null) {
            students.setText(student.getName() + " " + student.getLastName() + ": total mark " + student.getTotalMark());
        } else students.setText("Not found");
    }

}
