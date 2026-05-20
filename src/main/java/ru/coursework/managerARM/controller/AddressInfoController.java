package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.model.NaturalPerson;

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

    }

    @FXML
    void onExitButton(ActionEvent event) {

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