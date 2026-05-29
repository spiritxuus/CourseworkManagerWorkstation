package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.EquipmentDao;
import ru.coursework.managerARM.dao.impl.EquipmentDaoImpl;
import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.model.Equipment;

import java.io.File;
import java.math.BigDecimal;

public class EquipmentInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private Equipment equipment;

    @FXML
    private ImageView mainImage;

    @FXML
    private ComboBox<EquipmentCategoryView> cbCategory;

    @FXML
    private ComboBox<String> cbRepairReq;

    @FXML
    private TextField tfCondition;

    @FXML
    private TextField tfDepositAmount;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfInvNumber;

    @FXML
    private TextField tfManufacturer;

    @FXML
    private TextField tfModel;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfRentPerDay;

    @FXML
    private TextField tfSerialNumber;

    private ObservableList<EquipmentCategoryView> categoriesView = FXCollections.observableArrayList();

    private EquipmentDao equipmentDao;

    private String photoPath;

    @FXML
    void initialize(){
        this.equipmentDao = new EquipmentDaoImpl();

        categoriesView.setAll(equipmentDao.getCategory());
        cbCategory.setItems(categoriesView);

        cbRepairReq.setItems(FXCollections.observableArrayList(
                "Да",
                "Нет"
        ));
    }

    @FXML
    void onOkayButtonClick(ActionEvent event) {
        EquipmentCategoryView selectedCategory = cbCategory.getValue();
        photoPath = equipment.getPhoto();

        if (selectedCategory != null) {
            equipment.setCategory(selectedCategory.getCategoryId());
        }

        equipment.setName(tfName.getText().trim());
        equipment.setManufacturer(tfManufacturer.getText().trim());
        equipment.setModel(tfModel.getText().trim());
        equipment.setInventoryNumber(tfInvNumber.getText().trim());
        equipment.setSerialNumber(tfSerialNumber.getText().trim());
        equipment.setRentalPricePerDay(new BigDecimal(tfRentPerDay.getText().trim()));
        equipment.setDepositAmount(new BigDecimal(tfDepositAmount.getText().trim()));
        equipment.setConditionStatus(tfCondition.getText().trim());
        equipment.setRequiresRepair("Да".equals(cbRepairReq.getValue()));
        equipment.setPhoto(photoPath);
        equipment.setDescription(tfDescription.getText().trim());

        confirmed = true;
        stage.close();
    } //TODO ПОДУМАТЬ, СМ. ЧАТЖПТ, ИСПРАВИТЬ ОСТАЛЬНЫЕ!

    @FXML
    void onOpenPhotoButtonClick(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            FileChooser.ExtensionFilter filter = new
                    FileChooser.ExtensionFilter("All image files",
                    "*.png", "*.jpg", "*.gif");
            fileChooser.getExtensionFilters().addAll(filter);
            File file =
                    fileChooser.showOpenDialog(MainApplication.getStage());
            if (file != null) {
                Image img = new Image(file.toURI().toString());
                mainImage.setImage(img);
                photoPath = file.getAbsolutePath();
            }
        } catch (Exception exc) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки изображения.", ButtonType.OK).showAndWait(); //TODO логирование ClientChoose

        }
    }

    public void setEquipment(Equipment equipment){
        this.equipment = equipment;

        cbCategory.setItems(categoriesView);
        tfName.setText(equipment.getName());
        tfManufacturer.setText(equipment.getManufacturer());
        tfModel.setText(equipment.getModel());
        tfInvNumber.setText(equipment.getInventoryNumber());
        tfSerialNumber.setText(equipment.getSerialNumber());
        tfRentPerDay.setText(String.valueOf(equipment.getRentalPricePerDay()));
        tfDepositAmount.setText(String.valueOf(equipment.getDepositAmount()));
        tfCondition.setText(equipment.getConditionStatus());
        if (equipment.getRequiresRepair() == true) cbRepairReq.setValue("Да");
        else cbRepairReq.setValue("Нет");
        if (equipment.getPhoto() != null && !equipment.getPhoto().isBlank()) {
            Image img = new Image(new File(equipment.getPhoto()).toURI().toString());
            mainImage.setImage(img);
            photoPath = equipment.getPhoto();
        }
        tfDescription.setText(equipment.getDescription());
    }

}
