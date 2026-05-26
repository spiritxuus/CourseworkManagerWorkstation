package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.AddressDao;
import ru.coursework.managerARM.dao.NaturalPersonDao;
import ru.coursework.managerARM.dao.impl.AddressDaoImpl;
import ru.coursework.managerARM.dao.impl.NaturalPersonDaoImpl;
import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.dto.NaturalPersonView;
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;
import java.io.IOException;

public class LegalClientInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private LegalPerson legalPerson;

    @FXML
    private ComboBox<AddressView> cbAddress;

    @FXML
    private ComboBox<NaturalPersonView> cbContactPerson;

    @FXML
    private TextField ftEmail;

    @FXML
    private TextField tfCompanyName;

    @FXML
    private TextField tfInn;

    @FXML
    private TextField tfKpp;

    @FXML
    private TextField tfOgrn;

    @FXML
    private TextField tfPhoneNumber;

    private ObservableList<NaturalPersonView> contactViews = FXCollections.observableArrayList();

    private ObservableList<AddressView> addressViews = FXCollections.observableArrayList();

    private NaturalPersonDao naturalPersonDao;

    private AddressDao addressDao;

    @FXML
    void initialize(){
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.addressDao = new AddressDaoImpl();

        cbContactPerson.setItems(contactViews);
        cbAddress.setItems(addressViews);
        contactViews.setAll(naturalPersonDao.getAllViews());
        addressViews.setAll(addressDao.getAllViews());
    }


    @FXML
    void onAddAddrButton(ActionEvent event) {
        Address address = new Address();

        if(showAddressDialog(address) && isValid(address)){
            addressDao.add(address);
            addressViews.setAll(addressDao.getAllViews());
        }
    }

    @FXML
    void onAddContactButton(ActionEvent event) {
        NaturalPerson naturalPerson = new NaturalPerson();

        if(showContactDialog(naturalPerson) && isValid(naturalPerson)){
            naturalPersonDao.add(naturalPerson);
            contactViews.setAll(naturalPersonDao.getAllViews());
        }
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        AddressView selectedAddress = cbAddress.getValue();
        NaturalPersonView selectedContact = cbContactPerson.getValue();

        legalPerson.setCompanyName(tfCompanyName.getText());
        legalPerson.setInn(tfInn.getText());
        legalPerson.setKpp(tfKpp.getText());
        legalPerson.setOgrn(tfOgrn.getText());
        legalPerson.setPhone(tfPhoneNumber.getText());
        legalPerson.setEmail(ftEmail.getText());

        if (selectedAddress != null) {
            legalPerson.setAddress(selectedAddress.getAddressId());
        }

        if (selectedContact != null) {
            legalPerson.setContactPerson(selectedContact.getNaturalPersonId());
        }

        confirmed = true;
        stage.close();
    }

    public void setLegalPerson(LegalPerson legalPerson){
        this.legalPerson = legalPerson;

        tfCompanyName.setText(legalPerson.getCompanyName());
        tfInn.setText(legalPerson.getInn());
        tfKpp.setText(legalPerson.getKpp());
        tfOgrn.setText(legalPerson.getOgrn());
        tfPhoneNumber.setText(legalPerson.getPhone());
        ftEmail.setText(legalPerson.getEmail());
        cbAddress.setItems(addressViews);
        cbContactPerson.setItems(contactViews);
    }

    private boolean isValid(Address address){
        return address.getCountry() != null && !address.getCountry().isBlank()
                && address.getRegion() != null && !address.getRegion().isBlank()
                && address.getCity() != null && !address.getCity().isBlank()
                && address.getStreet() != null && !address.getStreet().isBlank()
                && address.getHouse() != null && !address.getHouse().isBlank();
    }

    private boolean isValid(NaturalPerson person){
        return person.getName() != null && !person.getName().isBlank()
                && person.getSurname() != null && !person.getSurname().isBlank()
                && person.getBirthDate() != null
                && person.getGender() != null && !person.getGender().isBlank()
                && person.getPassportSeries() != null && !person.getPassportSeries().isBlank()
                && person.getPassportNumber() != null && !person.getPassportNumber().isBlank()
                && person.getPhone() != null && !person.getPhone().isBlank()
                && person.getAddress() != null;
    }

    private boolean showContactDialog(NaturalPerson naturalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle("Введите данные контактного лица.");
        stage.setScene(scene);

        NaturalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setNaturalPerson(naturalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showAddressDialog(Address address){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("address-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 340, 300);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle("Введите адрес.");
        stage.setScene(scene);

        AddressInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setAddress(address);

        stage.showAndWait();
        return controller.isConfirmed();
    }
}
