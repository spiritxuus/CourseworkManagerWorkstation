package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.Payment;

public class PaymentInfoController {

    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    private Payment payment;

    @FXML
    private Label lbContractID;

    @FXML
    private Label lbPaymentAmount;

    @FXML
    private Label lbPaymentDate;

    @FXML
    private Label lbPaymentMethod;

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        confirmed = true;
        stage.close();
    }

    public void setPayment(Payment payment){
        this.payment = payment;

        lbContractID.setText(String.valueOf(payment.getContract()));
        lbPaymentDate.setText(String.valueOf(payment.getPaymentDate()));
        lbPaymentAmount.setText(String.valueOf(payment.getAmount()));
        lbPaymentMethod.setText(payment.getPaymentMethod());
    }

}
