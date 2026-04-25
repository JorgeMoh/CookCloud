package cookcloud.controlador;

import cookcloud.modelo.Receta;
import cookcloud.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GeneralController {

    @FXML
    public BorderPane background;
    @FXML
    public VBox form;

    Usuario usuario;

    @FXML
    public void initialize() {
        // Cargamos nada más empezar el login
        cargarLogin();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Metodo que carga y enlaza los controllers entre la vista general y el Login
     */
    public void cargarLogin() {

        try {

            // Cargamos la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Login.fxml"));
            Parent vistaLogin = loader.load();

            // Obtenemos el controlador y le pasamos este para conectarlos
            LoginController controller = loader.getController();
            controller.setControladorPrincipal(this);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaLogin);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga y enlaza los controllers entre la vista general y el Register
     */
    public void cargarRegister() {

        try {

            // Cargamos la vista del registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Register.fxml"));
            Parent vistaRegister = loader.load();

            //  Obtenemos el controlador y le pasamos este para conectarlos
            RegisterController controller = loader.getController();
            controller.setControlador(this);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaRegister);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga y enlaza los controllers entre la vista general y el Register
     */
    public void cargarVerification(int nVerificaion, Usuario userRegis) {

        try {

            // Cargamos la vista del registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Verification.fxml"));
            Parent vistaVerification = loader.load();

            //  Obtenemos el controlador y le pasamos este para conectarlos
            RegisterController controller = loader.getController();
            controller.setControlador(this);
            // Le pasamos el codigo de verificacion para no perderlo
            controller.setNumbVerificacion(nVerificaion);
            // Le pasamos el usuario que estábamos intentando registrar para no perderlo
            controller.setUserRegis(userRegis);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaVerification);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la vista de registro pero con el usuario que estábamos creando
     * @param userRegis usuario que estábamos creando
     */
    public void volverRegister(Usuario userRegis) {
        try {

            // Cargamos la vista del registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Register.fxml"));
            Parent vistaRegister = loader.load();

            //  Obtenemos el controlador y le pasamos este para conectarlos
            RegisterController controller = loader.getController();
            controller.setUserRegis(userRegis);
            controller.setCampos();
            controller.setControlador(this);

            // Caragamos el fxml en el VBox
            form.getChildren().clear();
            form.getChildren().setAll(vistaRegister);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo que guarda el usuario de la sesión iniciada y carga el sidebar y la vistas de mis recetas
     * @param user usuario que ha iniciado sesión
     */
    public void iniciarSesion(Usuario user) {

        background.getChildren().clear(); // limpiamos toda la escena

        usuario = user; // guardamos el usuario en la escena para mantenerlo siempre

        try {

            // cargamos el sidebar
            FXMLLoader loaderSidebar = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Sidebar.fxml"));
            Parent vistaSidebar = loaderSidebar.load();

            // cargamos la opcion de mis recetas
            FXMLLoader loaderMisRecetas = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/MisRecetas.fxml"));
            Parent vistaMisRecetas = loaderMisRecetas.load();

            // configuramos y sincronizamos los controladores
            SidebarController controllerSidebar = loaderSidebar.getController();
            controllerSidebar.setGeneralController(this);

            MisRecetasController controllerMisRecetas = loaderMisRecetas.getController();
            controllerMisRecetas.setUser(usuario);
            controllerMisRecetas.setControllerYCargarMisRecetas(this);

            // añadimos las vistas
            background.setLeft(vistaSidebar);
            background.setCenter(vistaMisRecetas);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la vista mis recetas
     */
    public void cargarMisRecetas() {

        try {

            // cargamos la vista
            FXMLLoader loaderMisRecetas = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/MisRecetas.fxml"));
            Parent vistaMisRecetas = loaderMisRecetas.load();

            // la configuramos
            MisRecetasController controllerMisRecetas = loaderMisRecetas.getController();
            controllerMisRecetas.setUser(usuario);
            controllerMisRecetas.setControllerYCargarMisRecetas(this);

            // la añadimos
            background.setCenter(vistaMisRecetas);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la vista del formulario de creación de recetas
     */
    public void cargarFormReceta() {

        try {

            // cargamos la cista
            FXMLLoader loaderFormReceta = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/FormReceta.fxml"));
            Parent vistaFormReceta = loaderFormReceta.load();

            // la configuramos
            FormRecetaController controllerFormReceta = loaderFormReceta.getController();
            controllerFormReceta.setBackgroundController(this);
            controllerFormReceta.setUser(usuario);

            // la añadimos a la vista
            background.setCenter(vistaFormReceta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que se encarga de cargar la vista que muestra una receta ya creada por el usuario
     * @param receta receta que queremos ver
     */
    public void cargarVistaReceta(Receta receta) {

        try {

            // obtenemos la vista
            FXMLLoader loaderReceta = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Receta.fxml"));

            // cargamos la vista
            Parent vistaFormReceta = loaderReceta.load();

            // la configuramos
            RecetaController controllerFormReceta = loaderReceta.getController();
            controllerFormReceta.setBackgroundController(this);
            controllerFormReceta.setReceta(receta);

            // la añadimos a la vista
            background.setCenter(vistaFormReceta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que se encarga de cargar el formulario autorrellenado para editar una receta ya existente
     * @param receta receta que vamos a editar
     */
    public void cargarVistaEditReceta(Receta receta) {

        try {

            // obtenemos la vista
            FXMLLoader loaderFormUpdate = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/FormUpdateReceta.fxml"));

            // cargamos la vista
            Parent vistaFormReceta = loaderFormUpdate.load();

            // la configuramos
            FormUpdateRecetaCotroller controllerFormUpdateReceta = loaderFormUpdate.getController();
            controllerFormUpdateReceta.setBackgroundController(this);
            controllerFormUpdateReceta.setReceta(receta);

            // la añadimos a la vista
            background.setCenter(vistaFormReceta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la vista del explorador de recetas
     */
    public void cargarExplorador() {
        try {

            // cargamos la vista
            FXMLLoader loaderExplorador = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/Explorador.fxml"));
            Parent vista = loaderExplorador.load();

            // la configuramos
            ExploradorController controllerMisRecetas = loaderExplorador.getController();
            controllerMisRecetas.setUser(usuario);
            controllerMisRecetas.setControllerYCargarMisRecetas(this);

            // la añadimos
            background.setCenter(vista);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que limpia la vista principal y borra el usuario para cargar el inicio de sesión de nuevo
     */
    public void cerrarSesion() {

        background.getChildren().clear();
        usuario = null;

        background.setCenter(form);
        cargarLogin();

    }

    /**
     * Metodo que carga la vista de una receta publica de otro usuario diferente al de la sesión
     * @param receta receta que vamos a mostrar
     * @param llamaElExplorador identificador para diferenciar si se llama desde el explorador o desde la sección de recetas guardadas
     */
    public void cargarVistaRecetaPublica(Receta receta, boolean llamaElExplorador) {

        try {

            // obtenemos la vista
            FXMLLoader loaderRecetaPublica = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/RecetaPublica.fxml"));

            // cargamos la vista
            Parent vistaFormReceta = loaderRecetaPublica.load();

            // la configuramos
            RecetaPublicaController controllerRecetaPublica = loaderRecetaPublica.getController();
            controllerRecetaPublica.setBackgroundController(this);
            controllerRecetaPublica.setReceta(receta);
            controllerRecetaPublica.setUser(usuario);
            controllerRecetaPublica.setLlamaElExplrador(llamaElExplorador);

            // la añadimos a la vista
            background.setCenter(vistaFormReceta);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo que carga la sección de mis recetas mostrando la subsección de recetas guardadas
     */
    public void cargarMisRecetasGuardadas() {

        try {

            // cargamos la vista
            FXMLLoader loaderMisRecetas = new FXMLLoader(getClass().getResource("/cookcloud/vista/fxml/MisRecetas.fxml"));
            Parent vistaMisRecetas = loaderMisRecetas.load();

            // la configuramos
            MisRecetasController controllerMisRecetas = loaderMisRecetas.getController();
            controllerMisRecetas.setUser(usuario);
            controllerMisRecetas.setControllerYCargarMisRecetas(this);
            controllerMisRecetas.cargarRecetasGuardadas();

            // la añadimos
            background.setCenter(vistaMisRecetas);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
