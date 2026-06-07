package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.ReturnOfEquipmentDao;
import ru.coursework.managerARM.dao.impl.ReturnOfEquipmentDaoImpl;
import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.model.ReturnOfEquipment;

import java.io.File;
import java.math.BigDecimal;

public class ReturnInfoController {

    @Setter
    @Getter
    private boolean confirmed = false;

    @Setter
    private Stage stage;

    @Getter
    private ReturnOfEquipment returnOfEquipment;

    @FXML
    private ComboBox<RentalContractViewCb> cbContract;

    @FXML
    private ComboBox<String> cbRepairReq;

    @FXML
    private DatePicker dpReturnDate;

    @FXML
    private ImageView mainImage;

    @FXML
    private TextField tfConditionDesc;

    @FXML
    private TextField tfDamage;

    @FXML
    private TextField tfDeduction;

    private ObservableList<RentalContractViewCb> contractsView = FXCollections.observableArrayList();

    private ReturnOfEquipmentDao returnDao;

    private String photoPath;

    private static final Logger logger = LoggerFactory.getLogger(ReturnInfoController.class);


    @FXML
    void initialize(){
        this.returnDao = new ReturnOfEquipmentDaoImpl();

        contractsView.setAll(returnDao.getContract());
        cbContract.setItems(contractsView);

        cbRepairReq.setItems(FXCollections.observableArrayList(
                "Да",
                "Нет"
        ));
    }


    @FXML
    void onOkayButtonClick(ActionEvent event) {
        RentalContractViewCb selectedContract = cbContract.getValue();

        if (selectedContract != null){
            returnOfEquipment.setContract(selectedContract.getContractId());
        }
        else{
            logger.info("return onOkayButtonClick() contract is not choosed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Выберите контракт.");
            alert.showAndWait();
            return;
        }

        try{
            returnOfEquipment.setReturnDate(dpReturnDate.getValue());
            returnOfEquipment.setConditionDesc(tfConditionDesc.getText().trim());
            returnOfEquipment.setConditionPhoto(photoPath);
            returnOfEquipment.setDamageAmount (new BigDecimal(tfDamage.getText().trim()));
            returnOfEquipment.setDeductionAmount(new BigDecimal(tfDeduction.getText().trim()));
            returnOfEquipment.setRepairRequired("Да".equals(cbRepairReq.getValue()));

            confirmed = true;
            stage.close();
        } catch (Exception e){
            logger.info("return onOkayButtonClick() some fields are empty");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Не все поля заполнены.");
            alert.showAndWait();
        }
    }

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
        } catch (Exception e) {
            logger.error("Error while opening photo", e);
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки изображения.", ButtonType.OK).showAndWait();
        }
    }

    public void setReturnOfEquipment(ReturnOfEquipment returnOfEquipment){
        this.returnOfEquipment = returnOfEquipment;

        cbContract.setItems(contractsView);
        cbContract.setValue(
                contractsView.stream()
                        .filter(c -> c.getContractId().equals(returnOfEquipment.getContract()))
                        .findFirst()
                        .orElse(null));

        dpReturnDate.setValue(returnOfEquipment.getReturnDate());
        tfConditionDesc.setText(returnOfEquipment.getConditionDesc());

        if (returnOfEquipment.getConditionPhoto() != null && !returnOfEquipment.getConditionPhoto().isBlank()) {
            Image img = new Image(new File(returnOfEquipment.getConditionPhoto()).toURI().toString());
            mainImage.setImage(img);
            photoPath = returnOfEquipment.getConditionPhoto();
        } else {
            photoPath = null;
        }

        tfDamage.setText(String.valueOf(returnOfEquipment.getDamageAmount()));
        tfDeduction.setText(String.valueOf(returnOfEquipment.getDeductionAmount()));

        if (Boolean.TRUE.equals(returnOfEquipment.getRepairRequired())) {
            cbRepairReq.setValue("Да");
        } else {
            cbRepairReq.setValue("Нет");
        }
    }
}
