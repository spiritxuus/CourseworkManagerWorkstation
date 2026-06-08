package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.RentalContract;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContractInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private RentalContract contract;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private DatePicker dpActualReturn;

    @FXML
    private DatePicker dpPlannedReturn;

    @FXML
    private ImageView mainImage;

    @FXML
    private DatePicker dpCurrentDate;

    @FXML
    private TextField tfDeposit;

    @FXML
    private TextField tfIssueCondDesc;

    @FXML
    private Label lbReservation;

    @FXML
    private TextField tfTotalAmount;

    private String photoPath;

    private boolean editMode;

    private static final Logger logger = LoggerFactory.getLogger(ContractInfoController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();


    @FXML
    void initialize(){
        cbStatus.setItems(FXCollections.observableArrayList(
                bundle.getString("combo_box.contract_ready"),
                bundle.getString("combo_box.contract_end")
        ));

        dpCurrentDate.setEditable(false);
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            if (dpPlannedReturn.getValue() == null){
                logger.info("contract onOkayButtonClick() dpPlannedReturn is not choosed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.date_warning_planned"));
                alert.showAndWait();
                return;
            }

            if (dpCurrentDate.getValue().isAfter(dpPlannedReturn.getValue())){
                logger.info("contract onOkayButtonClick() date error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.date_warning_current"));
                alert.showAndWait();
                return;
            }

            if (editMode && dpActualReturn.getValue() != null &&
                    dpCurrentDate.getValue().isAfter(dpActualReturn.getValue())) {
                logger.info("contract onOkayButtonClick() date error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.date_warning_actual"));
                alert.showAndWait();
                return;
            }

            contract.setIssueDate(dpCurrentDate.getValue());
            contract.setPlannedReturnDate(dpPlannedReturn.getValue());

            if (editMode) {
                contract.setActualReturnDate(dpActualReturn.getValue());
            } else {
                contract.setActualReturnDate(null);
            }

            contract.setDepositAmount(new BigDecimal(tfDeposit.getText().trim()));
            contract.setTotalAmount(new BigDecimal(tfTotalAmount.getText().trim()));

            if (Objects.equals(cbStatus.getValue(), "combo_box.contract_ready")) { contract.setStatus(1); }
            else { contract.setStatus(2); }

            contract.setIssueConditionDesc(tfIssueCondDesc.getText().trim());
            contract.setIssueConditionPhoto(photoPath);

            confirmed = true;
            stage.close();
        } catch (Exception e){
            logger.info("contract onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
            alert.showAndWait();
        }
    }

    @FXML
    void onOpenPhotoButtonClick(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            FileChooser.ExtensionFilter filter = new
                    FileChooser.ExtensionFilter("All image files",
                    "*.png", "*.jpg", "*.gif");
            fileChooser.getExtensionFilters().addAll(filter);
            File file =
                    fileChooser.showOpenDialog(MainApplication.getStage());
            if (file != null) {
                Image img = new Image(file.toURI().toString());
                mainImage.setImage(img);
                photoPath = file.getAbsolutePath();
            }
        } catch (Exception e) {
            logger.error("Error while opening photo", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.photo_open"), ButtonType.OK).showAndWait();

        }
    }

    public void setContract(RentalContract contract, ReservationView reservation) {
        this.contract = contract;

        lbReservation.setText(reservation.getClientName() + " | " +
                reservation.getEquipmentName() + " | " +
                reservation.getStartDate() + " - " + reservation.getEndDate());
        dpCurrentDate.setValue(LocalDate.now());
        dpPlannedReturn.setValue(contract.getPlannedReturnDate());
        dpActualReturn.setValue(contract.getActualReturnDate());
        tfDeposit.setText(String.valueOf(contract.getDepositAmount()));
        tfTotalAmount.setText(String.valueOf(contract.getTotalAmount()));

        if (contract.getStatus() == 1) { cbStatus.setValue(bundle.getString("combo_box.contract_ready")); }
        else { cbStatus.setValue(bundle.getString("combo_box.contract_end")); }

        tfIssueCondDesc.setText(contract.getIssueConditionDesc());

        if (contract.getIssueConditionPhoto() != null && !contract.getIssueConditionPhoto().isBlank()) {
            Image img = new Image(new File(contract.getIssueConditionPhoto()).toURI().toString());
            mainImage.setImage(img);
            photoPath = contract.getIssueConditionPhoto();
        } else {
            photoPath = null;
        }
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        dpActualReturn.setVisible(editMode);
        dpActualReturn.setManaged(editMode);
    }
}
