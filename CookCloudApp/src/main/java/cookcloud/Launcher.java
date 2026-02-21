package cookcloud;

import cookcloud.utils.UtilsBD;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Launcher extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Lanzamos un hilo que inicializa hibernate para evitar esperas iniciales
        new Thread(UtilsBD::getEntityManagerFactory).start();

        // Cargamos el fxml
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("vista/fxml/Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 550);

        stage.setTitle("CookCloud"); // Ponemos un titulo al marco
        stage.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png")); // Añadimos el logo al marco
        stage.setScene(scene); // Añadimos la escena al marco
        stage.show(); // Mostramos la ventana

    }
}
