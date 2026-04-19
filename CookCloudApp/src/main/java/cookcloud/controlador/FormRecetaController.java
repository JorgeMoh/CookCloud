package cookcloud.controlador;

import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import cookcloud.servicios.RecipeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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



        // Establecemos la altura del scrollpane al maximo de la ventana
        vbReceta.minHeightProperty().bind(scroll.heightProperty());

        // Configuramos los estilos de los errores
        errorTitulo.getStyleClass().add("error");
        errorResumen.getStyleClass().add("error");
        errorIngredientes.getStyleClass().add("error");
        errorPasos.getStyleClass().add("error");

        // Añadimos los primeros textfield de la sección de ingredientes a sus arraylists
        ingredientes.add(tfPrimerIngrediente);
        cantidades.add(tfPrimeraCantidad);
    }

    /**
     * Metodo que se encarga de añadir una nueva fila a la sección de ingredientes de una receta
     */
    public void agregarCampIngred() {

        // creo el contenedor del nuevo ingrediente
        HBox hbNuevoIngrediente = new HBox(5);

        // creo el campo del nombre del ingrediente y lo añado a su arraylist
        TextField tfIngrediente = new TextField();
        tfIngrediente.setPromptText("Ingrediente...");
        ingredientes.add(tfIngrediente);

        // creo el campo de cantidad y lo añado a su arraylist
        TextField tfCantidad = new TextField();
        tfCantidad.setPromptText("Cantidad...");
        tfCantidad.setMaxWidth(100);
        cantidades.add(tfCantidad);

        // lo añadimos al contenedor junto al botón de añadir ingrediente
        hbNuevoIngrediente.getChildren().addAll(tfIngrediente, tfCantidad, btAgregar);

        // añadimos el contenedor nuevo al contenedor general de ingredientes
        vbIngredientes.getChildren().add(hbNuevoIngrediente);

    }

    /**
     * Metodo que crea la receta y la sube a la base de datos
     */
    public void crearReceta() {

        // Comprobamos que los campos esten rellenados correctamente
        if (comprobarCampos()){

            // Guardamos los datos de los campos
            String titulo = tfTitulo.getText();
            String resumen = taResumen.getText();
            ArrayList<Ingrediente> listaIngredientes = cargarIngredientes(); // guarda los que tienen al menos el primer campo relleno
            String pasos = tfPrimeraCantidad.getText();
            boolean publica = tbPublica.isSelected();

            // creamos la receta
            Receta nuevaReceta = new Receta(titulo,resumen,pasos,publica,user);

            // Añadimos los ingredientes a la receta
            for(Ingrediente ingrediente: listaIngredientes){
                nuevaReceta.addIngrediente(ingrediente);
            }

            // subimos la receta
            recipeService.subirReceta(nuevaReceta);

            // Volvemos a la vista de mis recetas
            generalController.cargarMisRecetas();

        }

    }

    /**
     * Metodo que crea un arraylist con los ingredientes rellenados
     * @return ArrayList de ingredientes
     */
    private ArrayList<Ingrediente> cargarIngredientes() {

        ArrayList<Ingrediente> listaIngredientes = new ArrayList<>();

        for (int i = 0; i < ingredientes.size(); i++) {

            // Si el nombre del ingrediente esta relleno lo crea y lo añade al arraylist
            if (!ingredientes.get(i).getText().trim().isEmpty()){

                String cantidad;

                if (cantidades.get(i).getText().trim().isEmpty()) cantidad = "";
                else cantidad = cantidades.get(i).getText();

                listaIngredientes.add(new Ingrediente(ingredientes.get(i).getText(), cantidad));

            }

        }

        // devuelve el arraylist con ingredientes
        return listaIngredientes;

    }

    /**
     * Metodo que comprueba si los campos necesarios están rellenados correctamente
     * @return true si lo están, false si no lo están
     */
    private boolean comprobarCampos() {

        boolean correcto = true;

        // comprueba que el titulo no este vacío
        if(tfTitulo.getText().trim().isEmpty()){

            errorTitulo.setText("Titulo vacío");

            vbTitulo.getChildren().remove(errorTitulo);
            vbTitulo.getChildren().add(errorTitulo);

            correcto = false;

        }else vbTitulo.getChildren().remove(errorTitulo);

        // comprueba que el resumen no este vacio
        if(taResumen.getText().trim().isEmpty()){

            errorResumen.setText("Resumen vacío");

            vbResumen.getChildren().remove(errorResumen);
            vbResumen.getChildren().add(errorResumen);

            correcto = false;

        }else vbResumen.getChildren().remove(errorResumen);

        // Comprueba que haya al menos 1 ingrediente bien relleno
        if (!hayIngredienteCompleto()){

            errorIngredientes.setText("No hay ingredientes completos");

            vbIngredientes.getChildren().remove(errorIngredientes);
            vbIngredientes.getChildren().add(errorIngredientes);
            correcto = false;

        }else vbIngredientes.getChildren().remove(errorIngredientes);

        // Comprueba que los pasos no estén vacíos
        if (taPasos.getText().trim().isEmpty()){

            errorPasos.setText("Pasos vacíos");

            vbPasos.getChildren().remove(errorPasos);
            vbPasos.getChildren().add(errorPasos);

            correcto = false;

        } else vbPasos.getChildren().remove(errorPasos);


        return correcto;

    }

    /**
     * Metodo que comprueba que al menos haya un ingrediente bien introducido
     * @return true si lo hay, false si no
     */
    private boolean hayIngredienteCompleto() {

        boolean correcto = false;

        for (int i = 0; i < ingredientes.size(); i++) {

            if (!ingredientes.get(i).getText().trim().isEmpty()) {

                correcto = true;
                break;

            }
        }

        return correcto;

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
     * Carga la vista de mis recetas
     */
    public void volver() {
        generalController.cargarMisRecetas();
    }

    public void setBackgroundController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
