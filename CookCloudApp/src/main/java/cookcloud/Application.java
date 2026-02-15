package cookcloud;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vista/fxml/Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 550);

        stage.setTitle("CookCloud");
        stage.setScene(scene);
        stage.show();

    }
}
