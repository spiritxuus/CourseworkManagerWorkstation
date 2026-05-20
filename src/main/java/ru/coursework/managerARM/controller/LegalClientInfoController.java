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
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;
import java.io.IOException;
import java.util.Objects;

public class LegalClientInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    private Stage stage;

    @Getter
    private LegalPerson legalPerson;

    @FXML
    private ComboBox<AddressView> cbAddress;

    @FXML
    private ComboBox<NaturalPersonView> cbContactPhone;

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

    private ObservableList<String> phonesList = FXCollections.observableArrayList();

    private ObservableList<String> addressList = FXCollections.observableArrayList();

    private NaturalPersonDao naturalPersonDao;

    private AddressDao addressDao;
    //TODO ЧЕК ЧАТЖПТ ДЛЯ РЕДАКТИРОВАНИЯ РПОБЛЕМЫНХ МОМЕНТВ

    @FXML
    void initialize(){
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.addressDao = new AddressDaoImpl();

        cbContactPhone.setItems(phonesList);
        cbAddress.setItems(addressList);
        phonesList.setAll(naturalPersonDao.getAllByPhone());
        addressList.setAll(addressDao.getAllConcat()); //TODO РЕАЛИЗОВАТЬ И ЗАТЕСТИТЬ
    }


    @FXML
    void onAddAddrButton(ActionEvent event) {
        Address address = new Address();
        showAddressDialog(address);

        if(showAddressDialog(address) && isValid(address)){
            addressDao.add(address);
            addressList.setAll(addressDao.getAllConcat());
        }
        else{
            //new Alert(Alert.AlertType.WARNING, "Введены некорректные данные.", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    void onAddContactButton(ActionEvent event) {
        NaturalPerson naturalPerson = new NaturalPerson();
        showContactDialog(naturalPerson);

        if(showContactDialog(naturalPerson) && isValid(naturalPerson)){
            naturalPersonDao.add(naturalPerson);
            phonesList.setAll(naturalPersonDao.getAllByPhone());
        }
        else{
            //new Alert(Alert.AlertType.WARNING, "Введены некорректные данные.", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        legalPerson.setCompanyName(tfCompanyName.getText());
        legalPerson.setInn(tfInn.getText());
        legalPerson.setKpp(tfKpp.getText());
        legalPerson.setOgrn(tfOgrn.getText());
        legalPerson.setPhone(tfPhoneNumber.getText());
        legalPerson.setEmail(ftEmail.getText());
        legalPerson.setAddress(Long.valueOf(cbAddress.getValue())); //TODO ПОДУМАТЬ, КАК УСТАНАВЛИВАТЬ СТРОКУ АДРЕСА
        legalPerson.setContactPerson(Long.valueOf(cbContactPhone.getValue()));
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
        cbAddress.setItems(addressList);
        cbContactPhone.setItems(phonesList);
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
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ContactDialog
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
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ContactDialog
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
