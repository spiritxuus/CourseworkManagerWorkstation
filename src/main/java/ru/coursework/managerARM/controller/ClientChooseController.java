package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Setter;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;

import java.io.IOException;

public class ClientChooseController {

    @Setter
    private Stage stage;

    @FXML
    private BorderPane rootPane;

    @FXML
    private ComboBox<String> cbChooseClientType;


    @FXML
    void onClientTypeSelected(ActionEvent event) {
        if (cbChooseClientType.getValue() == null) {
            return;
        }

        try {
            FXMLLoader fxmlLoader;
            if (cbChooseClientType.getValue().equals("Физическое лицо")) {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("natural-client-info-view.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(MainApplication.class.getResource("legal-client-info-view.fxml"));
            }

            rootPane.setCenter(fxmlLoader.load());

            if (cbChooseClientType.getValue().equals("Физическое лицо")) {
                NaturalClientInfoController controller = fxmlLoader.getController();
                controller.setNaturalPerson(new NaturalPerson());
            } else {
                LegalClientInfoController controller = fxmlLoader.getController();
                controller.setLegalPerson(new LegalPerson());
            }

        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait(); //TODO логирование ClientChoose
        }
    }

    @FXML
    public void initialize() {
        cbChooseClientType.setItems(FXCollections.observableArrayList(
                "Физическое лицо",
                "Юридическое лицо"
        ));
    }


    @FXML
    void onExitButtonClick(ActionEvent event) {
        stage.close();
    }
}

