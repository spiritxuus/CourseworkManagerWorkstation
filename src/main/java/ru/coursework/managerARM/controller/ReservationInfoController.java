package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.Reservation;
import java.time.LocalDate;

public class ReservationInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private Reservation reservation;

    @FXML
    private Label lbClient;

    @FXML
    private Label lbEquip;

    @FXML
    private TextField tfEndDate;

    @FXML
    private TextField tfStartDate;

    @FXML
    private TextField tfStatus;

    //TODO ДОДЕЛАТЬ БРОНИ

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            reservation.setClient(Long.valueOf(String.valueOf(lbClient)));
            reservation.setEquipment(Long.valueOf(String.valueOf(lbEquip)));
            reservation.setStartDate(LocalDate.parse(tfStartDate.getText().trim()));
            reservation.setEndDate(LocalDate.parse(tfEndDate.getText().trim()));
            reservation.setStatus(tfStatus.getText().trim());

            confirmed = true;
            stage.close();
        } catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Не все поля заполнены.");
            alert.showAndWait();
        }
    }

    public void setReservation(Reservation reservation){
        this.reservation = reservation;

        lbClient.setText(String.valueOf(reservation.getClient()));
        lbEquip.setText(String.valueOf(reservation.getEquipment()));
        tfStartDate.setText(String.valueOf(reservation.getStartDate()));
        tfEndDate.setText(String.valueOf(reservation.getEndDate()));
        tfStatus.setText(reservation.getStatus());
    }

}
