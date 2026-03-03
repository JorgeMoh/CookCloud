package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.event.ActionEvent;

public class SidebarController {

    private PrincipalController principalController;
    private Usuario user;

    public void mostrarMisRecetas(ActionEvent actionEvent) {
    }

    public void mostrarBuscar(ActionEvent actionEvent) {
    }

    public void mostrarAjustes(ActionEvent actionEvent) {
    }

    public void cerrarSesion(ActionEvent actionEvent) {
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
