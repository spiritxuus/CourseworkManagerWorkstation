package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.model.Repair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

    private static final Logger logger = LoggerFactory.getLogger(RepairRequestController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();


    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            repair.setRepairReason(tfRepairReason.getText().trim());
            repair.setRepairCost(new BigDecimal(tfRepairAmount.getText().trim()));

            confirmed = true;
            stage.close();
        } catch (Exception e){
            logger.info("repair onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
            alert.showAndWait();
        }
    }

    public void setRepair(Repair repair,Equipment equipment){
        this.repair = repair;

        lbEquipment.setText(equipment.getName());
        lbCreationDate.setText(String.valueOf(LocalDate.now()));
        tfRepairReason.setText(bundle.getString("lb.repair_request"));

        if (repair.getRepairStatus() == 1){
            lbRepairStatus.setText(bundle.getString("lb.repair_request"));
        }
        else if (repair.getRepairStatus() == 2){
            lbRepairStatus.setText(bundle.getString("lb.repair_completed"));
        }
        else{
            lbRepairStatus.setText(bundle.getString("lb.repair_cancelled"));
        }

        tfRepairAmount.setText(String.valueOf(repair.getRepairCost()));
    }
}
