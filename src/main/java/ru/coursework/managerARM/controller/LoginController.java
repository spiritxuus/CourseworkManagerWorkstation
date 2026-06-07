package ru.coursework.managerARM.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.util.DbUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController {

    @Setter
    private Stage stage;

    @FXML
    private ComboBox<String> cbLanguage;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfPassword;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    void initialize(){
        cbLanguage.setItems(FXCollections.observableArrayList(
                "Русский",
                "English",
                "Polski"
        ));
        cbLanguage.getSelectionModel().selectFirst();
    }

    @FXML
    void onAuthenticateButtonClick(ActionEvent event) {
        String username = tfLogin.getText().trim();
        String password = tfPassword.getText();

        if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Введите логин.");
            alert.showAndWait();
            return;
        }

        try {
            DbUtils.initConnection(username, password);
            logger.info("Successful login for user {}", username);
            showMainDialog();
            stage.hide();
        } catch (SQLException e) {
            logger.error("Error while logging in for user {}", username, e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ошибка подключения.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        stage.close();
    }

    private void showMainDialog() {
        Locale locale = switch (cbLanguage.getValue()) {
            case "Русский" -> new Locale("ru", "RU");
            case "English" -> new Locale("en", "US");
            case "Polski" -> new Locale("pl", "PL");
            default -> new Locale("ru", "RU");
        };


        ResourceBundle bundle = ResourceBundle.getBundle("i18n.main", locale);

        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("manager-window-view.fxml"),
                bundle
        );

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening main window", e);
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
            return;
        }

        Stage mainStage = new Stage();
        mainStage.setTitle(bundle.getString("app.title"));
        mainStage.setScene(scene);

        ManagerController controller = fxmlLoader.getController();
        controller.setStage(mainStage);

        mainStage.show();
    }
}

