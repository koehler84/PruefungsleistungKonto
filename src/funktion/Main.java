package funktion;

import datenbank.DatenbankService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        Kontoinhaber test = new Kontoinhaber(1, "Mad Max", "Str");
        Sparkonto testKonto1 = new Sparkonto(1, LocalDate.now(), 1000f,1);
        Girokonto testKonto2 = new Girokonto(3, LocalDate.now(), 1000f,10000f, 1);



        test.addKonto(testKonto1);
        test.addKonto(testKonto2);

        DatenbankService datenbank = new DatenbankService();
        datenbank.datenSpeichernKontoinhaber(test);
        datenbank.datenSpeichernKonto(testKonto2);
        Kontoinhaber konto = datenbank.datenLadenKontoinhaber(1);
        System.out.println();
    }
}
