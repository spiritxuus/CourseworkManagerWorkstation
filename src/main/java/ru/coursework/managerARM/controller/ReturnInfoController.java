package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ReturnInfoController {

    @FXML
    private ComboBox<?> cbContract;

    @FXML
    private TextField tfConditionDesc;

    @FXML
    private TextField tfConditionPhoto;

    @FXML
    private TextField tfDamage;

    @FXML
    private TextField tfRepair;

    @FXML
    private TextField tfReturnDate;

    @FXML
    private TextField tfStatus;

    @FXML
    void onOkayButtonClick(ActionEvent event) {

    }

}
