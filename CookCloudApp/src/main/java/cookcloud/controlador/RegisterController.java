package cookcloud.controlador;

import cookcloud.servicios.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterController {

    @FXML
    public VBox vbUser;
    @FXML
    public VBox vbEmail;
    @FXML
    public VBox vbPass;
    @FXML
    public TextField tfUser;
    @FXML
    public TextField tfEmail;
    @FXML
    public PasswordField tfPass;

    private Label errUser = new Label();
    private Label errEmail = new Label();
    private Label errPass = new Label();

    private PrincipalController principalController;
    private UserService userService = new UserService();

    @FXML
    public void initialize() {

        // Configuaramos la clase de estilo de los mensajes de errores
        errUser.getStyleClass().add("error");
        errEmail.getStyleClass().add("error");
        errPass.getStyleClass().add("error");

    }

    /**
     * Sincroniza el controlador principal con este
     * @param principalController variable del controlador principal
     */
    public void setControlador(PrincipalController principalController) {
        this.principalController = principalController;
    }

    /**
     * Metodo que cambia el menu de Register
     */
    @FXML
    public void irALogin() {
        principalController.cargarLogin();
    }

    /**
     * Metodo que registra a un usuario en caso de que todos los campos esten correctamente rellenados y no exista
     * en la base de datos
     * @param actionEvent
     */
    public void registrarUsuario(ActionEvent actionEvent) {

        // Comprobamos que los campos están correctamente rellenados
        if (comprobarCampos()) {
//            userService.registrarUsuario(tfUser.getText(),tfEmail.getText(), tfPass.getText());
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

        // Si el campo de correo esta vacio se muestra mensaje de error
        if (tfEmail.getText().trim().isEmpty()) {

            errEmail.setText("Email vacio");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
            rellenados = false;

        // En caso de que el correo electrónico no este bien construido muestra mensaje de error
        } else if (!tfEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            errEmail.setText("Email no valido. EJEMPLO: nombreCorreo@dominio.com");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
            rellenados = false;

        // Si está correctamente rellenado se elimina el mensaje de error
        } else vbEmail.getChildren().remove(errEmail);

        // Si la contraseña esta vacía muestra un mensaje de error
        if (tfPass.getText().trim().isEmpty()) {

            errPass.setText("Contraseña vacia");
            vbPass.getChildren().remove(errPass);
            vbPass.getChildren().add(errPass);
            rellenados = false;

        // Muestra un mensaje de error en caso de que la contraseña no coincida con el patron: 8 caracteres, 1 mayúscula y 1 número
        } else if (!tfPass.getText().matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {

            errPass.setText("Contraseña debe tener 8 caracteres(1 mayúscula y 1 numero)");
            vbPass.getChildren().remove(errPass);
            vbPass.getChildren().add(errPass);
            rellenados = false;

        // Si todo esta bien elimina el mensaje
        } else vbPass.getChildren().remove(errPass);

        return rellenados;

    }
}
