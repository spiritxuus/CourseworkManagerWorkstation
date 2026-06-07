module ru.coursework.managerARM {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires javafx.graphics;
    requires jdk.compiler;
    requires javafx.base;
    requires org.slf4j;

    opens ru.coursework.managerARM to javafx.fxml;
    exports ru.coursework.managerARM;
    exports ru.coursework.managerARM.controller;
    opens ru.coursework.managerARM.controller to javafx.fxml;
}