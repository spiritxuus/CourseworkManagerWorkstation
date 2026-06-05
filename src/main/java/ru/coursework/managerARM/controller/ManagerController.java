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
import ru.coursework.managerARM.dto.*;
import javafx.scene.Scene;
import ru.coursework.managerARM.model.*;
import ru.coursework.managerARM.util.ReportService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ManagerController {

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
    private TableView<RentalHistory> historyTable;

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
    private TableView<ReturnOfEquipment> returnsTable;

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

    private ObservableList<ReturnOfEquipment> returnViews = FXCollections.observableArrayList();

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
        categoriesView.setAll(equipmentDao.getCategory());
        cbSearchEquipCategory.setItems(categoriesView);
    }

    @FXML
    void OnAddReturnButtonClick(ActionEvent event) {
        ReturnOfEquipment returnOfEquipment = new ReturnOfEquipment();

        if (showReturnDialog(returnOfEquipment) && isValid(returnOfEquipment)){
            returnDao.add(returnOfEquipment);
            returnViews.setAll(returnDao.getAll());
        }
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

        equipmentDao.update(equipment);
        equipViews.setAll(equipmentDao.getAll());
    }

    @FXML
    void onAddClientButtonClick(ActionEvent event) {
        showClientDialog();
        clientViews.setAll(clientDao.getAllViews());
    }

    @FXML
    void onAddContractButtonClick(ActionEvent event) {
        ReservationView selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите бронь.");
            alert.showAndWait();
            return;
        }

        RentalContract contract = new RentalContract();
        contract.setReservationId(selectedReservation.getReservationId());
        contract.setClientId(selectedReservation.getClientId());
        contract.setPlannedReturnDate(selectedReservation.getEndDate());

        if (showContractDialog(contract, selectedReservation, false) && isValid(contract)){
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
        RentalContractView contractToDelete = contractsTable.getSelectionModel().getSelectedItem();
        ReservationView reservationToDelete = reservationsTable.getSelectionModel().getSelectedItem();


        boolean contractSelected = contractToDelete != null;
        boolean reservationSelected = reservationToDelete != null;

        if (contractSelected == reservationSelected) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText(contractSelected
                    ? "Выберите только один объект."
                    : "Выберите контракт или бронь.");
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
        RentalHistory historyToDelete = historyTable.getSelectionModel().getSelectedItem();

        if (historyToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("История не выбрана.");
            alert.showAndWait();
            return;
        }

        historyDao.delete(historyToDelete.getHistoryId());
        historyViews.setAll(historyDao.getAll());
    }

    @FXML
    void onDeleteReturnButotnClick(ActionEvent event) {
        ReturnOfEquipment returnOfEquipmentToDelete = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Возврат оборудования не выбран.");
            alert.showAndWait();
            return;
        }

        returnDao.delete(returnOfEquipmentToDelete.getReturnId());
        returnViews.setAll(returnDao.getAll());
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
    void onEditContractButtonClick(ActionEvent event) {
        RentalContractView contractToEdit = contractsTable.getSelectionModel().getSelectedItem();
        ReservationView reservationToEdit = reservationsTable.getSelectionModel().getSelectedItem();

        if (contractToEdit == null && reservationToEdit == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ничего не редактируется.");
            alert.showAndWait();
            return;
        }

        if (reservationToEdit != null && contractToEdit != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите только один объект.");
            alert.showAndWait();
            return;
        }

        if (contractToEdit != null) {
            RentalContract contract = contractDao.getById(contractToEdit.getContractId()).orElse(null);
            ReservationView reservationView = findReservationViewById(contractToEdit.getReservationId());

            if (contract == null || reservationView == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Контракт или бронь не найдены.");
                alert.showAndWait();
                return;
            }

            boolean confirmed = showContractDialog(contract, reservationView, true);
            if (!confirmed) return;

            if (!isValid(contract)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание.");
                alert.setHeaderText("Введены некорректные данные.");
                alert.showAndWait();
                return;
            }

            contractDao.update(contract);
            contractViews.setAll(contractDao.getAllViews());
            return;
        }

        Reservation reservation = reservationDao.getById(reservationToEdit.getReservationId()).orElse(null);

        if (reservation == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Бронь не найдена.");
            alert.showAndWait();
            return;
        }

        boolean confirmed = showReservDialog(reservation, reservationToEdit, true);
        if (!confirmed) return;

        if (!isValid(reservation)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введены некорректные данные.");
            alert.showAndWait();
            return;
        }

        reservationDao.update(reservation);
        reservationsViews.setAll(reservationDao.getAllViews());
    }

    @FXML
    void onEditReturnButtonClick(ActionEvent event) {
        ReturnOfEquipment returnOfEquipment = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ничего не редактируется.");
            alert.showAndWait();
            return;
        }

        boolean confirmed = showReturnDialog(returnOfEquipment);
        if (!confirmed) return;

        if (!isValid(returnOfEquipment)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введены некорректные данные.");
            alert.showAndWait();
            return;
        }

        returnDao.update(returnOfEquipment);
        returnViews.setAll(returnDao.getAll());
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
        String contractReservSurnameText = tfSearchContractReservClient.getText() == null ? "" : tfSearchContractReservClient.getText().trim().toLowerCase();

        if (contractReservSurnameText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введите данные для поиска.");
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Ничего не найдено.");
            alert.showAndWait();
        }
        else if (filteredContracts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Контракты не найдены.");
            alert.showAndWait();
        }
        else if (filteredReserv.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат поиска");
            alert.setHeaderText("Брони не найдены.");
            alert.showAndWait();
        }
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
    void onShowContractButtonClick(ActionEvent event) {
        ReturnOfEquipment returnOfEquipment = returnsTable.getSelectionModel().getSelectedItem();

        if (returnOfEquipment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите возврат оборудования.");
            alert.showAndWait();
            return;
        }

        RentalContract contract = contractDao.getById(returnOfEquipment.getContract()).orElse(null);
        if (contract == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Контракт по этому возврату не найден.");
            alert.showAndWait();
            return;
        }

        ReservationView reservationView = findReservationViewById(contract.getReservationId());
        if (reservationView == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Бронь по этому контракту не найдена.");
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите контракт.");
            alert.showAndWait();
            return;
        }

        Payment payment = paymentDao.getByContract(contract.getContractId()).orElse(null);

        if (payment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Платёж по этому контракту не найден.");
            alert.showAndWait();
            return;
        }

        boolean confirmed = showPaymentDialog(payment);
        if (!confirmed) return;
    }

    @FXML
    void onCreateReportButtonClick(ActionEvent event) {
        //TODO доделать
        ReportService service = new ReportService();
        // Например, отчёт за текущий месяц
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        // Путь для сохранения (можно через FileChooser)
        String filePath = "report_" + year + "_" + month + ".txt";
        try {
            service.generateReportToFile(year, month, filePath);
            // Показать сообщение об успехе
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Отчёт сохранён в " + filePath);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ошибка записи файла: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private ReservationView findReservationViewById(Long reservationId) {
        return reservationsViews.stream()
                .filter(r -> r.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
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

    private boolean showReservDialog(Reservation reservation, ReservationView reservationView, boolean editMode){
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
        stage.setTitle(editMode ? "Редактирование брони." : "Создание брони."); //TODO ДОБАВИТЬ ТАКОЕ В ДРУгИЕ
        stage.setScene(scene);

        ReservationInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setReservation(reservation, reservationView);

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

    private boolean showContractDialog(RentalContract contract, ReservationView reservation, Boolean editMode){
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

        stage.setTitle("Редактирование контракта.");
        stage.setScene(scene);

        ContractInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setContract(contract, reservation);
        controller.setEditMode(editMode);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showPaymentDialog(Payment payment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("payment-info-view.fxml"));
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

        stage.setTitle("Информация о платеже.");
        stage.setScene(scene);

        PaymentInfoController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setPayment(payment);

        stage.showAndWait();
        return controller.isConfirmed();
    }

    private boolean showReturnDialog(ReturnOfEquipment returnOfEquipment){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("return-equip-info-view.fxml"));
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

        stage.setTitle("Редактирование возврата.");
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
