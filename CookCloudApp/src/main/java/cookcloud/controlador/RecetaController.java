package cookcloud.controlador;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.servicios.IngredientService;
import cookcloud.servicios.RecipeService;
import cookcloud.utils.UtilsPDF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    @FXML
    public void initialize() {

        tbPublica.setDisable(true); // deshabilitamos el botón para que no cambie

        // Establecemos la altura del scrollpane al máximo de la ventana
        vbReceta.minHeightProperty().bind(scroll.heightProperty());

    }

    /**
     * Metodo que elimina la receta de la base de datos después de confirmar que quieres borrarla
     */
    public void eliminarReceta() {

        // Configuramos la alerta de confirmación
        Alert confirmarElim = new Alert(Alert.AlertType.CONFIRMATION);
        confirmarElim.setTitle("Confirmación");
        confirmarElim.setHeaderText(null);
        confirmarElim.setContentText("¿Estas seguro que quieres ELIMINAR esta receta?");
        Stage alertStage = (Stage) confirmarElim.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));

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

            Label cantidadIngrediente = new Label(" - " + ingrediente.getCantidad());
            cantidadIngrediente.getStyleClass().add("ingredientes");

            Label nombreIngrediente = new Label(ingrediente.getNombre());
            nombreIngrediente.getStyleClass().add("ingredientes");

            hbIngrediente.getChildren().addAll(cantidadIngrediente,nombreIngrediente);

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

    /**
     * Metodo que cambia la vista la de mis recetas
     */
    public void volver() {
        generalController.cargarMisRecetas();
    }

    /**
     * Metodo que pide la ruta donde queramos guardar la receta y la guarda en formato pdf
     */
    public void exportarPDF() {

        // Cargamos los ingredientes
        List<Ingrediente> ingredientes = ingredientService.listarRecetas(receta.getId_receta());

        // obtenemos el stage y guardamos la ruta
        Stage stage = generalController.getStage();
        File ruta = pedirRuta(stage);

        // Si el usuario ha seleccionado una ruta crea el archivo
        if (ruta != null) {
            System.out.println(ruta);
            UtilsPDF.exportarReceta(receta, ingredientes, ruta.getAbsolutePath());
        }

    }

    /**
     * Metodo que carga la ventana del selector de archivos
     * @param stage stage al que está vinculado el diálogo
     * @return file con la ruta que hemos seleccionado
     */
    private File pedirRuta(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar receta");

        // Sugerimos el nombre por defecto
        fileChooser.setInitialFileName(receta.getTitulo()+".pdf");

        // mostramos solo los archivos PDF
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));

        // Abrimos diálogo
        return fileChooser.showSaveDialog(stage);
    }

}
