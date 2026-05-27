package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.Address;

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

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        address.setCountry(tfCountry.getText().trim());
        address.setRegion(tfRegion.getText().trim());
        address.setCity(tfCity.getText().trim());
        address.setStreet(tfStreet.getText().trim());
        address.setHouse(tfHouse.getText().trim());
        address.setApartment(tfApartment.getText());

        confirmed = true;
        stage.close();
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