package cookcloud.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PrincipalController {

    @FXML
    public BorderPane background;
    @FXML
    public VBox form;

    @FXML
    public void initialize() {
        // Cargamos nada más empezar el login
        cargarLogin();
    }

    /**
     * Metodo que carga y enlaza los controllers entre la vista general y el Login
     */
    public void cargarLogin() {

        try {

            // Cargamos la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Login.fxml"));
            Parent vistaLogin = loader.load();

            // Obtenemos el controlador y le pasamos este para conectarlos
            LoginController controller = loader.getController();
            controller.setControladorPrincipal(this);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaLogin);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga y enlaza los controllers entre la vista general y el Register
     */
    public void cargarRegister() {

        try {

            // Cargamos la vista del registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Register.fxml"));
            Parent vistaRegister = loader.load();

            //  Obtenemos el controlador y le pasamos este para conectarlos
            RegisterController controller = loader.getController();
            controller.setControlador(this);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaRegister);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
