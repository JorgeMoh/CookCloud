package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.layout.FlowPane;

public class MisRecetasController {

    public FlowPane fpRecetas;

    private PrincipalController principalController;
    private Usuario user;

    public void cargarMisRecetas(ActionEvent actionEvent) {
    }

    public void cargarRecetasGuardadas(ActionEvent actionEvent) {
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
