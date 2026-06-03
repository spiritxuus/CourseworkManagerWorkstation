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
import ru.coursework.managerARM.dao.*;
import ru.coursework.managerARM.dao.impl.*;
import ru.coursework.managerARM.dto.ClientView;
import javafx.scene.Scene;
import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.dto.ReservationView;
import ru.coursework.managerARM.model.*;

import java.io.IOException;
import java.util.List;

public class ManagerController {

    @FXML
    private ComboBox<?> cbSearchContract;

    @FXML
    private ComboBox<EquipmentCategoryView> cbSearchEquipCategory;

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
    private TableView<RentalContractView> contractsTable;

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
    private TableColumn<?, ?> equipPhotoPathColumn;

    @FXML
    private TableColumn<?, ?> equipRentPriceColumn;

    @FXML
    private TableColumn<?, ?> equipRepairColumn;

    @FXML
    private TableColumn<?, ?> equipSerialColumn;

    @FXML
    private TableColumn<?, ?> equipStatusColumn;

    @FXML
    private TableView<Equipment> equipmentTable;

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
    private TableColumn<?, ?> reservStartColumn;

    @FXML
    private TableView<ReservationView> reservationsTable;

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

    private ObservableList<Equipment> equipViews = FXCollections.observableArrayList();

    private ObservableList<ReservationView> reservationsViews = FXCollections.observableArrayList();

    private ObservableList<EquipmentCategoryView> categoriesView = FXCollections.observableArrayList();

    private ObservableList<RentalContractView> contractViews = FXCollections.observableArrayList();

    private final ClientDao clientDao;

    private final EquipmentDao equipmentDao;

    private final ReservationDao reservationDao;

    private final NaturalPersonDao naturalPersonDao;

    private final LegalPersonDao legalPersonDao;

    private final RepairDao repairDao;

    private final RentalContractDao contractDao;

    public ManagerController() {
        this.clientDao = new ClientDaoImpl();
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.legalPersonDao = new LegalPersonDaoImpl();
        this.equipmentDao = new EquipmentDaoImpl();
        this.reservationDao = new ReservationDaoImpl();
        this.repairDao = new RepairDaoImpl();
        this.contractDao = new RentalContractDaoImpl();
    }

    @FXML
    void initialize(){
        categoriesView.setAll(equipmentDao.getCategory());
        cbSearchEquipCategory.setItems(categoriesView);
    }

    @FXML
    void OnAddReturnButtonClick(ActionEvent event) {

    }

    @FXML
    void OnEditEquipButtonClick(ActionEvent event) {
        Equipment equipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (equipment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ничего не редактируется.");
            alert.showAndWait();
            return;
        }

        boolean confirmed = showEquipDialog(equipment);
        if (!confirmed) return;

        if (!isValid(equipment)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введены некорректные данные.");
            alert.showAndWait();
            return;
        }

        equipViews.setAll(equipmentDao.getAll());
        equipmentDao.update(equipment);
    }

