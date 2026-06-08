package ru.coursework.managerARM;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.coursework.managerARM.controller.LoginController;
import ru.coursework.managerARM.util.DbUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplication extends Application {
    @Getter
    private static Stage stage;

    @Getter
    private static Locale appLocale = new Locale("ru", "RU");

    @Getter
    private static ResourceBundle appBundle = ResourceBundle.getBundle("i18n.main", appLocale);

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"),
                appBundle);
        Scene scene = new Scene(fxmlLoader.load(), 300, 200);

        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle(appBundle.getString("login.title"));
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws SQLException {
        DbUtils.closeConnection();
    }

    public static void setAppLocale(Locale locale) {
        appLocale = locale;
        appBundle = ResourceBundle.getBundle("i18n.main", locale);
        logger.info("Locale set to {}", locale);
    }
}
