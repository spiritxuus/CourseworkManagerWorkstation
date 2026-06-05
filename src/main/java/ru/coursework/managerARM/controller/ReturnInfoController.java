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
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.dao.ReturnOfEquipmentDao;
import ru.coursework.managerARM.dao.impl.EquipmentDaoImpl;
import ru.coursework.managerARM.dao.impl.ReturnOfEquipmentDaoImpl;
import ru.coursework.managerARM.dto.RentalContractView;
import ru.coursework.managerARM.dto.RentalContractViewCb;
import ru.coursework.managerARM.model.ReturnOfEquipment;

import java.io.File;

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
    private TextField tfRepair;

    private ObservableList<RentalContractViewCb> contractsView = FXCollections.observableArrayList();

    private ReturnOfEquipmentDao returnDao;

    private String photoPath;

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
        } catch (Exception exc) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки изображения.", ButtonType.OK).showAndWait(); //TODO логирование ClientChoose

        }
    }

}
