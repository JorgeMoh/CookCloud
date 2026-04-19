package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MisRecetasController {

    @FXML
    public BorderPane bpLayaut;
    @FXML
    public Button btMisRecetas;
    @FXML
    public Button btGuardados;

    private GeneralController generalController;
    private Usuario user;

    /**
     * Metodo que carga la vista con las recetas creadas por el usuario
     */
    public void cargarRecetasCreadas() {

        seleccionarBoton(btMisRecetas); // marcamos el boton como seleccionado

        try {

            // cargamos la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetasCreadas.fxml"));
            Parent vistaLogin = loader.load();

            // la configuramos
            RecetasCreadasController controller = loader.getController();
            controller.setControlador(this);
            controller.setControladorBackground(generalController);
            controller.setUsuarioYRecetas(user);

            // mostramos la vista
            bpLayaut.setCenter(vistaLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la vista que muestra las recetas guardadas por el usuario
     */
    public void cargarRecetasGuardadas() {

        seleccionarBoton(btGuardados); // establece el boton seleccionado

        try {

            // cargamos la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetasGuardadas.fxml"));
            Parent vistaLogin = loader.load();

            // la configuramos
            RecetasGuardadasController controller = loader.getController();
            controller.setControlador(this);
            controller.setUser(user);

            // la mostramos
            bpLayaut.setCenter(vistaLogin);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Setea el controlador para sincronizarlo y llama al metodo para mostrar las recetas creadas por el usuario
     * @param generalController controlador de la vista general/principal
     */
    public void setControllerYCargarMisRecetas(GeneralController generalController) {
        this.generalController = generalController;
        cargarRecetasCreadas();
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * Metodo que marca el botón presionado para diferenciarlo de los demás con una clase de estilos
     * @param activo botón que pasa a estar activo
     */
    private void seleccionarBoton(Button activo) {

        // desmarcamos los botones
        btMisRecetas.getStyleClass().remove("btSelect");
        btGuardados.getStyleClass().remove("btSelect");

        // marcamos el que hemos presionado
        activo.getStyleClass().add("btSelect");
    }
}
