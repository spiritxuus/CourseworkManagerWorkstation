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
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.model.LegalPerson;
import ru.coursework.managerARM.model.NaturalPerson;

import java.io.IOException;
import java.util.ResourceBundle;

public class ClientChooseController {

    public enum ClientType {
        NATURAL,
        LEGAL
    }

    @Setter
    private Stage stage;

    @FXML
    private BorderPane rootPane;

    @FXML
    private ComboBox<String> cbChooseClientType;

    private static final Logger logger = LoggerFactory.getLogger(ClientChooseController.class);

    private final ResourceBundle bundle = MainApplication.getAppBundle();

    @Getter
    private ClientType selectedType;
    @Getter
    private NaturalClientInfoController naturalController;
    @Getter
    private LegalClientInfoController legalController;

    @FXML
    public void initialize() {
        cbChooseClientType.setItems(FXCollections.observableArrayList(
                bundle.getString("combo_box.natural_face"),
                bundle.getString("combo_box.legal_face")
        ));
    }

    @FXML
    void onClientTypeSelected(ActionEvent event) {
        if (cbChooseClientType.getValue() == null) {
            return;
        }

        try {
            if (cbChooseClientType.getValue().equals(bundle.getString("combo_box.natural_face"))) {
                selectedType = ClientType.NATURAL;

                FXMLLoader fxmlLoader = new FXMLLoader(
                        MainApplication.class.getResource("natural-client-info-view.fxml"),
                        bundle
                );
                rootPane.setCenter(fxmlLoader.load());

                naturalController = fxmlLoader.getController();
                naturalController.setNaturalPerson(new NaturalPerson());

            } else {
                selectedType = ClientType.LEGAL;

                FXMLLoader fxmlLoader = new FXMLLoader(
                        MainApplication.class.getResource("legal-client-info-view.fxml"),
                        bundle
                );
                rootPane.setCenter(fxmlLoader.load());

                legalController = fxmlLoader.getController();
                legalController.setLegalPerson(new LegalPerson());
            }
        } catch (IOException e) {
            logger.error("Error while opening client info form", e);
            new Alert(Alert.AlertType.WARNING, bundle.getString("error.show_dialog"), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    void onExitButtonClick(ActionEvent event) {
        stage.close();
    }
}

