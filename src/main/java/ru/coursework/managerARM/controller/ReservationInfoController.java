package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.model.Reservation;

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
    private DatePicker dpEndDate;

    @FXML
    private DatePicker dpStartDate;

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            if (dpStartDate.getValue() == null || dpEndDate.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Даты не заполнены.");
                alert.showAndWait();
                return;
            }

            if (dpStartDate.getValue().isAfter(dpEndDate.getValue())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Дата начала не может быть позже даты окончания.");
                alert.showAndWait();
                return;
            }

            reservation.setStartDate(dpStartDate.getValue());
            reservation.setEndDate(dpEndDate.getValue());

            confirmed = true;
            stage.close();
        } catch (Exception exc){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Не все поля заполнены.");
            alert.showAndWait();
        }
    }

    public void setReservation(Reservation reservation, ReservationView reservationView){
        this.reservation = reservation;

        lbClient.setText(reservationView.getClientName());
        lbEquip.setText(reservationView.getEquipmentName());
        dpStartDate.setValue(reservation.getStartDate());
        dpEndDate.setValue(reservation.getEndDate());
    }
}
