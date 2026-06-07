package ru.coursework.managerARM.controller;

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

public class LoginController {

    @Setter
    private Stage stage;

    @FXML
    private ComboBox<?> cbLanguage;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfPassword;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @FXML
    void onAuthenticateButtonClick(ActionEvent event) {
        String username = tfLogin.getText().trim();
        String password = tfPassword.getText().trim();
        try {
            DbUtils.initConnection(username, password);
            showMainDialog();
        } catch (SQLException e) {
            logger.error("Error while logging in", e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание.");
            alert.setHeaderText("Ошибка подключения.");
            alert.showAndWait();
        }
    }

    @FXML
    void onCancelButtonClick(ActionEvent event) {
        stage.close();
    }

    private void showMainDialog(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("manager-window-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), 600, 500);
        } catch (IOException e) {
            logger.error("Error while opening main window", e);
            new Alert(Alert.AlertType.WARNING, "Ошибка загрузки окна.", ButtonType.OK).showAndWait();
        }
        Stage stage = new Stage();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainApplication.getStage());

        stage.setTitle("Главное окно.");
        stage.setScene(scene);

        ManagerController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.showAndWait();
    }
}

