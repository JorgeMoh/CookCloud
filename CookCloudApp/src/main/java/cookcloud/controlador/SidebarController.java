package cookcloud.controlador;

import javafx.scene.control.Button;

public class SidebarController {

    public Button btMisrecetas;
    public Button btBuscar;
    public Button btAjustes;
    private GeneralController generalController;

    /**
     * Metodo que muestra la vista de mis recetas
     */
    public void mostrarMisRecetas() {
        seleccionarBoton(btMisrecetas); // selecciona el botón
        generalController.cargarMisRecetas(); // cargamos la vista
    }

    /**
     * Metodo que carga la vista del explorador de recetas
     */
    public void mostrarExplorador() {
        seleccionarBoton(btBuscar); // seleccionamos el botón
        generalController.cargarExplorador();
    }

    /**
     * Metodo que carga la vista de ajustes
     */
    public void mostrarAjustes() {
        seleccionarBoton(btAjustes); // seleccionamos el botón
        generalController.cargarAjustes();
    }

    /**
     * Metodo que limpia la app cerrando sesión
     */
    public void cerrarSesion() {
        generalController.cerrarSesion();
    }

    /**
     * Metodo que gestiona la apariencia de los botones para que el presionado sea diferente a los demás
     * @param activo botón que pasa a estar activo
     */
    private void seleccionarBoton(Button activo) {

        // quitamos el estiolo de todos los botones
        btMisrecetas.getStyleClass().remove("btSelect");
        btBuscar.getStyleClass().remove("btSelect");
        btAjustes.getStyleClass().remove("btSelect");

        activo.getStyleClass().add("btSelect"); // establecemos el estilo al botón que acabamos de presionar
    }

    public void setGeneralController(GeneralController generalController) {
        this.generalController = generalController;
    }
}
