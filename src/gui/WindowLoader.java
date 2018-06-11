package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by VaniR on 09.06.2018.
 * Project: PRUEFUNGSLEISTUNG PRAKTISCHE ANWENDUNGSENTWICKLUNG SS 2018
 */
public class WindowLoader extends Stage {
    Stage stage;
    public WindowLoader(String fxml) throws IOException {
        stage = this;
        Parent root = FXMLLoader.load(getClass().getResource(fxml));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
