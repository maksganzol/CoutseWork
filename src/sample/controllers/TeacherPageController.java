
package sample.controllers;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.Pane;
        import javafx.stage.Stage;
        import javafx.stage.StageStyle;
        import sample.exceptions.UserAlreadyExistsException;
        import sample.models.Student;
        import sample.models.Teacher;
        import sample.services.StudentService;
        import sample.services.StudyMaterialsService;
        import sample.services.TeacherService;
        import sample.services.TestQuestionService;


public class TeacherPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addmaterial;

    @FXML
    private Button all;

    @FXML
    private Button editstudents;

    @FXML
    private Label hello;

    @FXML
    private TextField initials;

    @FXML
    private TextField lastName;

    @FXML
    private TextField name;

    @FXML
    private Button one;

    @FXML
    private Label pages;

    @FXML
    private TextArea students;

    @FXML
    private Button studmarks;

    @FXML
    private Button add;
    @FXML
    private Button back;

    private TeacherService teacherService;
    private StudentService studentService;
    private StudyMaterialsService materialsService;
    private Teacher teacher;
    private TestQuestionService questionService;
    private Stage stage;


    @FXML
    void initialize() {
        teacherService = new TeacherService();
        studentService = new StudentService();
        materialsService = new StudyMaterialsService();
    }

    public void initData(Teacher teacher, TestQuestionService questionService, Stage stage) {
        this.stage = stage;
        this.teacher = teacher;
        this.questionService = questionService;
    }

    public void getAll(){
        students.setText("");
        for(Student student: teacherService.getAllStudents())
            if(!(student.getStudiedTopics().size()<materialsService.getAllTitles().size()))
                students.setText(students.getText() + "\n" + student.getName() + " " + student.getLastName() + ": total mark " + student.getTotalMark());
    }

    public void getByLogin(){
        String login;
        Student student = null;
        if((login=initials.getText())!=null&& !login.equals(""))
            student = teacherService.getStudent(login);

        if(student!=null&&!(student.getStudiedTopics().size()<materialsService.getAllTitles().size())) {
            students.setText(student.getName() + " " + student.getLastName() + ": total mark " + student.getTotalMark());
        } else students.setText("Студент не найден или еще не прошел все темы");
    }

    public void studMarks(){
        students.setVisible(true);
        all.setVisible(true);
        one.setVisible(true);
        initials.setVisible(true);

        name.setVisible(false);
        lastName.setVisible(false);
        add.setVisible(false);
    }

    public void add(){
        try {
            studentService.addStudentName(name.getText(), lastName.getText());
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
        name.setText("");
        lastName.setText("");
    }

    public void editStudents(){
        students.setVisible(false);
        all.setVisible(false);
        one.setVisible(false);
        initials.setVisible(false);

        name.setVisible(true);
        lastName.setVisible(true);
        add.setVisible(true);

    }

    public void addMaterial() throws IOException {
        showMaterialInputPage();
    }

    public Stage showMaterialInputPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/materialinput.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        stage.showAndWait();
        return stage;
    }

    public void questions() throws IOException {
        if(teacher.isAdmin())
            showQuestionWatchingPage();

    }
    private void showQuestionWatchingPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../views/questionwatching.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene((Pane)loader.load()));
        QuestionWatchingController controller = (QuestionWatchingController) loader.getController();
        controller.initData(questionService);
        stage.showAndWait();
    }
    
    public void back(){
        back.getScene().getWindow().hide();
        stage.show();
    }

}
