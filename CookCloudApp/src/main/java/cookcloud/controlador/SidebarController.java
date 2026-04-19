package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class SidebarController {

    public Button btMisrecetas;
    public Button btBuscar;
    public Button btAjustes;
    private GeneralController principalController;

    public void mostrarMisRecetas() {
        seleccionarBoton(btMisrecetas);
        principalController.cargarMisRecetas();
    }

    public void mostrarBuscar(ActionEvent actionEvent) {
        seleccionarBoton(btBuscar);
    }

    public void mostrarAjustes(ActionEvent actionEvent) {
        seleccionarBoton(btAjustes);
    }

    public void cerrarSesion(ActionEvent actionEvent) {
    }

    public void setPrincipalController(GeneralController principalController) {
        this.principalController = principalController;
    }

    private void seleccionarBoton(Button activo) {

        btMisrecetas.getStyleClass().remove("btSelect");
        btBuscar.getStyleClass().remove("btSelect");
        btAjustes.getStyleClass().remove("btSelect");

        activo.getStyleClass().add("btSelect");
    }
}
