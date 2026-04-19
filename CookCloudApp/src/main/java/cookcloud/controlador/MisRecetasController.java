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

    private GeneralController generalController;
    private Usuario user;

    public void cargarRecetasCreadas() {

        seleccionarBoton(btMisRecetas);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetasCreadas.fxml"));
            Parent vistaLogin = loader.load();

            RecetasCreadasController controller = loader.getController();
            controller.setControlador(this);
            controller.setControladorBackground(generalController);
            controller.setUsuario(user);


            bpLayaut.setCenter(vistaLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cargarRecetasGuardadas(ActionEvent actionEvent) {

        seleccionarBoton(btGuardados);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetasGuardadas.fxml"));
            Parent vistaLogin = loader.load();

            RecetasGuardadasController controller = loader.getController();
            controller.setControlador(this);
            controller.setUser(user);

            bpLayaut.setCenter(vistaLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setControllerYCargarMisRecetas(GeneralController generalController) {
        this.generalController = generalController;
        cargarRecetasCreadas();
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
