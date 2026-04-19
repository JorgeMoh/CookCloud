package cookcloud.controlador;

import cookcloud.modelo.Receta;
import cookcloud.modelo.TarjetaReceta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class RecetasCreadasController {

    @FXML
    public FlowPane fpRecetas;

    private MisRecetasController misRecetasController;
    private GeneralController generalController;

    private RecipeService recipeService = new RecipeService();

    private List<Receta> recetasCreadas = new ArrayList<>();
    private Usuario usuario;

    public void setControlador(MisRecetasController misRecetasController) {
        this.misRecetasController = misRecetasController;
    }

    public void setControladorBackground(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void cargarVistaFormReceta(ActionEvent actionEvent) {
        generalController.cargarFormReceta();
    }

    public void setUsuario(Usuario user) {
        this.usuario = user;

        mostrarRecetas();

    }

    private void mostrarRecetas() {

        recetasCreadas = recipeService.cargarRecetasCreador(usuario.getId_usuario());

        for (Receta receta : recetasCreadas) {

            fpRecetas.getChildren().add(new TarjetaReceta(receta));

        }

    }
}
