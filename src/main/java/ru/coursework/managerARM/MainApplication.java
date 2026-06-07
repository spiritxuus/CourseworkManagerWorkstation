package ru.coursework.managerARM;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import ru.coursework.managerARM.controller.LoginController;
import ru.coursework.managerARM.util.DbUtils;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Getter
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);

        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Введите данные.");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws SQLException {
        DbUtils.closeConnection();
    }
}
