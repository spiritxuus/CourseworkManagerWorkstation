package ru.coursework.managerARM;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ru.coursework.managerARM.MainApplication;
import ru.coursework.managerARM.controller.EquipmentInfoController;
import ru.coursework.managerARM.controller.ManagerController;
import ru.coursework.managerARM.dao.EquipmentDao;
import ru.coursework.managerARM.dao.impl.EquipmentDaoImpl;
import ru.coursework.managerARM.dto.EquipmentCategoryView;
import ru.coursework.managerARM.model.Equipment;
import ru.coursework.managerARM.util.DbUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.util.WaitForAsyncUtils.waitFor;

public class EquipmentUiTest extends ApplicationTest {

    private final EquipmentDao equipmentDao = new EquipmentDaoImpl();

    private EquipmentInfoController dialogController;
    private Stage dialogStage;
    private ManagerController managerController;

    private String testPhotoPath;
    private Long firstCategoryId;

    private static final String DELETE_SEED_INV = "TEST-EQ-DELETE-SEED";
    private static final String PREFIX = "TEST-EQ-";

    @Override
    public void start(Stage stage) throws Exception {
        MainApplication.setAppLocale(new Locale("ru", "RU"));

        DbUtils.initConnection("manager", "pass");

        URL photoUrl = Objects.requireNonNull(
                getClass().getResource("/test_photo.jpg"),
                "Put test-photo.png into src/test/resources"
        );
        testPhotoPath = Path.of(photoUrl.toURI()).toAbsolutePath().toString();

        firstCategoryId = equipmentDao.getCategory().get(0).getCategoryId();

        seedDeleteEquipmentIfMissing();

        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("manager-window-view.fxml"),
                MainApplication.getAppBundle()
        );
        Parent root = loader.load();

        managerController = loader.getController();
        managerController.setStage(stage);

        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    @AfterEach
    void cleanUp() {
        List<Equipment> all = new ArrayList<>(equipmentDao.getAll());
        for (Equipment e : all) {
            if (e.getInventoryNumber() != null && e.getInventoryNumber().startsWith(PREFIX)) {
                equipmentDao.delete(e.getEquipmentId());
            }
        }

        if (dialogStage != null && dialogStage.isShowing()) {
            dialogStage.close();
        }
    }

    @AfterAll
    static void closeDb() {
        DbUtils.closeConnection();
    }

    @Test
    @DisplayName("Добавление оборудования")
    void testAddEquipmentPositive() throws Exception {
        String inv = PREFIX + "ADD-" + System.currentTimeMillis();

        DialogHandle handle = openEquipmentDialog(
                createEquipment(inv, firstCategoryId)
        );

        waitFor(3, TimeUnit.SECONDS, () -> lookup("#tfName").tryQuery().isPresent());

        interact(() -> handle.controller.onOkayButtonClick(new ActionEvent()));

        assertThat(handle.controller.isConfirmed()).isTrue();

        equipmentDao.add(handle.controller.getEquipment());

        boolean found = equipmentDao.getAll().stream()
                .anyMatch(e -> inv.equals(e.getInventoryNumber()));
        assertThat(found).isTrue();
    }

    @Test
    @DisplayName("Удаление оборудования")
    void testDeleteEquipmentPositive() throws TimeoutException {
        TableView<Equipment> table = lookup("#equipmentTable").queryTableView();

        waitFor(3, TimeUnit.SECONDS, () ->
                table.getItems().stream().anyMatch(e -> DELETE_SEED_INV.equals(e.getInventoryNumber()))
        );

        Equipment target = table.getItems().stream()
                .filter(e -> DELETE_SEED_INV.equals(e.getInventoryNumber()))
                .findFirst()
                .orElseThrow();

        interact(() -> {
            table.getSelectionModel().select(target);
            table.scrollTo(target);
        });

        interact(() -> managerController.onDeleteEquipButtonClick(new ActionEvent()));

        waitFor(3, TimeUnit.SECONDS, () ->
                equipmentDao.getAll().stream().noneMatch(e -> DELETE_SEED_INV.equals(e.getInventoryNumber()))
        );

        assertThat(equipmentDao.getAll())
                .noneMatch(e -> DELETE_SEED_INV.equals(e.getInventoryNumber()));
    }

    @Test
    @DisplayName("Нельзя сохранить оборудование без категории")
    void testAddEquipmentNoCategoryNegative() throws Exception {
        String inv = PREFIX + "NOCAT-" + System.currentTimeMillis();

        DialogHandle handle = openEquipmentDialog(
                createEquipment(inv, null)
        );

        waitFor(3, TimeUnit.SECONDS, () -> lookup("#tfName").tryQuery().isPresent());

        interact(() -> handle.controller.onOkayButtonClick(new ActionEvent()));

        assertThat(handle.controller.isConfirmed()).isFalse();

        boolean found = equipmentDao.getAll().stream()
                .anyMatch(e -> inv.equals(e.getInventoryNumber()));
        assertThat(found).isFalse();

        interact(() -> handle.stage.close());
    }

    @Test
    @DisplayName("Нельзя сохранить оборудование с неверной ценой")
    void testAddEquipmentInvalidPriceNegative() throws Exception {
        String inv = PREFIX + "BADPRICE-" + System.currentTimeMillis();

        DialogHandle handle = openEquipmentDialog(
                createEquipment(inv, firstCategoryId)
        );

        waitFor(3, TimeUnit.SECONDS, () -> lookup("#tfName").tryQuery().isPresent());

        TextField tfRent = (TextField) lookup("#tfRentPerDay").queryTextInputControl();
        interact(() -> tfRent.setText("abc"));

        interact(() -> handle.controller.onOkayButtonClick(new ActionEvent()));

        assertThat(handle.controller.isConfirmed()).isFalse();

        boolean found = equipmentDao.getAll().stream()
                .anyMatch(e -> inv.equals(e.getInventoryNumber()));
        assertThat(found).isFalse();

        interact(() -> handle.stage.close());
    }

    private void seedDeleteEquipmentIfMissing() {
        boolean exists = equipmentDao.getAll().stream()
                .anyMatch(e -> DELETE_SEED_INV.equals(e.getInventoryNumber()));

        if (!exists) {
            equipmentDao.add(createEquipment(DELETE_SEED_INV, firstCategoryId));
        }
    }

    private Equipment createEquipment(String inventoryNumber, Long categoryId) {
        return new Equipment(
                null,
                categoryId,
                "Тестовое оборудование",
                "TestBrand",
                "Model-1",
                inventoryNumber,
                "SN-" + inventoryNumber,
                new BigDecimal("1500"),
                new BigDecimal("2500"),
                "Новое",
                false,
                testPhotoPath,
                "Описание для теста"
        );
    }

    private DialogHandle openEquipmentDialog(Equipment equipment) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("equipment-info-view.fxml"),
                MainApplication.getAppBundle()
        );
        Parent root = loader.load();

        dialogController = loader.getController();
        dialogStage = new Stage();
        dialogStage.setScene(new Scene(root, 600, 500));

        dialogController.setStage(dialogStage);
        dialogController.setEquipment(equipment);

        dialogStage.show();

        return new DialogHandle(dialogStage, dialogController);
    }

    private static final class DialogHandle {
        private final Stage stage;
        private final EquipmentInfoController controller;

        private DialogHandle(Stage stage, EquipmentInfoController controller) {
            this.stage = stage;
            this.controller = controller;
        }
    }
}