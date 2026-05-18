package cookcloud.controlador;

import cookcloud.modelo.Receta;
import cookcloud.modelo.TarjetaReceta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class RecetasGuardadasController {

    @FXML
    public FlowPane fpRecetas;

    private RecetarioController recetarioController;
    private GeneralController generalController;
    private RecipeService recipeService = new RecipeService();

    private Usuario user;

    /**
     * Metodo que muestra las recetas guardadas por el usuario
     */
    private void mostrarRecetasGuardadas() {

        // busca en la base de datos las recetas
        List<Receta> recetasGuardadas = recipeService.cargarRecetasGuardadas(user.getId_usuario());

        // las muestra en el componente personalizado
        for (Receta receta : recetasGuardadas) {

            if (receta.isPublica()){
                TarjetaReceta tarjetaReceta = new TarjetaReceta(receta);
                tarjetaReceta.setOnMouseClicked(e -> {
                    generalController.cargarVistaRecetaPublica(receta,false);
                });
                tarjetaReceta.mostrarCreador();

                fpRecetas.getChildren().add(tarjetaReceta);
            }

        }

    }

    public void setMisRecetasContoller(RecetarioController recetarioController) {
        this.recetarioController = recetarioController;
    }

    public void setGenenralController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
        mostrarRecetasGuardadas();
    }

}
