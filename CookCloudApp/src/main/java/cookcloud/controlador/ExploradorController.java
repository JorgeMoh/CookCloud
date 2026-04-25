package cookcloud.controlador;

import cookcloud.modelo.Receta;
import cookcloud.modelo.TarjetaReceta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class ExploradorController {


    @FXML
    public FlowPane fpRecetas;

    private Usuario usuario;
    private GeneralController generalController;
    private RecipeService recipeService = new RecipeService();


    public void setUser(Usuario usuario) {
        this.usuario = usuario;
        mostrarRecetas();
    }

    public void setControllerYCargarMisRecetas(GeneralController generalController) {
        this.generalController = generalController;
    }

    /**
     * Metodo que carga en la vista las recetas públicas que no sean las del usuario que ha iniciado sesión
     */
    private void mostrarRecetas() {

        // busca en la base de datos las recetas
        List<Receta> recetasCreadas = recipeService.cargarRecetasPublicas(usuario.getId_usuario());

        // las muestra en el componente personalizado
        for (Receta receta : recetasCreadas) {

            TarjetaReceta tarjetaReceta = new TarjetaReceta(receta);
            tarjetaReceta.setOnMouseClicked(e -> {
                generalController.cargarVistaRecetaPublica(tarjetaReceta.getReceta(), true);
            });
            tarjetaReceta.mostrarCreador();

            fpRecetas.getChildren().add(tarjetaReceta);

        }

    }
}
