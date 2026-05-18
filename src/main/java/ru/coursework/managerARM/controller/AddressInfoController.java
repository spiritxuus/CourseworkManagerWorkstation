package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class AddressInfoController {

    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @FXML
    private TextField tfBirthdate;

    @FXML
    private TextField tfGender;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassportSeries;

    @FXML
    private TextField tfPatronymic;

    @FXML
    private TextField tfSurname;

    @FXML
    void onOkayButtonClick(ActionEvent event) {

    }


}