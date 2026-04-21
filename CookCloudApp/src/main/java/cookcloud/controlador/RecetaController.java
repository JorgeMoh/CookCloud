package cookcloud.controlador;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.servicios.IngredientService;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecetaController {

    @FXML
    public ScrollPane scroll;
    @FXML
    public VBox vbReceta;

    @FXML
    public Label lbTitulo;
    @FXML
    public ToggleButton tbPublica;
    @FXML
    public Label lbResumen;
    @FXML
    public VBox vbIngredientes;
    @FXML
    public Label lbPasos;

    IngredientService ingredientService = new IngredientService();
    RecipeService recipeService = new RecipeService();

    GeneralController generalController;
    Receta receta;
    Alert confirmarElim = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    public void initialize() {

        // Configuramos la alerta de confirmación
        confirmarElim.setTitle("Confirmación");
        confirmarElim.setHeaderText(null);
        confirmarElim.setContentText("¿Estas seguro que quieres ELIMINAR esta receta?");
        Stage alertStage = (Stage) confirmarElim.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));

        tbPublica.setDisable(true); // deshabilitamos el botón para que no cambie

        // Establecemos la altura del scrollpane al máximo de la ventana
        vbReceta.minHeightProperty().bind(scroll.heightProperty());

    }

    /**
     * Metodo que elimina la receta de la base de datos después de confirmar que quieres borrarla
     */
    public void eliminarReceta() {

        // lanzamos el alert y guardamos el boton que ha presionado el usuario
        Optional<ButtonType> resultado = confirmarElim.showAndWait();

        // si el boton es el de aceptar  se elimina la receta y volvemos a la vista d mis recetas
        if (resultado.get() == ButtonType.OK) {

            recipeService.eliminarReceta(receta.getId_receta());

            volver();
        }

    }

    /**
     * Metodo que llama al metodo que carga el formulario de edicion de recetas
     */
    public void editarReceta() {
        generalController.cargarVistaEditReceta(receta);
    }

    public void setBackgroundController(GeneralController generalController) {
        this.generalController = generalController;
    }

    /**
     * Metodo que setea la receta que se va a mostrar y llama al metod que rellena los labels
     * @param receta
     */
    public void setReceta(Receta receta) {
        this.receta = receta;

        rellenarReceta();

    }

    /**
     * Metodo que rellena los labels
     */
    private void rellenarReceta() {

        lbTitulo.setText(receta.getTitulo()); // seteamos el título
        tbPublica.setSelected(receta.isPublica()); // seteamos el estado de la receta
        cambiarEstado();
        lbResumen.setText(receta.getResumen()); // seteamos el resumen
        lbResumen.getStyleClass().add("resumen");
        cargarIngredientes();
        lbPasos.setText(receta.getPasos()); // seteamos el resumen

    }

    /**
     * Metodo que muestra los ingredientes
     */
    private void cargarIngredientes() {

        List<Ingrediente> ingredientes = ingredientService.listarRecetas(receta.getId_receta());

        for (Ingrediente ingrediente : ingredientes) {

            HBox hbIngrediente = new HBox(5);

            Label nombreIngrediente = new Label(" - " + ingrediente.getNombre());
            nombreIngrediente.getStyleClass().add("ingredientes");
            Label cantidadIngrediente = new Label(ingrediente.getCantidad());
            cantidadIngrediente.getStyleClass().add("ingredientes");

            hbIngrediente.getChildren().addAll(nombreIngrediente, cantidadIngrediente);

            vbIngredientes.getChildren().add(hbIngrediente);

        }

    }

    /**
     * Cambia el texto del togglebutton dependiendo de su estado
     */
    public void cambiarEstado() {

        if(tbPublica.isSelected()){
            tbPublica.setText("Publica");
        } else tbPublica.setText("Privada");

    }

    public void volver() {
        generalController.cargarMisRecetas();
    }
}
