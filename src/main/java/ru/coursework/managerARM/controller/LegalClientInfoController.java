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

    @Getter
    private LegalPerson legalPerson;

    @FXML
    private ComboBox<String> cbAddress;

    @FXML
    private ComboBox<String> cbContactPhone;

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

    private ObservableList<String> phones = FXCollections.observableArrayList();

    private ObservableList<String> address = FXCollections.observableArrayList();

    private NaturalPersonDao naturalPersonDao;

    private AddressDao addressDao;
    //TODO ЧЕК ЧАТЖПТ ДЛЯ РЕДАКТИРОВАНИЯ РПОБЛЕМЫНХ МОМЕНТВ

    @FXML
    void initialize(){
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.addressDao = new AddressDaoImpl();

        cbContactPhone.setItems(phones);
        cbAddress.setItems(address);
        phones.setAll(naturalPersonDao.getAllByPhone());
        address.setAll(addressDao.getAllConcat()); //TODO РЕАЛИЗОВАТЬ И ЗАТЕСТИТЬ
    }


    @FXML
    void onAddAddrButton(ActionEvent event) {
        Address address = new Address();
        showAddressDialog(address);

        if(Objects.equals(address.getCountry(), "") | Objects.equals(address.getRegion(), "")
                | Objects.equals(address.getCity(), "") | Objects.equals(address.getStreet(), "")
                | Objects.equals(address.getHouse(), "")){
            //new Alert(Alert.AlertType.WARNING, "Введены некорректные данные.", ButtonType.OK).showAndWait();
        }
        else{
            addressDao.add(address);
        }
    }

    @FXML
    void onAddContactButton(ActionEvent event) {
        NaturalPerson naturalPerson = new NaturalPerson();
        showContactDialog(naturalPerson);

        if(Objects.equals(naturalPerson.getName(), "") | Objects.equals(naturalPerson.getSurname(), "")
                | Objects.equals(naturalPerson.getPatronymic(), "") | Objects.equals(naturalPerson.getBirthDate(), "")
                | Objects.equals(naturalPerson.getGender(), "") | Objects.equals(naturalPerson.getPassportSeries(), "")
                | Objects.equals(naturalPerson.getPassportNumber(), "") | Objects.equals(naturalPerson.getPhone(), "")
                | Objects.equals(naturalPerson.getEmail(), "") | naturalPerson.getAddress() == null){
            //new Alert(Alert.AlertType.WARNING, "Введены некорректные данные.", ButtonType.OK).showAndWait();
        }
        else{
            naturalPersonDao.add(naturalPerson);
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
        legalPerson.setAddress(Long.valueOf(cbAddress.getValue()));
        legalPerson.setContactPerson(Long.valueOf(cbContactPhone.getValue()));
    }

    public void setLegalPerson(LegalPerson legalPerson){
        this.legalPerson = legalPerson;

        tfCompanyName.setText(legalPerson.getCompanyName());
        tfInn.setText(legalPerson.getInn());
        tfKpp.setText(legalPerson.getKpp());
        tfOgrn.setText(legalPerson.getOgrn());
        tfPhoneNumber.setText(legalPerson.getPhone());
        ftEmail.setText(legalPerson.getEmail());
        cbAddress.setItems(address);
        cbContactPhone.setItems(phones);
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

        stage.showAndWait();
        return controller.isConfirmed();
    }
}
