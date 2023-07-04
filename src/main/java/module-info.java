module com.policedb.policedb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.policedb.policedb to javafx.fxml;
    exports com.policedb.policedb;
}