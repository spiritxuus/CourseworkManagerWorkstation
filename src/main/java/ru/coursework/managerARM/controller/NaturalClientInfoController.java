package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.AddressDao;
import ru.coursework.managerARM.dao.impl.AddressDaoImpl;
import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.model.NaturalPerson;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class NaturalClientInfoController {

    @Getter
    private boolean confirmed = false;

    private NaturalPerson naturalPerson;

    @Setter
    private Stage stage;

    @FXML
    private ComboBox<AddressView> cbAddress;

    @FXML
    private ComboBox<String> cbGender;

    @FXML
    private TextField ftEmail;

    @FXML
    private TextField tfPassportNumber;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassportSeries;

    @FXML
    private TextField tfPatronymic;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfSurname;

    private ObservableList<AddressView> addressViews = FXCollections.observableArrayList();

    private AddressDao addressDao;

    private static final Logger logger = LoggerFactory.getLogger(NaturalClientInfoController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();


    @FXML
    public void initialize() {
        cbGender.setItems(FXCollections.observableArrayList(
                bundle.getString("combo_box.male"),
                bundle.getString("combo_box.female")));

        this.addressDao = new AddressDaoImpl();

        cbAddress.setItems(addressViews);
        addressViews.setAll(addressDao.getAllViews());
    }

    public void setNaturalPerson(NaturalPerson naturalPerson){
        this.naturalPerson = naturalPerson;

        tfName.setText(naturalPerson.getName());
        tfSurname.setText(naturalPerson.getSurname());
        tfPatronymic.setText(naturalPerson.getPatronymic());
        dpBirthDate.setValue(naturalPerson.getBirthDate());
        tfPassportNumber.setText(naturalPerson.getPassportNumber());

        if (Boolean.TRUE.equals(naturalPerson.getGender())) {
            cbGender.setValue(bundle.getString("combo_box.male"));
        } else {
            cbGender.setValue(bundle.getString("combo_box.female"));
        }

        tfPassportSeries.setText(naturalPerson.getPassportSeries());
        tfPhoneNumber.setText(naturalPerson.getPhone());
        ftEmail.setText(naturalPerson.getEmail());
    }

    @FXML
    void onAddAddrButton(ActionEvent event) {
        Address address = new Address();

        if(showAddressDialog(address) && isValid(address)){
            addressDao.add(address);
            addressViews.setAll(addressDao.getAllViews());

            AddressView last = addressDao.getAllViews().getLast();
            cbAddress.getSelectionModel().select(last);
        }
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        AddressView selectedAddress = cbAddress.getValue();

        try{
            naturalPerson.setName(tfName.getText().trim());
            naturalPerson.setSurname(tfSurname.getText().trim());
            naturalPerson.setPatronymic(tfPatronymic.getText());
            naturalPerson.setBirthDate(dpBirthDate.getValue());
            naturalPerson.setGender(Objects.equals(cbGender.getValue(), bundle.getString("combo_box.male")));
            naturalPerson.setPassportSeries(tfPassportSeries.getText().trim());
            naturalPerson.setPassportNumber(tfPassportNumber.getText().trim());
            naturalPerson.setPhone(tfPhoneNumber.getText().trim());
            naturalPerson.setEmail(ftEmail.getText());

            if (selectedAddress != null) {
                naturalPerson.setAddress(selectedAddress.getAddressId());
            }
            else{
                logger.info("natural client onOkayButtonClick() address is not choosed");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.no_address"));
                alert.showAndWait();
                return;
            }

            confirmed = true;
            stage.close();
        } catch (Exception e) {
            logger.info("natural client onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.not_all_fields_are_written"));
            alert.showAndWait();
        }
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
        stage.setTitle(bundle.getString("warning.no_address"));
        stage.setScene(scene);

        AddressInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setAddress(address);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean isValid(Address address){
        return address.getCountry() != null && !address.getCountry().isBlank()
                && address.getRegion() != null && !address.getRegion().isBlank()
                && address.getCity() != null && !address.getCity().isBlank()
                && address.getStreet() != null && !address.getStreet().isBlank()
                && address.getHouse() != null && !address.getHouse().isBlank();
    }
}
