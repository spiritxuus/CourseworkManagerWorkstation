package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.model.Reservation;

import java.util.ResourceBundle;

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

    private static final Logger logger = LoggerFactory.getLogger(ReservationInfoController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();


    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            if (dpStartDate.getValue() == null || dpEndDate.getValue() == null){
                logger.info("reservation onOkayButtonClick() dates are empty");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.reserv_date_not_filled"));
                alert.showAndWait();
                return;
            }

            if (dpStartDate.getValue().isAfter(dpEndDate.getValue())){
                logger.info("reservation onOkayButtonClick() data error");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.reserv_date_start"));
                alert.showAndWait();
                return;
            }

            reservation.setStartDate(dpStartDate.getValue());
            reservation.setEndDate(dpEndDate.getValue());

            confirmed = true;
            stage.close();
        } catch (Exception e){
            logger.info("reservation onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
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
