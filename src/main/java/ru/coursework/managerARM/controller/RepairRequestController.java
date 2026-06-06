package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.model.Repair;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RepairRequestController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private Repair repair;

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
        try{
            repair.setRepairReason(tfRepairReason.getText().trim());
            repair.setRepairCost(new BigDecimal(tfRepairAmount.getText().trim()));

            confirmed = true;
            stage.close();
        } catch (Exception e){
            e.printStackTrace(); //TODO log
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Не все поля заполнены.");
            alert.showAndWait();
        }
    }

    public void setRepair(Repair repair,Equipment equipment){
        this.repair = repair;

        lbEquipment.setText(equipment.getName());
        lbCreationDate.setText(String.valueOf(LocalDate.now()));
        tfRepairReason.setText("Запрос");
        lbRepairStatus.setText(repair.getRepairStatus());
        tfRepairAmount.setText(String.valueOf(repair.getRepairCost()));
    }
}
