package ru.coursework.managerARM.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dto.ClientView;
import ru.coursework.managerARM.model.Client;
import javafx.scene.Scene;

import java.io.IOException;

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
    private TableColumn<?, ?> historyContractIdColumn;

    @FXML
    private TableColumn<?, ?> historyDateColumn;

    @FXML
    private TableColumn<?, ?> historyDescColumn;

    @FXML
    private TableColumn<?, ?> historyTypeColumn;

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

    @FXML
    void OnAddReturnButtonClick(ActionEvent event) {

    }

    @FXML
    void OnEditEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onAddClientButtonClick(ActionEvent event) {
        Client client = new Client();
        showClientDialog(client);
    }

    @FXML
    void onAddContractClick(ActionEvent event) {

    }

    @FXML
    void onAddEquipButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteClientButtonClick(ActionEvent event) {

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
    void onResetButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchClientButtonClick(ActionEvent event) {

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
}
