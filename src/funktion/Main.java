package funktion;

import datenbank.DatenbankService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../gui/main.fxml"));
        primaryStage.setTitle("Praktische Anwendungsentwicklung");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




    }


    public void save(Kontoinhaber kontoinhaber) {

    }

    public static void main(String[] args) {

        launch(args);

    }
}
