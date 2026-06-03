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
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.RentalContract;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @FXML
    void initialize(){

        cbStatus.setItems(FXCollections.observableArrayList(
                "Действителен",
                "Завершён"
        ));

        dpCurrentDate.setEditable(false);
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            if (dpPlannedReturn.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Дата планируемого возвращения не заполнена.");
                alert.showAndWait();
                return;
            }

            if (dpCurrentDate.getValue().isAfter(dpPlannedReturn.getValue()) ||
                    dpCurrentDate.getValue().isAfter(dpActualReturn.getValue())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Дата заключения не может быть позже даты возвращения.");
                alert.showAndWait();
                return;
            }

            contract.setIssueDate(dpCurrentDate.getValue());
            contract.setPlannedReturnDate(dpPlannedReturn.getValue());
            contract.setActualReturnDate(dpActualReturn.getValue());
            contract.setDepositAmount(new BigDecimal(tfDeposit.getText().trim()));
            contract.setTotalAmount(new BigDecimal(tfTotalAmount.getText().trim()));
            contract.setStatus(cbStatus.getValue());
            contract.setIssueConditionDesc(tfIssueCondDesc.getText().trim());
            contract.setIssueConditionPhoto(photoPath);

            confirmed = true;
            stage.close();
        } catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Не все поля заполнены.");
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
        } catch (Exception exc) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки изображения.", ButtonType.OK).showAndWait(); //TODO логирование ClientChoose

        }
    }

    public void setContract(RentalContract contract, ReservationView reservation) {
        this.contract = contract;

        lbReservation.setText(String.valueOf(reservation.getClientName()) + reservation.getStartDate());
        dpCurrentDate.setValue(LocalDate.now());
        dpPlannedReturn.setValue(contract.getPlannedReturnDate());
        dpActualReturn.setValue(contract.getActualReturnDate());
        tfDeposit.setText(String.valueOf(contract.getDepositAmount()));
        tfTotalAmount.setText(String.valueOf(contract.getTotalAmount()));
        cbStatus.setValue(contract.getStatus());
        tfIssueCondDesc.setText(contract.getIssueConditionDesc());

        if (contract.getIssueConditionPhoto() != null && !contract.getIssueConditionPhoto().isBlank()) {
            Image img = new Image(new File(contract.getIssueConditionPhoto()).toURI().toString());
            mainImage.setImage(img);
            photoPath = contract.getIssueConditionPhoto();
        } else {
            photoPath = null;
        }
    }
}
