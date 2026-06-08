module ru.coursework.managerARM {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires javafx.graphics;
    requires jdk.compiler;
    requires javafx.base;
    requires org.slf4j;


    opens ru.coursework.managerARM.controller to javafx.fxml;
    opens ru.coursework.managerARM.dao to javafx.base;
    opens ru.coursework.managerARM.dto to javafx.base;
    opens ru.coursework.managerARM.model to javafx.base;
    opens ru.coursework.managerARM.util to javafx.base;
    opens ru.coursework.managerARM to javafx.fxml;
    exports ru.coursework.managerARM;
    exports ru.coursework.managerARM.controller;
    exports ru.coursework.managerARM.dao;
    exports ru.coursework.managerARM.dto;
    exports ru.coursework.managerARM.model;
    exports ru.coursework.managerARM.util;
}