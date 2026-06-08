package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.model.Address;

import java.util.ResourceBundle;

public class AddressInfoController {

    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    private Address address;

    @FXML
    private TextField tfApartment;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfCountry;

    @FXML
    private TextField tfHouse;

    @FXML
    private TextField tfRegion;

    @FXML
    private TextField tfStreet;

    private static final Logger logger = LoggerFactory.getLogger(AddressInfoController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        try{
            address.setCountry(tfCountry.getText().trim());
            address.setRegion(tfRegion.getText().trim());
            address.setCity(tfCity.getText().trim());
            address.setStreet(tfStreet.getText().trim());
            address.setHouse(tfHouse.getText().trim());
            address.setApartment(tfApartment.getText());

            confirmed = true;
            stage.close();
        } catch (Exception e) {
            logger.info("address onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
            alert.showAndWait();
        }
    }

    @FXML
    void onExitButton(ActionEvent event) {
        confirmed = false;
        stage.close();
    }

    public void setAddress(Address address){
        this.address = address;

        tfCountry.setText(address.getCountry());
        tfRegion.setText(address.getRegion());
        tfCity.setText(address.getCity());
        tfStreet.setText(address.getStreet());
        tfHouse.setText(address.getHouse());
        tfApartment.setText(address.getApartment());

    }

}