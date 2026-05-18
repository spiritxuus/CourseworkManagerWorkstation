package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;

public class NaturalClientInfoController {

    @Getter
    private boolean confirmed = false;

    private NaturalPerson naturalPerson;

    @Setter
    private Stage stage;

    @FXML
    private ComboBox<String> cbAddress;

    @FXML
    private TextField ftEmail;

    @FXML
    private TextField ftfPassportNumber;

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
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfSurname;

    public void setNaturalPerson(NaturalPerson naturalPerson){
        this.naturalPerson = naturalPerson;

    }

    @FXML
    void onAddAddrButton(ActionEvent event) {

    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {

    }

}
