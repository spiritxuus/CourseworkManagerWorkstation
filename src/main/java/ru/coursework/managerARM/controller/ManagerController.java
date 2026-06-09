package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.*;
import ru.coursework.managerARM.dao.impl.*;
import ru.coursework.managerARM.dto.*;
import javafx.scene.Scene;
import ru.coursework.managerARM.model.*;
import ru.coursework.managerARM.util.ReportService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerController {

    @Setter
    private Stage stage;

    @FXML
    private ComboBox<EquipmentCategoryView> cbSearchEquipCategory;

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
    private TableColumn<ClientView, String> clientAddressColumnEquip;

    @FXML
    private TableColumn<ClientView, String> clientEmailColumnEquip;

    @FXML
    private TableColumn<ClientView, String> clientNameColumnEquip;

    @FXML
    private TableColumn<ClientView, String> clientPhoneColumnEquip;

    @FXML
    private TableColumn<ClientView, String> clientTypeColumnEquip;

    @FXML
    private TableView<ClientView> clientsTable;

    @FXML
    private TableView<ClientView> clientsTableEquip;

    @FXML
    private TableColumn<RentalContractView, String> contractDescColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractActualDateColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractClientColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractCreateDateColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractDepositColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractFinalPriceColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractPhotoColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractPlannedDateColumn;

    @FXML
    private TableColumn<RentalContractView, String> contractStatusColumn;

    @FXML
    private TableView<RentalContractView> contractsTable;

    @FXML
    private TableColumn<Equipment, String> equipDepositColumn;

    @FXML
    private TableColumn<Equipment, String> equipDescColumn;

    @FXML
    private TableColumn<Equipment, String> equipInvNumColumn;

    @FXML
    private TableColumn<Equipment, String> equipManufacturerColumn;

    @FXML
    private TableColumn<Equipment, String> equipModelColumn;

    @FXML
    private TableColumn<Equipment, String> equipNameColumn;

    @FXML
    private TableColumn<Equipment, String> equipPhotoPathColumn;

    @FXML
    private TableColumn<Equipment, String> equipRentPriceColumn;

    @FXML
    private TableColumn<Equipment, String> equipRepairColumn;

    @FXML
    private TableColumn<Equipment, String> equipSerialColumn;

    @FXML
    private TableColumn<Equipment, String> equipStatusColumn;

    @FXML
    private TableView<Equipment> equipmentTable;

    @FXML
    private TableColumn<RentalHistory, String> historyDateColumn;

    @FXML
    private TableColumn<RentalHistory, String> historyDescColumn;

    @FXML
    private TableColumn<RentalHistory, String> historyTypeColumn;

    @FXML
    private TableView<RentalHistory> historyTable;

    @FXML
    private TableColumn<ReservationView, String> reservClientColumn;

    @FXML
    private TableColumn<ReservationView, String> reservEndColumn;

    @FXML
    private TableColumn<ReservationView, String> reservEquipColumn;

    @FXML
    private TableColumn<ReservationView, String> reservStartColumn;

    @FXML
    private TableView<ReservationView> reservationsTable;

    @FXML
    private TableColumn<ReturnView, String> returnClientNameColumn;

    @FXML
    private TableColumn<ReturnView, String> returnEquipNameColumn;

    @FXML
    private TableColumn<ReturnView, String> returnDamageColumn;

    @FXML
    private TableColumn<ReturnView, String> returnDateColumn;

    @FXML
    private TableColumn<ReturnView, String> returnDeductionColumn;

    @FXML
    private TableColumn<ReturnView, String> returnDescColumn;

    @FXML
    private TableColumn<ReturnView, String> returnPhotoColumn;

    @FXML
    private TableColumn<ReturnView, String> returnRepairColumn;

    @FXML
    private TableView<ReturnView> returnsTable;

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

    private ObservableList<ReturnView> returnViews = FXCollections.observableArrayList();

    private ObservableList<RentalHistory> historyViews = FXCollections.observableArrayList();

    private final ClientDao clientDao;

    private final EquipmentDao equipmentDao;

    private final ReservationDao reservationDao;

    private final NaturalPersonDao naturalPersonDao;

    private final LegalPersonDao legalPersonDao;

    private final RepairDao repairDao;

    private final RentalContractDao contractDao;

    private final PaymentDao paymentDao;

    private final ReturnOfEquipmentDao returnDao;

    private final RentalHistoryDao historyDao;

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    private ResourceBundle bundle = MainApplication.getAppBundle();

    public ManagerController() {
        this.clientDao = new ClientDaoImpl();
        this.naturalPersonDao = new NaturalPersonDaoImpl();
        this.legalPersonDao = new LegalPersonDaoImpl();
        this.equipmentDao = new EquipmentDaoImpl();
        this.reservationDao = new ReservationDaoImpl();
        this.repairDao = new RepairDaoImpl();
        this.contractDao = new RentalContractDaoImpl();
        this.paymentDao = new PaymentDaoImpl();
        this.returnDao = new ReturnOfEquipmentDaoImpl();
        this.historyDao = new RentalHistoryDaoImpl();
    }

    @FXML
    void initialize(){
        clientTypeColumn.setCellValueFactory(new PropertyValueFactory<>("clientType"));
        clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
        clientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));

        clientTypeColumnEquip.setCellValueFactory(new PropertyValueFactory<>("clientType"));
        clientNameColumnEquip.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientPhoneColumnEquip.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        clientEmailColumnEquip.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
        clientAddressColumnEquip.setCellValueFactory(new PropertyValueFactory<>("clientAddress"));

        equipNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        equipManufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        equipModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        equipInvNumColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryNumber"));
        equipSerialColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        equipRentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentalPricePerDay"));
        equipDepositColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        equipStatusColumn.setCellValueFactory(new PropertyValueFactory<>("conditionStatus"));
        equipRepairColumn.setCellValueFactory(new PropertyValueFactory<>("requiresRepair"));
        equipPhotoPathColumn.setCellValueFactory(new PropertyValueFactory<>("photo"));
        equipDescColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        contractClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        contractCreateDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        contractPlannedDateColumn.setCellValueFactory(new PropertyValueFactory<>("plannedReturnDate"));
        contractActualDateColumn.setCellValueFactory(new PropertyValueFactory<>("actualReturnDate"));
        contractDepositColumn.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        contractFinalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        contractStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        contractDescColumn.setCellValueFactory(new PropertyValueFactory<>("issueConditionDesc"));
        contractPhotoColumn.setCellValueFactory(new PropertyValueFactory<>("issueConditionPhoto"));

        reservClientColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        reservEquipColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
        reservStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        reservEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        returnClientNameColumn.setCellValueFactory(new PropertyValueFactory<>("contractClientName"));
        returnEquipNameColumn.setCellValueFactory(new PropertyValueFactory<>("contractEquipmentName"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnDescColumn.setCellValueFactory(new PropertyValueFactory<>("conditionDesc"));
        returnPhotoColumn.setCellValueFactory(new PropertyValueFactory<>("conditionPhoto"));
        returnDamageColumn.setCellValueFactory(new PropertyValueFactory<>("damageAmount"));
        returnDeductionColumn.setCellValueFactory(new PropertyValueFactory<>("deductionAmount"));
        returnRepairColumn.setCellValueFactory(new PropertyValueFactory<>("repairRequired"));

        historyDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        historyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        historyDescColumn.setCellValueFactory(new PropertyValueFactory<>("details"));

        clientViews.setAll(clientDao.getAllViews());
        logger.info("clientsView size = {}, table items = {}", clientViews.size(), clientsTable.getItems().size());
        equipViews.setAll(equipmentDao.getAll());
        reservationsViews.setAll(reservationDao.getAllViews());
        contractViews.setAll(contractDao.getAllViews());
        returnViews.setAll(returnDao.getAllViews());
        historyViews.setAll(historyDao.getAll());

        clientsTable.setItems(clientViews);
        clientsTableEquip.setItems(clientViews);
        equipmentTable.setItems(equipViews);
        reservationsTable.setItems(reservationsViews);
        contractsTable.setItems(contractViews);
        returnsTable.setItems(returnViews);
        historyTable.setItems(historyViews);

        categoriesView.setAll(equipmentDao.getCategory());
        cbSearchEquipCategory.setItems(categoriesView);
    }

    @FXML
    void OnAddReturnButtonClick(ActionEvent event) {
        ReturnOfEquipment returnOfEquipment = new ReturnOfEquipment();

        if (showReturnDialog(returnOfEquipment, false) && isValid(returnOfEquipment)){
            returnDao.add(returnOfEquipment);
            returnViews.setAll(returnDao.getAllViews());
        }
    }

    @FXML
    void OnEditEquipButtonClick(ActionEvent event) {
        Equipment equipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (equipment == null) {
            logger.info("OnEditEquipButtonClick() nothing is redacted");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_redacted"));
            alert.showAndWait();
            return;
        }

        boolean confirmed = showEquipDialog(equipment, true);
        if (!confirmed) return;

        if (!isValid(equipment)) {
            logger.info("OnEditEquipButtonClick() incorrect data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.incorrect_data"));
            alert.showAndWait();
            return;
        }

        equipmentDao.update(equipment);
        equipViews.setAll(equipmentDao.getAll());
    }

    @FXML
    void onAddClientButtonClick(ActionEvent event) {
        ClientChooseController controller = showClientDialog();
        if (controller == null) {
            return;
        }

        if (controller.getSelectedType() == ClientChooseController.ClientType.NATURAL) {
            NaturalClientInfoController naturalController = controller.getNaturalController();
            if (naturalController == null || !naturalController.isConfirmed()) {
                return;
            }

            NaturalPerson naturalPerson = naturalController.getNaturalPerson();
            Long naturalPersonId = naturalPersonDao.add(naturalPerson);

            Client client = new Client(null, naturalPersonId, null);
            clientDao.add(client);
        } else if (controller.getSelectedType() == ClientChooseController.ClientType.LEGAL) {
            LegalClientInfoController legalController = controller.getLegalController();
            if (legalController == null || !legalController.isConfirmed()) {
                return;
            }

            LegalPerson legalPerson = legalController.getLegalPerson();
            Long legalPersonId = legalPersonDao.add(legalPerson);

            Client client = new Client(null, null, legalPersonId);
            clientDao.add(client);
        } else {
            return;
        }

        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onAddContractButtonClick(ActionEvent event) {
        ReservationView selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            logger.info("onAddContractButtonClick() reservation is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.choose_reserv"));
            alert.showAndWait();
            return;
        }

        RentalContract contract = new RentalContract();
        contract.setReservationId(selectedReservation.getReservationId());
        contract.setClientId(selectedReservation.getClientId());
        contract.setPlannedReturnDate(selectedReservation.getEndDate());
        contract.setActualReturnDate(null);
        contract.setStatus(1);

        if (showContractDialog(contract, selectedReservation, false) && isValid(contract)){
            contractDao.add(contract);
            contractViews.setAll(contractDao.getAllViews());
        }
    }

    @FXML
    void onAddEquipButtonClick(ActionEvent event) {
        Equipment equipment = new Equipment();

        if (showEquipDialog(equipment, false) && isValid(equipment)){
            equipmentDao.add(equipment);
            equipViews.setAll(equipmentDao.getAll());
        }
    }

    @FXML
    void onDeleteClientButtonClick(ActionEvent event) {
        ClientView clientToDelete = clientsTable.getSelectionModel().getSelectedItem();
        if (clientToDelete == null) {
            logger.info("onDeleteClientButtonClick() client is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.client_not_selected"));
            alert.showAndWait();
            return;
        }

        clientDao.delete(clientToDelete.getClientId());
        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onDeleteContractReservButtonClick(ActionEvent event) {
        RentalContractView contractToDelete = contractsTable.getSelectionModel().getSelectedItem();
        ReservationView reservationToDelete = reservationsTable.getSelectionModel().getSelectedItem();


        boolean contractSelected = contractToDelete != null;
        boolean reservationSelected = reservationToDelete != null;

        if (contractSelected == reservationSelected) {
            logger.info("onDeleteContractReservButtonClick() both objects are choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(contractSelected
                    ? bundle.getString("warning.choose_one_object")
                    : bundle.getString("warning.choose_contract_or_reserv"));
            alert.showAndWait();
            return;
        }

        if (contractSelected) {
            contractDao.delete(contractToDelete.getContractId());
            contractViews.setAll(contractDao.getAllViews());
        } else {
            reservationDao.delete(reservationToDelete.getReservationId());
            reservationsViews.setAll(reservationDao.getAllViews());
        }
    }

    @FXML
    public void onDeleteEquipButtonClick(ActionEvent event) {
        Equipment equipmentToDelete = equipmentTable.getSelectionModel().getSelectedItem();
        if (equipmentToDelete == null) {
            logger.info("onDeleteEquipButtonClick() equipment is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.equip_not_selected"));
            alert.showAndWait();
            return;
        }

        equipmentDao.delete(equipmentToDelete.getEquipmentId());
        equipViews.setAll(equipmentDao.getAll());
    }

    @FXML
    void onDeleteHistoryButtonClick(ActionEvent event) {
        RentalHistory historyToDelete = historyTable.getSelectionModel().getSelectedItem();

        if (historyToDelete == null) {
            logger.info("onDeleteHistoryButtonClick() history is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.history_not_selected"));
            alert.showAndWait();
            return;
        }

        historyDao.delete(historyToDelete.getHistoryId());
        historyViews.setAll(historyDao.getAll());
    }

    @FXML
    void onDeleteReturnButotnClick(ActionEvent event) {
        ReturnView returnOfEquipmentToDelete = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipmentToDelete == null) {
            logger.info("onDeleteReturnButotnClick() returnOfEquipment is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.return_not_selected"));
            alert.showAndWait();
            return;
        }

        returnDao.delete(returnOfEquipmentToDelete.getReturnId());
        returnViews.setAll(returnDao.getAllViews());
    }

    @FXML
    void onEditClientButtonClick(ActionEvent event) {
        ClientView client = clientsTable.getSelectionModel().getSelectedItem();

        if(client == null) {
            logger.info("onEditClientButtonClick() client is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_redacted"));
            alert.showAndWait();
            return;
        }

        if ("Физическое лицо".equals(client.getClientType())){
            NaturalPerson naturalPerson = naturalPersonDao.getById(client.getNaturalPersonId()).orElse(null);

            if (naturalPerson == null){
                logger.info("onEditClientButtonClick() natural person not found");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.natural_not_found"));
                alert.showAndWait();
                return;
            }

            boolean confirmed = showNaturalPersonDialog(naturalPerson, true);
            if(!confirmed) return;

            if (!isValid(naturalPerson)){
                logger.info("onEditClientButtonClick() incorrect data");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.incorrect_data"));
                alert.showAndWait();
                return;
            }

            naturalPersonDao.update(naturalPerson);
            clientViews.setAll(clientDao.getAllViews());
        }
        else{
            LegalPerson legalPerson = legalPersonDao.getById(client.getLegalPersonId()).orElse(null);

            if (legalPerson == null) {
                logger.info("onEditClientButtonClick() legal person not found");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.legal_not_found"));
                alert.showAndWait();
                return;
            }

            boolean confirmed = showLegalPersonDialog(legalPerson, true);
            if (!confirmed) {
                return;
            }

            if (!isValid(legalPerson)) {
                logger.info("onEditClientButtonClick() incorrect data");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.incorrect_data"));
                alert.showAndWait();
                return;
            }

            legalPersonDao.update(legalPerson);
            clientViews.setAll(clientDao.getAllViews());
        }
    }

    @FXML
    void onEditContractButtonClick(ActionEvent event) {
        RentalContractView contractToEdit = contractsTable.getSelectionModel().getSelectedItem();
        ReservationView reservationToEdit = reservationsTable.getSelectionModel().getSelectedItem();

        if (contractToEdit == null && reservationToEdit == null) {
            logger.info("onEditContractButtonClick() contract or reservation not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_redacted"));
            alert.showAndWait();
            return;
        }

        if (reservationToEdit != null && contractToEdit != null) {
            logger.info("onEditContractButtonClick() both objects are choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.choose_one_object"));
            alert.showAndWait();
            return;
        }

        if (contractToEdit != null) {
            RentalContract contract = contractDao.getById(contractToEdit.getContractId()).orElse(null);
            ReservationView reservationView = findReservationViewById(contractToEdit.getReservationId());

            if (contract == null || reservationView == null) {
                logger.info("onEditContractButtonClick() objects are not found");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.contract_or_reserv_not_found"));
                alert.showAndWait();
                return;
            }

            boolean confirmed = showContractDialog(contract, reservationView, true);
            if (!confirmed) return;

            if (!isValid(contract)) {
                logger.info("onEditContractButtonClick() incorrect data");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("warning.title"));
                alert.setHeaderText(bundle.getString("warning.incorrect_data"));
                alert.showAndWait();
                return;
            }

            contractDao.update(contract);
            contractViews.setAll(contractDao.getAllViews());
            return;
        }

        Reservation reservation = reservationDao.getById(reservationToEdit.getReservationId()).orElse(null);

        if (reservation == null) {
            logger.info("onEditContractButtonClick() reservation not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.reserv_not_found"));
            alert.showAndWait();
            return;
        }

        boolean confirmed = showReservDialog(reservation, reservationToEdit, true);
        if (!confirmed) return;

        if (!isValid(reservation)) {
            logger.info("onEditContractButtonClick() incorrect data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.incorrect_data"));
            alert.showAndWait();
            return;
        }

        reservationDao.update(reservation);
        reservationsViews.setAll(reservationDao.getAllViews());
    }

    @FXML
    void onEditReturnButtonClick(ActionEvent event) {
        ReturnView returnOfEquipmentSelected = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipmentSelected == null) {
            logger.info("onEditReturnButtonClick() returnOfEquipment is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_redacted"));
            alert.showAndWait();
            return;
        }

        ReturnOfEquipment returnOfEquipment = returnDao.getById(returnOfEquipmentSelected.getReturnId()).orElse(null);

        if (returnOfEquipment == null) {
            logger.info("onEditReturnButtonClick() returnOfEquipment is not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.return_not_found"));
            alert.showAndWait();
            return;
        }

        boolean confirmed = showReturnDialog(returnOfEquipment, true);
        if (!confirmed) return;

        if (!isValid(returnOfEquipment)) {
            logger.info("onEditReturnButtonClick() incorrect data");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.incorrect_data"));
            alert.showAndWait();
            return;
        }

        returnDao.update(returnOfEquipment);
        returnViews.setAll(returnDao.getAllViews());
    }

    @FXML
    void onRepairButtonClick(ActionEvent event) {
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (selectedEquipment == null) {
            logger.info("onEditReturnButtonClick() selectedEquipment is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.equip_not_selected"));
            alert.showAndWait();
            return;
        }

        Repair repair = new Repair();
        repair.setEquipment(selectedEquipment.getEquipmentId());
        repair.setRepairStatus(1);

        if (showRepairDialog(repair, selectedEquipment, false) && isValid(repair)){
            repairDao.add(repair);
            equipViews.setAll(equipmentDao.getAll());
        }
    }

    @FXML
    void onReserveButtonClick(ActionEvent event) {
        ClientView selectedClient = clientsTableEquip.getSelectionModel().getSelectedItem();
        Equipment selectedEquipment = equipmentTable.getSelectionModel().getSelectedItem();

        if (selectedClient == null || selectedEquipment == null) {
            logger.info("onReserveButtonClick() selectedEquipment or selectedClient is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.equip_and_client_not_selected"));
            alert.showAndWait();
            return;
        }

        Reservation reservation = new Reservation();
        reservation.setClient(selectedClient.getClientId());
        reservation.setEquipment(selectedEquipment.getEquipmentId());


        ReservationView reservationView = new ReservationView(
                null,
                selectedClient.getClientId(),
                selectedClient.getNaturalPersonId(),
                selectedClient.getLegalPersonId(),
                selectedClient.getClientName(),
                selectedEquipment.getName(),
                null,
                null
        );

        if (showReservDialog(reservation, reservationView, false) && isValid(reservation)){
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
        reservationsViews.setAll(reservationDao.getAllViews());
        contractViews.setAll(contractDao.getAllViews()
        );
    }

    @FXML
    void onResetEquipClientButtonClick(ActionEvent event) {
        clientViews.setAll(clientDao.getAllViews());
        equipViews.setAll(equipmentDao.getAll());
    }

    @FXML
    void onResetHistoryButtonClick(ActionEvent event) {
        historyViews.setAll(historyDao.getAll());
    }


    @FXML
    void onResetReturnButtonClick(ActionEvent event) {
        returnViews.setAll(returnDao.getAllViews());
    }

    @FXML
    void onSearchClientButtonClick(ActionEvent event) {
       String companyText = tfSearchClientCompany.getText() == null ? "" : tfSearchClientCompany.getText().trim();
       String surnameText = tfSearchClientSurname.getText() == null ? "" : tfSearchClientSurname.getText().trim();

        if (companyText.isEmpty() && surnameText.isEmpty()) {
            logger.info("onSearchClientButtonClick() no data for search");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.no_data_for_search"));
            alert.showAndWait();
            return;
        }

        if (!companyText.isEmpty() && !surnameText.isEmpty()) {
            logger.info("onSearchClientButtonClick() both fields contains text");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.only_one_field_for_search"));
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
            logger.info("onSearchClientButtonClick() no data found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_found"));
            alert.showAndWait();
        }
    }

    @FXML
    void onSearchContractButtonClick(ActionEvent event) {
        String contractReservSurnameText = tfSearchContractReservClient.getText() == null ? "" : tfSearchContractReservClient.getText().trim().toLowerCase();

        if (contractReservSurnameText.isEmpty()) {
            logger.info("onSearchContractButtonClick() no data for search");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.no_data_for_search"));
            alert.showAndWait();
            return;
        }

        List<RentalContractView> filteredContracts = contractDao.getAllViews().stream()
                .filter(contract ->
                        (contract.getClientName() != null && contract.getClientName().toLowerCase().contains(contractReservSurnameText))
                )
                .toList();

        List<ReservationView> filteredReserv = reservationDao.getAllViews().stream()
                .filter(reservation ->
                        (reservation.getClientName() != null && reservation.getClientName().toLowerCase().contains(contractReservSurnameText))
                )
                .toList();

        contractViews.setAll(filteredContracts);
        reservationsViews.setAll(filteredReserv);

        tfSearchContractReservClient.clear();

        if (filteredContracts.isEmpty() && filteredReserv.isEmpty()) {
            logger.info("onSearchContractButtonClick() no data found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_found"));
            alert.showAndWait();
        }
        else if (filteredContracts.isEmpty()){
            logger.info("onSearchContractButtonClick() no contract found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.contracts_not_found"));
            alert.showAndWait();
        }
        else if (filteredReserv.isEmpty()){
            logger.info("onSearchContractButtonClick() no reservation found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.reservs_not_found"));
            alert.showAndWait();
        }
    }

    @FXML
    void onSearchEquipButtonClick(ActionEvent event) {
        EquipmentCategoryView selectedCategory = cbSearchEquipCategory.getValue();
        String equipNameText = tfSearchEquipName.getText() == null ? "" : tfSearchEquipName.getText().trim();

        if (selectedCategory == null && equipNameText.isEmpty()) {
            logger.info("onSearchEquipButtonClick() no data for search");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.no_data_for_search"));
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
            logger.info("onSearchEquipButtonClick() no data found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.nothing_found"));
            alert.showAndWait();
        }
    }


    @FXML
    void onShowContractButtonClick(ActionEvent event) {
        ReturnView returnOfEquipment = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipment == null) {
            logger.info("onShowContractButtonClick() returnOfEquipment is not selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.return_not_selected"));
            alert.showAndWait();
            return;
        }

        RentalContract contract = contractDao.getById(returnOfEquipment.getContractId()).orElse(null);
        if (contract == null) {
            logger.info("onShowContractButtonClick() contract is not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.contract_reserv_not_found"));
            alert.showAndWait();
            return;
        }

        ReservationView reservationView = findReservationViewById(contract.getReservationId());
        if (reservationView == null) {
            logger.info("onShowContractButtonClick() reservation not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.reserv_contract_not_found"));
            alert.showAndWait();
            return;
        }

        boolean confirmed = showContractDialog(contract, reservationView, false);
        if (!confirmed) return;
    }

    @FXML
    void onShowPaymentButtonClick(ActionEvent event) {
        RentalContractView contract = contractsTable.getSelectionModel().getSelectedItem();

        if (contract == null) {
            logger.info("onShowPaymentButtonClick() reservation not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.reserv_not_found"));
            alert.showAndWait();
            return;
        }

        Payment payment = paymentDao.getByContract(contract.getContractId()).orElse(null);

        if (payment == null) {
            logger.info("onShowContractButtonClick() payment not found");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setHeaderText(bundle.getString("warning.payment_not_found"));
            alert.showAndWait();
            return;
        }

        boolean confirmed = showPaymentDialog(payment);
        if (!confirmed) return;
    }

    @FXML
    void onCreateReportButtonClick(ActionEvent event) {
        ReportService service = new ReportService();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        String filePath = "report_" + year + "_" + month + ".txt";
        try {
            logger.info("onCreateReportButtonClick() report saved");
            service.generateReportToFile(year, month, filePath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("warning.title"));
            alert.setContentText(bundle.getString("warning.report_saved_path") + " " + filePath);
            alert.showAndWait();
        } catch (IOException e) {
            logger.error("Error while writing report", e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(bundle.getString("warning.error_saving_file") + " " + e.getMessage());
            alert.showAndWait();
        }
    }

    private ReservationView findReservationViewById(Long reservationId) {
        return reservationsViews.stream()
                .filter(r -> r.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    private ClientChooseController showClientDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("client-choose-view.fxml"),
                bundle
        );

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening client window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return null;
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle(bundle.getString("client.title_choose"));
        stage.setScene(scene);

        ClientChooseController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.showAndWait();
        return controller;
    }

    private boolean showNaturalPersonDialog(NaturalPerson naturalPerson, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening natural person window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("natural.title_edit") : bundle.getString("natural.title_add"));
        stage.setScene(scene);

        NaturalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setNaturalPerson(naturalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showLegalPersonDialog(LegalPerson legalPerson, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("legal-client-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening legal person window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("legal.title_edit") : bundle.getString("legal.title_add"));
        stage.setScene(scene);

        LegalClientInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setLegalPerson(legalPerson);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showEquipDialog(Equipment equipment, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("equipment-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening equipment window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("equipment.title_edit") : bundle.getString("equipment.title_add"));
        stage.setScene(scene);

        EquipmentInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setEquipment(equipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showReservDialog(Reservation reservation, ReservationView reservationView, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reservation-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening reservation window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());
        stage.setTitle(editMode ? bundle.getString("reservation.title_edit") : bundle.getString("reservation.title_add"));
        stage.setScene(scene);

        ReservationInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setReservation(reservation, reservationView);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showRepairDialog(Repair repair, Equipment equipment, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("repair-request-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening repair window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("repair.title_edit") : bundle.getString("repair.title_add"));
        stage.setScene(scene);

        RepairRequestController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setRepair(repair, equipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showContractDialog(RentalContract contract, ReservationView reservation, Boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("contract-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
            scene.getRoot().applyCss();
            scene.getRoot().layout();
        } catch (IOException e) {
            logger.error("Error while opening contract window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("contract.title_edit") : bundle.getString("contract.title_add"));
        stage.setScene(scene);

        ContractInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setContract(contract, reservation);
        controller.setEditMode(editMode);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showPaymentDialog(Payment payment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("payment-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening payment window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(bundle.getString("payment.title_show"));
        stage.setScene(scene);

        PaymentInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setPayment(payment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showReturnDialog(ReturnOfEquipment returnOfEquipment, boolean editMode){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("return-equip-info-view.fxml"), bundle);
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening return window", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
            return false;
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle(editMode ? bundle.getString("return.title_edit") : bundle.getString("return.title_add"));
        stage.setScene(scene);

        ReturnInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setReturnOfEquipment(returnOfEquipment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean isValid(NaturalPerson person){
        return person.getName() != null && !person.getName().isBlank()
                && person.getSurname() != null && !person.getSurname().isBlank()
                && person.getBirthDate() != null
                && person.getGender() != null && !person.getGender()
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
                && repair.getRepairStatus() != null
                && repair.getRepairCost() != null;
    }

    private boolean isValid(RentalContract contract){
        return contract.getReservationId() != null
                && contract.getClientId() != null
                && contract.getIssueDate() != null
                && contract.getPlannedReturnDate() != null
                && contract.getDepositAmount() != null
                && contract.getTotalAmount() != null
                && contract.getStatus() != null
                && contract.getIssueConditionDesc() != null && !contract.getIssueConditionDesc().isBlank()
                && contract.getIssueConditionPhoto() != null && !contract.getIssueConditionPhoto().isBlank();

    }

    private boolean isValid(ReturnOfEquipment returnOfEquipment){
        return returnOfEquipment.getContract() != null
                && returnOfEquipment.getReturnDate() != null
                && returnOfEquipment.getConditionDesc() != null && !returnOfEquipment.getConditionDesc().isBlank()
                && returnOfEquipment.getConditionPhoto() != null && !returnOfEquipment.getConditionPhoto().isBlank()
                && returnOfEquipment.getDamageAmount() != null
                && returnOfEquipment.getDeductionAmount() != null
                && returnOfEquipment.getRepairRequired() != null;

    }
}
