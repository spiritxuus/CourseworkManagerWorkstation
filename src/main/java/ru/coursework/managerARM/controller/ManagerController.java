package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.ClientDao;
import ru.coursework.managerARM.dao.LegalPersonDao;
import ru.coursework.managerARM.dao.NaturalPersonDao;
import ru.coursework.managerARM.dao.impl.ClientDaoImpl;
import ru.coursework.managerARM.dao.impl.LegalPersonDaoImpl;
import ru.coursework.managerARM.dao.impl.NaturalPersonDaoImpl;
import ru.coursework.managerARM.dto.AddressView;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.model.Address;
import ru.coursework.managerARM.model.Client;
import javafx.scene.Scene;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;

import java.io.IOException;
import java.util.Objects;

public class ManagerController {

    @FXML
    private ComboBox<?> cbSearchContract;

    @FXML
    private ComboBox<?> cbSearchEquipCategory;

    @FXML
    private ComboBox<?> cbSearchHistoryDate;

    @FXML
    private TableColumn<ClientView, String> clientAddressColumn;

    @FXML
    private TableColumn<ClientView, String> clientEmailColumn;

    @FXML
    private TableColumn<ClientView, String> clientNameColumn;

    @FXML
    private TableColumn<ClientView, String> clientPhoneColumn;

    @FXML
    private TableColumn<ClientView, String> clientTypeColumn;

    @FXML
    private TableView<ClientView> clientsTable;

    @FXML
    private TableColumn<?, ?> contractDescColumn;

    @FXML
    private TableColumn<?, ?> contractActualDateColumn;

    @FXML
    private TableColumn<?, ?> contractClientColumn;

    @FXML
    private TableColumn<?, ?> contractCreateDateColumn;

    @FXML
    private TableColumn<?, ?> contractDepositColumn;

    @FXML
    private TableColumn<?, ?> contractFinalPriceColumn;

    @FXML
    private TableColumn<?, ?> contractPhotoColumn;

    @FXML
    private TableColumn<?, ?> contractPlannedDateColumn;

    @FXML
    private TableColumn<?, ?> contractReservIdColumn;

    @FXML
    private TableColumn<?, ?> contractStatusColumn;

    @FXML
    private TableView<?> contractsTable;

    @FXML
    private TableColumn<?, ?> equipCategoryColumn;

    @FXML
    private TableColumn<?, ?> equipDepositColumn;

    @FXML
    private TableColumn<?, ?> equipDescColumn;

    @FXML
    private TableColumn<?, ?> equipInvNumColumn;

    @FXML
    private TableColumn<?, ?> equipManufacturerColumn;

    @FXML
    private TableColumn<?, ?> equipModelColumn;

    @FXML
    private TableColumn<?, ?> equipNameColumn;

    @FXML
    private TableColumn<?, ?> equipRentPriceColumn;

    @FXML
    private TableColumn<?, ?> equipRepairColumn;

    @FXML
    private TableColumn<?, ?> equipSerialColumn;

    @FXML
    private TableColumn<?, ?> equipStatusColumn;

    @FXML
    private TableView<?> equipmentTable;

    @FXML
    private TableColumn<?, ?> historyContractIdColumn;

    @FXML
    private TableColumn<?, ?> historyDateColumn;

    @FXML
    private TableColumn<?, ?> historyDescColumn;

    @FXML
    private TableColumn<?, ?> historyTypeColumn;

    @FXML
    private TableView<?> historyTable;

    @FXML
    private TableColumn<?, ?> reservClientcolumn;

    @FXML
    private TableColumn<?, ?> reservDescColumn;

    @FXML
    private TableColumn<?, ?> reservEndColumn;

    @FXML
    private TableColumn<?, ?> reservEquipColumn;

    @FXML
    private TableColumn<?, ?> reservIdColumn;

    @FXML
    private TableColumn<?, ?> reservStartColumn;

    @FXML
    private TableView<?> reservationsTable;

    @FXML
    private TableColumn<?, ?> returnContractIdColumn;

    @FXML
    private TableColumn<?, ?> returnDamageColumn;

    @FXML
    private TableColumn<?, ?> returnDateColumn;

    @FXML
    private TableColumn<?, ?> returnDeductionColumn;

    @FXML
    private TableColumn<?, ?> returnDescColumn;

    @FXML
    private TableColumn<?, ?> returnPhotoColumn;

    @FXML
    private TableColumn<?, ?> returnRepairColumn;

    @FXML
    private TableView<?> returnsTable;

    @FXML
    private TextField tfSearchClientCompEquip;

    @FXML
    private TextField tfSearchClientCompany;

    @FXML
    private TextField tfSearchClientEquip;

    @FXML
    private TextField tfSearchClientSurname;

    @FXML
    private TextField tfSearchContractReservClient;

    @FXML
    private TextField tfSearchEquipName;

    private ObservableList<ClientView> clientViews = FXCollections.observableArrayList();

    private final ClientDao clientDao;

