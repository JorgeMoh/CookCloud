package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MisRecetasController {

    @FXML
    public BorderPane bpLayaut;
    @FXML
    public Button btMisRecetas;
    @FXML
    public Button btGuardados;

    private BackgroundController backgroundController;
    private Usuario user;

    @FXML
    public void initialize() {
        cargarMisRecetas();
    }

    public void cargarMisRecetas() {

        seleccionarBoton(btMisRecetas);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetasPropias.fxml"));
            Parent vistaLogin = loader.load();

            RecetasPropiasController controller = loader.getController();
            controller.setControlador(this);

            bpLayaut.setCenter(vistaLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarRecetasGuardadas(ActionEvent actionEvent) {

        seleccionarBoton(btGuardados);

    }

    public void setBackgroundController(BackgroundController backgroundController) {
        this.backgroundController = backgroundController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    private void seleccionarBoton(Button activo) {

        btMisRecetas.getStyleClass().remove("btSelect");
        btGuardados.getStyleClass().remove("btSelect");

        activo.getStyleClass().add("btSelect");
    }
}
