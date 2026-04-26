package cookcloud.controlador;

import cookcloud.modelo.Receta;
import cookcloud.modelo.TarjetaReceta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.regex.Pattern;

public class ExploradorController {


    @FXML
    public FlowPane fpRecetas;
    @FXML
    public TextField tfBuscador;

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

        // busca en la base de datos las recetas publicas
        List<Receta> recetasPublicas = recipeService.cargarRecetasPublicas(usuario.getId_usuario());

        // las muestra en el componente personalizado
        for (Receta receta : recetasPublicas) {

            TarjetaReceta tarjetaReceta = new TarjetaReceta(receta);
            tarjetaReceta.setOnMouseClicked(e -> {
                generalController.cargarVistaRecetaPublica(tarjetaReceta.getReceta(), true);
            });
            tarjetaReceta.mostrarCreador();

            fpRecetas.getChildren().add(tarjetaReceta);

        }

    }

    /**
     * Metodo que busca las recetas que concuerdan con lo que escribe el usuario
     */
    public void buscar() {

        // mientras no este vacío buscara
        if(!tfBuscador.getText().trim().isEmpty()){

            fpRecetas.getChildren().clear();

            // busca en la base de datos las recetas publicas
            List<Receta> recetasPublicas = recipeService.cargarRecetasPublicas(usuario.getId_usuario());

            Pattern patron = Pattern.compile(tfBuscador.getText(), Pattern.CASE_INSENSITIVE);

            // las muestra en el componente personalizado que c
            for (Receta receta : recetasPublicas) {

                // cargamos las recetas que el patron escrito por el usuario
                if(patron.matcher(receta.getTitulo()).find()){

                    TarjetaReceta tarjetaReceta = new TarjetaReceta(receta);
                    tarjetaReceta.setOnMouseClicked(e -> {
                        generalController.cargarVistaRecetaPublica(tarjetaReceta.getReceta(), true);
                    });
                    tarjetaReceta.mostrarCreador();

                    fpRecetas.getChildren().add(tarjetaReceta);

                }

            }

        }

    }

    /**
     * Metodo que limpia el buscador, las recetas mostradas y recarga todas las recetas
     */
    public void recargar() {

        tfBuscador.clear();
        fpRecetas.getChildren().clear();
        mostrarRecetas();

    }
}
