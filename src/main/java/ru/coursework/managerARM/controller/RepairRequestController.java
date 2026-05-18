package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RepairRequestController {

    @FXML
    private ComboBox<?> cbContract;

    @FXML
    private Label lbCreationDate;

    @FXML
    private Label lbEquipment;

    @FXML
    private Label lbRepairStatus;

    @FXML
    private TextField tfRepairAmount;

    @FXML
    private TextField tfRepairReason;

    @FXML
    void onOkayButtonClick(ActionEvent event) {

    }

}
