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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ResourceBundle;

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

    private static final Logger logger = LoggerFactory.getLogger(LegalClientInfoController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();


    @FXML
    void initialize(){
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.addressDao = new AddressDaoImpl();

        contactViews.setAll(naturalPersonDao.getAllViews());
        addressViews.setAll(addressDao.getAllViews());
        cbContactPerson.setItems(contactViews);
        cbAddress.setItems(addressViews);

    }


    @FXML
    void onAddAddrButton(ActionEvent event) {
        Address address = new Address();

        if (showAddressDialog(address) && isValid(address)) {
            addressDao.add(address);
            addressViews.setAll(addressDao.getAllViews());

            if (!addressViews.isEmpty()) {
                cbAddress.setValue(addressViews.get(addressViews.size() - 1));
            }
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

        try{
            legalPerson.setCompanyName(tfCompanyName.getText().trim());
            legalPerson.setInn(tfInn.getText().trim());
            legalPerson.setKpp(tfKpp.getText().trim());
            legalPerson.setOgrn(tfOgrn.getText().trim());
            legalPerson.setPhone(tfPhoneNumber.getText().trim());
            legalPerson.setEmail(ftEmail.getText());

            if (selectedAddress != null) {
                legalPerson.setAddress(selectedAddress.getAddressId());
            }
            else{
                logger.info("legal client onOkayButtonClick() address is not choosed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.no_address"));
                alert.showAndWait();
                return;
            }

            if (selectedContact != null) {
                legalPerson.setContactPerson(selectedContact.getNaturalPersonId());
            }
            else{
                logger.info("legal client onOkayButtonClick() contact person is not choosed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.no_contact_person"));
                alert.showAndWait();
                return;
            }

            confirmed = true;
            ((Stage) tfCompanyName.getScene().getWindow()).close();
        } catch (Exception e) {
            logger.info("legal client onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
            alert.showAndWait();
        }
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
                && person.getGender() != null
                && person.getPassportSeries() != null && !person.getPassportSeries().isBlank()
                && person.getPassportNumber() != null && !person.getPassportNumber().isBlank()
                && person.getPhone() != null && !person.getPhone().isBlank()
                && person.getAddress() != null;
    }

    private boolean showContactDialog(NaturalPerson naturalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening contract window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle(bundle.getString("contact.title"));
        stage.setScene(scene);

        NaturalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setNaturalPerson(naturalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showAddressDialog(Address address){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("address-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 340, 300);
        } catch (IOException e) {
            logger.error("Error while opening address window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle(bundle.getString("address.title_add"));
        stage.setScene(scene);

        AddressInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setAddress(address);

        stage.showAndWait();
        return controller.isConfirmed();
    }
}
