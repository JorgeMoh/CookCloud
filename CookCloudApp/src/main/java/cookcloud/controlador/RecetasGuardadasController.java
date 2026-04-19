package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

public class RecetasGuardadasController {

    @FXML
    public FlowPane fpRecetas;

    public MisRecetasController misRecetasController;
    Usuario user;

    public void setControlador(MisRecetasController misRecetasController) {
        this.misRecetasController = misRecetasController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}