    @FXML
    void onAddClientButtonClick(ActionEvent event) {
        showClientDialog();
        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onAddContractClick(ActionEvent event) {
        ReservationView selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        RentalContract contract = new RentalContract();
        contract.setReservationId(selectedReservation.getReservationId());
        contract.setClientId(selectedReservation.getClientId());

        if (showContractDialog(contract, selectedReservation) && isValid(contract)){
            contractDao.add(contract);
            contractViews.setAll(contractDao.getAllViews());
        }
    }

    @FXML
    void onAddEquipButtonClick(ActionEvent event) {
        Equipment equipment = new Equipment();

        if (showEquipDialog(equipment) && isValid(equipment)){
            equipmentDao.add(equipment);
            equipViews.setAll(equipmentDao.getAll());
        }
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
        /*
        RentalContractView contractToDelete = contractsTable.getSelectionModel().getSelectedItem();
        if (contractToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Контракт не выбран.");
            alert.showAndWait();
            return;
        }

        contractDao.delete(contractToDelete.getContractId());
        contractViews.setAll(contractDao.getAllViews());

         */
    }

    @FXML
    void onDeleteEquipButtonClick(ActionEvent event) {
        Equipment equipmentToDelete = equipmentTable.getSelectionModel().getSelectedItem();
        if (equipmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Оборудование не выбрано.");
            alert.showAndWait();
            return;
        }

        equipmentDao.delete(equipmentToDelete.getEquipmentId());
        equipViews.setAll(equipmentDao.getAll());
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
            NaturalPerson naturalPerson = naturalPersonDao.getById(client.getNaturalPersonId()).orElse(null);

            if (naturalPerson == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Физическое лицо не найдено.");
                alert.showAndWait();
                return;
            }

            boolean confirmed = showNaturalPersonDialog(naturalPerson);
            if(!confirmed) return;

            if (!isValid(naturalPerson)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Введены некорректные данные.");
                alert.showAndWait();
                return;
            }

            naturalPersonDao.update(naturalPerson);
            clientViews.setAll(clientDao.getAllViews());
        }
        else{
            LegalPerson legalPerson = legalPersonDao.getById(client.getLegalPersonId()).orElse(null);

            if (legalPerson == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Юридическое лицо не найдено.");
                alert.showAndWait();
                return;
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
                return;
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
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (selectedEquipment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите оборудование.");
            alert.showAndWait();
            return;
        }

        Repair repair = new Repair();
        repair.setEquipment(selectedEquipment.getEquipmentId());

        if (showRepairDialog(repair, selectedEquipment) && isValid(repair)){
            repairDao.add(repair);
            equipViews.setAll(equipmentDao.getAll());
        }
    }

    @FXML
    void onReserveButtonClick(ActionEvent event) {
        ClientView selectedClient = clientsTable.getSelectionModel().getSelectedItem();
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (selectedClient == null || selectedEquipment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите клиента и оборудование.");
            alert.showAndWait();
            return;
        }

        Reservation reservation = new Reservation();
        reservation.setClient(selectedClient.getClientId());
        reservation.setEquipment(selectedEquipment.getEquipmentId());

        if (showReservDialog(reservation, selectedClient, selectedEquipment) && isValid(reservation)){
            reservationDao.add(reservation);
            reservationsViews.setAll(reservationDao.getAllViews());
        }
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
        clientViews.setAll(clientDao.getAllViews());
        equipViews.setAll(equipmentDao.getAll());
    }

    @FXML
    void onResetHistoryButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchClientButtonClick(ActionEvent event) {
       String companyText = tfSearchClientCompany.getText() == null ? "" : tfSearchClientCompany.getText().trim();
       String surnameText = tfSearchClientSurname.getText() == null ? "" : tfSearchClientSurname.getText().trim();

        if (companyText.isEmpty() && surnameText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введите данные для поиска.");
            alert.showAndWait();
            return;
        }

        if (!companyText.isEmpty() && !surnameText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Заполните только одно поле поиска.");
            alert.showAndWait();
            return;
        }

        String query = !companyText.isEmpty() ? companyText.toLowerCase() : surnameText.toLowerCase();

        List<ClientView> filtered = clientDao.getAllViews().stream()
                .filter(client ->
                        (client.getClientName() != null && client.getClientName().toLowerCase().contains(query)) ||
                                (client.getClientPhone() != null && client.getClientPhone().toLowerCase().contains(query)) ||
                                (client.getClientEmail() != null && client.getClientEmail().toLowerCase().contains(query)) ||
                                (client.getClientAddress() != null && client.getClientAddress().toLowerCase().contains(query)) ||
                                (client.getClientType() != null && client.getClientType().toLowerCase().contains(query))
                )
                .toList();

        clientViews.setAll(filtered);

        tfSearchClientCompany.clear();
        tfSearchClientSurname.clear();

        if (filtered.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Ничего не найдено.");
            alert.showAndWait();
        }
    }

    @FXML
    void onSearchContractButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchEquipButtonClick(ActionEvent event) {
        EquipmentCategoryView selectedCategory = cbSearchEquipCategory.getValue();
        String equipNameText = tfSearchEquipName.getText() == null ? "" : tfSearchEquipName.getText().trim();

        if (selectedCategory == null && equipNameText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введите данные для поиска.");
            alert.showAndWait();
            return;
        }

        List<Equipment> filtered = equipmentDao.getAll().stream()
                .filter(equipment ->
                        (selectedCategory == null || equipment.getCategory().equals(selectedCategory.getCategoryId())) &&
                                (equipNameText.isEmpty() || (equipment.getName() != null) && equipment.getName().toLowerCase().contains(equipNameText)))
                .toList();

        equipViews.setAll(filtered);

        cbSearchEquipCategory.setValue(null);
        tfSearchEquipName.clear();

        if (filtered.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Ничего не найдено.");
            alert.showAndWait();
        }
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

    private void showClientDialog(){
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
    }

    private boolean showNaturalPersonDialog(NaturalPerson naturalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование клиента.");
        stage.setScene(scene);

        NaturalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setNaturalPerson(naturalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showLegalPersonDialog(LegalPerson legalPerson){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("legal-client-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование клиента.");
        stage.setScene(scene);

        LegalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setLegalPerson(legalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showEquipDialog(Equipment equipment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("equipment-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование оборудования.");
        stage.setScene(scene);

        EquipmentInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setEquipment(equipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showReservDialog(Reservation reservation, ClientView client, Equipment equipment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reservation-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Редактирование брони.");
        stage.setScene(scene);

        ReservationInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setReservation(reservation, client, equipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showRepairDialog(Repair repair, Equipment equipment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("repair-request-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Создание запроса на ремонт.");
        stage.setScene(scene);

        RepairRequestController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setRepair(repair, equipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showContractDialog(RentalContract contract, ReservationView reservation){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("contract-info-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return false;//TODO логирование ClientDialog
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Создание запроса на ремонт.");
        stage.setScene(scene);

        ContractInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setContract(contract, reservation);

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

    private boolean isValid(Equipment equipment){
        return equipment.getCategory() != null
                && equipment.getName() != null && !equipment.getName().isBlank()
                && equipment.getManufacturer() != null && !equipment.getManufacturer().isBlank()
                && equipment.getModel() != null && !equipment.getModel().isBlank()
                && equipment.getInventoryNumber() != null && !equipment.getInventoryNumber().isBlank()
                && equipment.getSerialNumber() != null && !equipment.getSerialNumber().isBlank()
                && equipment.getRentalPricePerDay() != null
                && equipment.getDepositAmount() != null
                && equipment.getConditionStatus() != null && !equipment.getConditionStatus().isBlank()
                && equipment.getRequiresRepair() != null
                && equipment.getPhoto() != null && !equipment.getPhoto().isBlank()
                && equipment.getDescription() != null && !equipment.getDescription().isBlank();

    }

    private boolean isValid(Reservation reservation){
        return reservation.getClient() != null
                && reservation.getEquipment() != null
                && reservation.getStartDate() != null
                && reservation.getEndDate() != null;
    }

    private boolean isValid(Repair repair){
        return repair.getEquipment() != null
                && repair.getRepairStatus() != null && !repair.getRepairStatus().isBlank()
                && repair.getRepairCost() != null;
    }

    private boolean isValid(RentalContract contract){
        return contract.getReservationId() != null
                && contract.getClientId() != null
                && contract.getIssueDate() != null
                && contract.getPlannedReturnDate() != null
                && contract.getActualReturnDate() != null
                && contract.getDepositAmount() != null
                && contract.getTotalAmount() != null
                && contract.getStatus() != null && !contract.getStatus().isBlank()
                && contract.getIssueConditionDesc() != null && !contract.getIssueConditionDesc().isBlank()
                && contract.getIssueConditionPhoto() != null && !contract.getIssueConditionPhoto().isBlank();

    }
}
