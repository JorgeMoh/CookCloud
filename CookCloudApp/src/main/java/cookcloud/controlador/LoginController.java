package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import cookcloud.servicios.UserService;
import cookcloud.utils.UtilsPass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LoginController {

    @FXML
    public VBox vbUser;
    @FXML
    public VBox vbPass;
    @FXML
    public TextField tfUser;
    @FXML
    public PasswordField tfPass;
    @FXML
    public VBox vbErrorGen;

    private Label errUser = new Label();
    private Label errPass = new Label();

    private GeneralController generalController;
    private UserService userService = new UserService();

    @FXML
    public void initialize() {

        // Configuaramos la clase de estilo de los mensajes de errores
        errUser.getStyleClass().add("error");
        errPass.getStyleClass().add("error");

    }

    /**
     * Sincroniza el controlador principal con este
     * @param principalController variable del controlador principal
     */
    public void setControladorPrincipal(GeneralController principalController) {
        this.generalController = principalController;
    }

    /**
     * Metodo que cambia el menu de login al presionar un label
     * @param mouseEvent
     */
    public void irARegistro(MouseEvent mouseEvent) {
        generalController.cargarRegister();
    }

    /**
     * Metodo que inicia sesion si los datos son correctos
     * @param actionEvent
     */
    public void iniciarSesion(ActionEvent actionEvent) {

        // Comprobamos si los campos están vacíos
        if (comprobarCampos()){

            errUser.setText("Usuario o contraseña incorrectars");

            // En caso de que estén rellenos comprobamos si hay un usuario con ese nombre de usuario
            if (userService.comprobarNombreUser(tfUser.getText())){

                vbErrorGen.getChildren().remove(errUser);

                // Buscamos al usuario y lo guardamos
                Usuario user = userService.buscarUsuarioPorNombre(tfUser.getText());

                // Si la contraseña coincide se inicia sesión
                if (UtilsPass.verificarPass(tfPass.getText(),user.getPassw())){

                    vbErrorGen.getChildren().remove(errUser);

                    generalController.iniciarSesion(user);

                // En caso de que no coincidan
                }else{

                    vbErrorGen.getChildren().remove(errUser);
                    vbErrorGen.getChildren().add(errUser);

                }

            // En caso de que no existan se muestra mensaje de error
            }else {

                vbErrorGen.getChildren().remove(errUser);
                vbErrorGen.getChildren().add(errUser);

            }

        }

    }

    /**
     * Metodo que comprueba el contenido de los textfield
     * @return devuelve un true si los campos están correctamente rellenados o un false si no lo están
     */
    private boolean comprobarCampos() {

        boolean rellenados = true;

        // Si el campo de usuario esta vacío se muestra mensaje de error
        if (tfUser.getText().trim().isEmpty()) {

            errUser.setText("Usuario vacio");
            vbUser.getChildren().remove(errUser);
            vbUser.getChildren().add(errUser);
            rellenados = false;

        // Si no está vacío se elimina el mensaje de error
        } else vbUser.getChildren().remove(errUser);

        // Si la contraseña esta vacía muestra un mensaje de error
        if (tfPass.getText().trim().isEmpty()) {

            errPass.setText("Contraseña vacia");
            vbPass.getChildren().remove(errPass);
            vbPass.getChildren().add(errPass);
            rellenados = false;

        // Si está bien elimina el mensaje
        } else vbPass.getChildren().remove(errPass);

        return rellenados;

    }

    /**
     * Metodo que cambia la vista a la de recuperación de contraseña en el login
     */
    public void irARecuperacion() {
        generalController.cargarRecoverPass();
    }
}
