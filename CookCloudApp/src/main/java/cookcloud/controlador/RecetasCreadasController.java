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

    /**
     * Setea el usuario y muestra sus recetas creadas
     * @param user usuario de la sesion
     */
    public void setUsuarioYRecetas(Usuario user) {
        this.usuario = user;

        mostrarRecetas();

    }

    /**
     * Metodo que muestra una lista de recetas creadas por el usuario que ha iniciado sesión
     */
    private void mostrarRecetas() {

        // busca en la base de datos las recetas
        recetasCreadas = recipeService.cargarRecetasCreador(usuario.getId_usuario());

        // las muestra en el componente personalizado
        for (Receta receta : recetasCreadas) {

            TarjetaReceta tarjetaReceta = new TarjetaReceta(receta);
            tarjetaReceta.setOnMouseClicked(e -> {
                generalController.cargarVistaReceta(tarjetaReceta.getReceta());
            });

            fpRecetas.getChildren().add(tarjetaReceta);

        }

    }

    public void setControlador(MisRecetasController misRecetasController) {
        this.misRecetasController = misRecetasController;
    }

    public void setControladorBackground(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void cargarVistaFormReceta(ActionEvent actionEvent) {
        generalController.cargarFormReceta();
    }
}
