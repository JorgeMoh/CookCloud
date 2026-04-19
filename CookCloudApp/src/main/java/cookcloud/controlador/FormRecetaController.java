package cookcloud.controlador;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FormRecetaController {

    @FXML
    public VBox vbReceta;
    @FXML
    public ScrollPane scroll;

    @FXML
    public VBox vbTitulo;
    @FXML
    public TextField tfTitulo;

    @FXML
    public ToggleButton tbPublica;

    @FXML
    public VBox vbResumen;
    @FXML
    public TextArea taResumen;

    @FXML
    public VBox vbIngredientes;
    @FXML
    public TextField tfPrimerIngrediente;
    @FXML
    public TextField tfPrimeraCantidad;
    @FXML
    public Button btAgregar;

    @FXML
    public VBox vbPasos;
    @FXML
    public TextArea taPasos;


    private Label errorTitulo = new Label();
    private Label errorResumen = new Label();
    private Label errorIngredientes = new Label();
    private Label errorPasos = new Label();

    private GeneralController generalController;
    private Usuario user;

    private ArrayList<TextField> ingredientes = new ArrayList<>();
    private ArrayList<TextField> cantidades = new ArrayList<>();

    private RecipeService recipeService = new RecipeService();

    @FXML
    public void initialize() {

        vbReceta.minHeightProperty().bind(scroll.heightProperty());

        errorTitulo.getStyleClass().add("error");
        errorResumen.getStyleClass().add("error");
        errorIngredientes.getStyleClass().add("error");
        errorPasos.getStyleClass().add("error");

        ingredientes.add(tfPrimerIngrediente);
        cantidades.add(tfPrimeraCantidad);
    }

    public void agregarCampIngred(ActionEvent actionEvent) {

        HBox hbNuevoIngrediente = new HBox(5);

        TextField tfIngrediente = new TextField();
        tfIngrediente.setPromptText("Ingrediente...");
        ingredientes.add(tfIngrediente);

        TextField tfCantidad = new TextField();
        tfCantidad.setPromptText("Cantidad...");
        tfCantidad.setMaxWidth(100);
        cantidades.add(tfCantidad);

        hbNuevoIngrediente.getChildren().addAll(tfIngrediente, tfCantidad, btAgregar);

        vbIngredientes.getChildren().add(hbNuevoIngrediente);

    }

    public void crearReceta(ActionEvent actionEvent) {

        if (comprobarCampos()){

            String titulo = tfTitulo.getText();
            String resumen = taResumen.getText();
            ArrayList<Ingrediente> listaIngredientes = cargarIngredientes();
            String pasos = tfPrimeraCantidad.getText();
            //Si esta presionado es publico si no es privado
            boolean publica = tbPublica.isSelected();

            Receta nuevaReceta = new Receta(titulo,resumen,pasos,publica,user);

            for(Ingrediente ingrediente: listaIngredientes){
                nuevaReceta.addIngrediente(ingrediente);
            }

            recipeService.subirReceta(nuevaReceta);

//            user.getRecetas().add(nuevaReceta);

            generalController.cargarMisRecetas();

        }

    }

    private ArrayList<Ingrediente> cargarIngredientes() {

        ArrayList<Ingrediente> listaIngredientes = new ArrayList<>();

        for (int i = 0; i < ingredientes.size(); i++) {

            if (!ingredientes.get(i).getText().trim().isEmpty()){

                String cantidad;

                if (cantidades.get(i).getText().trim().isEmpty()) cantidad = "";
                else cantidad = cantidades.get(i).getText();

                listaIngredientes.add(new Ingrediente(ingredientes.get(i).getText(), cantidad));

            }

        }

        return listaIngredientes;

    }

    private boolean comprobarCampos() {

        boolean correcto = true;

        if(tfTitulo.getText().trim().isEmpty()){

            errorTitulo.setText("Titulo vacío");

            vbTitulo.getChildren().remove(errorTitulo);
            vbTitulo.getChildren().add(errorTitulo);

            correcto = false;

        }else vbTitulo.getChildren().remove(errorTitulo);

        if(taResumen.getText().trim().isEmpty()){

            errorResumen.setText("Resumen vacío");

            vbResumen.getChildren().remove(errorResumen);
            vbResumen.getChildren().add(errorResumen);

            correcto = false;

        }else vbResumen.getChildren().remove(errorResumen);


        if (!hayIngredienteCompleto()){

            errorIngredientes.setText("No hay ingredientes completos");

            vbIngredientes.getChildren().remove(errorIngredientes);
            vbIngredientes.getChildren().add(errorIngredientes);
            correcto = false;

        }else vbIngredientes.getChildren().remove(errorIngredientes);

        if (taPasos.getText().trim().isEmpty()){

            errorPasos.setText("Pasos vacíos");

            vbPasos.getChildren().remove(errorPasos);
            vbPasos.getChildren().add(errorPasos);

            correcto = false;

        } else vbPasos.getChildren().remove(errorPasos);


        return correcto;

    }

    private boolean hayIngredienteCompleto() {

        boolean correcto = false;

        for (int i = 0; i < ingredientes.size(); i++) {

            if (!ingredientes.get(i).getText().trim().isEmpty()) {

                correcto = true;

            }
        }

        return correcto;

    }

    public void setBackgroundController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void volver(ActionEvent actionEvent) {
        generalController.cargarMisRecetas();
    }


}
