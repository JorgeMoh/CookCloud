package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class SidebarController {

    public Button btMisrecetas;
    public Button btBuscar;
    public Button btAjustes;
    private BackgroundController principalController;
    private Usuario user;

    public void mostrarMisRecetas() {
        seleccionarBoton(btMisrecetas);
        principalController.cargarMisRecetas(user);
    }

    public void mostrarBuscar(ActionEvent actionEvent) {
        seleccionarBoton(btBuscar);
    }

    public void mostrarAjustes(ActionEvent actionEvent) {
        seleccionarBoton(btAjustes);
    }

    public void cerrarSesion(ActionEvent actionEvent) {
    }

    public void setPrincipalController(BackgroundController principalController) {
        this.principalController = principalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    private void seleccionarBoton(Button activo) {

        btMisrecetas.getStyleClass().remove("btSelect");
        btBuscar.getStyleClass().remove("btSelect");
        btAjustes.getStyleClass().remove("btSelect");

        activo.getStyleClass().add("btSelect");
    }
}
