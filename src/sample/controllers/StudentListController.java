package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.services.StudentService;

import java.util.ArrayList;
import java.util.List;

public class StudentListController {
    @FXML
    private ListView<String> stlist;

    private StudentService service;

    @FXML
    public void initialize(){

    }

    public void initData(StudentService service, TextField stname, TextField stlastName){
        final String[] initials = new String[2];
        this.service = service;
        List<String> students = new ArrayList<>();
        service.getAll().forEach(student -> {
        students.add(student.getName() + " " + student.getLastName());
        });
        ObservableList<String> titles = FXCollections.observableArrayList(students);
        stlist.setItems(titles);
        MultipleSelectionModel<String> langsSelectionModel = stlist.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){
            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue){
                stname.setText(newValue.split(" ")[0]);
                stlastName.setText(newValue.split(" ")[1]);
                stlist.getScene().getWindow().hide();
            }
        });
    }
}
