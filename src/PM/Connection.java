package PM;

import javafx.scene.control.Alert;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private java.sql.Connection conn = null;

    public java.sql.Connection connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/password_db", "root", "");
            return conn;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
