package util;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showAlertMessage(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.show();
    }
}
