package cookcloud.controlador;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.IngredientService;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class RecetaPublicaController {
    @FXML
    public ScrollPane scroll;
    @FXML
    public VBox vbReceta;

    @FXML
    public Label lbTitulo;
    @FXML
    public Label lbResumen;
    @FXML
    public VBox vbIngredientes;
    @FXML
    public Label lbPasos;
    @FXML
    public HBox hbBotones;
    @FXML
    public Button btGuardar;

    private Button btQuitarGuardado = new Button("Quitar de Guardado");

    private IngredientService ingredientService = new IngredientService();
    private RecipeService recipeService = new RecipeService();

    private GeneralController generalController;
    private Receta receta;
    private Usuario usuario;
    private boolean llamaElExplorador;
    private Alert confirmarElim = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    public void initialize() {

        btQuitarGuardado.getStyleClass().add("btEliminar");
        btQuitarGuardado.setOnAction((e) -> {
            borrarRecetaDeGuardados();
        });

        // Configuramos la alerta de confirmación
        confirmarElim.setTitle("Confirmación");
        confirmarElim.setHeaderText(null);
        confirmarElim.setContentText("¿Estas seguro que quieres ELIMINAR esta receta?");
        Stage alertStage = (Stage) confirmarElim.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));

        // Establecemos la altura del scrollpane al máximo de la ventana
        vbReceta.minHeightProperty().bind(scroll.heightProperty());

    }

    /**
     * Metodo que quita la receta de guardados
     */
    private void borrarRecetaDeGuardados() {
        recipeService.quitarDeGuardadas(receta.getId_receta(),usuario.getId_usuario());
        mostrarBoton();
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

            Label nombreIngrediente = new Label(" - " + ingrediente.getNombre());
            nombreIngrediente.getStyleClass().add("ingredientes");
            Label cantidadIngrediente = new Label(ingrediente.getCantidad());
            cantidadIngrediente.getStyleClass().add("ingredientes");

            hbIngrediente.getChildren().addAll(nombreIngrediente, cantidadIngrediente);

            vbIngredientes.getChildren().add(hbIngrediente);

        }

    }

    /**
     * Metodo que vuelve a lavista anterior desde la que se llamó a esta vista
     */
    public void volver() {
        if (llamaElExplorador) {
            generalController.cargarExplorador();
        } else generalController.cargarMisRecetasGuardadas();
    }

    /**
     * Metodo que guarda una receta
     */
    public void guardarReceta() {
        recipeService.guardarReceta(receta,usuario.getId_usuario());
        mostrarBoton();
    }

    /**
     * Metodo que cambia el botón de la vista de receta dependiendo de si ya se ha guardado o no
     */
    private void mostrarBoton() {

        hbBotones.getChildren().clear();

        if (recipeService.recetaYaGuardada(usuario.getId_usuario(),receta.getId_receta())) {
            hbBotones.getChildren().add(btQuitarGuardado);
        } else  {
            hbBotones.getChildren().add(btGuardar);
        }

    }

    public void setUser(Usuario usuario) {
        this.usuario = usuario;
        mostrarBoton();
    }

    public void setLlamaElExplrador(boolean llamaElExplorador) {
        this.llamaElExplorador = llamaElExplorador;
    }

}