    private final NaturalPersonDao naturalPersonDao;

    private final LegalPersonDao legalPersonDao;

    public ManagerController() {
        this.clientDao = new ClientDaoImpl();
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.legalPersonDao = new LegalPersonDaoImpl();
    }

    @FXML
    void OnAddReturnButtonClick(ActionEvent event) {

    }

    @FXML
    void OnEditEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onAddClientButtonClick(ActionEvent event) {
        Client client = new Client();
        boolean confirmed = showClientDialog(client);
    }

    @FXML
    void onAddContractClick(ActionEvent event) {

    }

    @FXML
    void onAddEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteClientButtonClick(ActionEvent event) {
        ClientView clientToDelete = clientsTable.getSelectionModel().getSelectedItem();
        if (clientToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Клиент не выбран.");
            alert.showAndWait();
            return;
        }

        clientDao.delete(clientToDelete.getClientId());
        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onDeleteContractReservButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteHistoryButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteReturnButotnClick(ActionEvent event) {

    }

    @FXML
    void onEditClientButtonClick(ActionEvent event) {
        ClientView client = clientsTable.getSelectionModel().getSelectedItem();

        if(client == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ничего не редактируется.");
            alert.showAndWait();
            return;
        }

        if ("Физическое лицо".equals(client.getClientType())){
            NaturalPerson naturalPerson = naturalPersonDao.getById(client.getClientId()).orElse(null);

            if (naturalPerson == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Физическое лицо не найдено.");
                alert.showAndWait();
            }

            boolean confirmed = showNaturalPersonDialog(naturalPerson);
            if(!confirmed) return;

            if (!isValid(naturalPerson)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Введены некорректные данные.");
                alert.showAndWait();
            }

            naturalPersonDao.update(naturalPerson);
            clientViews.setAll(clientDao.getAllViews());
        }
        else{
            LegalPerson legalPerson = legalPersonDao.getById(client.getClientId()).orElse(null);

            if (legalPerson == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Юридическое лицо не найдено.");
                alert.showAndWait();
            }

            boolean confirmed = showLegalPersonDialog(legalPerson);
            if (!confirmed) {
                return;
            }

            if (!isValid(legalPerson)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Введены некорректные данные.");
                alert.showAndWait();
            }

            legalPersonDao.update(legalPerson);
            clientViews.setAll(clientDao.getAllViews());
        }
    }

    @FXML
    void onEditContractClick(ActionEvent event) {

    }

    @FXML
    void onEditReturnButtonClick(ActionEvent event) {

    }

    @FXML
    void onRepairButtonClick(ActionEvent event) {

    }

    @FXML
    void onReserveButtonClick(ActionEvent event) {

    }

    @FXML
    void onResetClientButtonClick(ActionEvent event) {
        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onResetContractReservButtonClick(ActionEvent event) {

    }

    @FXML
    void onResetEquipClientButtonClick(ActionEvent event) {

    }

    @FXML
    void onResetHistoryButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchClientButtonClick(ActionEvent event) {
        if (tfSearchClientCompany != null){
            LegalPerson legalPerson = legalPersonDao.getByCompany(String.valueOf(tfSearchClientCompany.getText()));
            if (legalPerson == null) clientViews.clear();
            else clientViews.setAll(clientDao.getAllViews());
        }
        else if (tfSearchClientSurname != null){
            NaturalPerson naturalPerson = naturalPersonDao.getBySurname(String.valueOf(tfSearchClientSurname.getText()));
            if (naturalPerson == null) clientViews.clear();
            else clientViews.setAll(clientDao.getAllViews());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Поиск невозможен.");
            alert.showAndWait();
        }
        tfSearchClientSurname.clear();
        tfSearchClientCompany.clear();
        //TODO ДОДЕЛАТЬ ПОИСК В MANAGER WINDOW
    }

    @FXML
    void onSearchContractButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchHistoryButton(ActionEvent event) {

    }

    @FXML
    void onShowContractButtonClick(ActionEvent event) {

    }

    @FXML
    void onShowPaymentButtonClick(ActionEvent event) {

    }

    private boolean showClientDialog(Client client){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("client-choose-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Выберите тип клиента.");
        stage.setScene(scene);

        ClientChooseController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showNaturalPersonDialog(NaturalPerson naturalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование клиента.");
        stage.setScene(scene);

        NaturalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showLegalPersonDialog(LegalPerson legalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("legal-client-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование клиента.");
        stage.setScene(scene);

        LegalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.showAndWait();
        return controller.isConfirmed();
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

    private boolean isValid(LegalPerson person){
        return person.getCompanyName() != null && !person.getCompanyName().isBlank()
                && person.getInn() != null && !person.getInn().isBlank()
                && person.getKpp() != null && !person.getKpp().isBlank()
                && person.getOgrn() != null && !person.getOgrn().isBlank()
                && person.getPhone() != null && !person.getPhone().isBlank()
                && person.getAddress() != null
                && person.getContactPerson() != null;

    }
}
