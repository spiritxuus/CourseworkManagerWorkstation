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
        address.setCountry(tfCountry.getText());
        address.setRegion(tfRegion.getText());
        address.setCity(tfCity.getText());
        address.setStreet(tfStreet.getText());
        address.setHouse(tfHouse.getText());
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

        tfCountry.setText(address.getCountry().trim());
        tfRegion.setText(address.getRegion().trim());
        tfCity.setText(address.getCity().trim());
        tfStreet.setText(address.getStreet().trim());
        tfHouse.setText(address.getHouse().trim());
        tfApartment.setText(address.getApartment().trim());

    }

}