package cookcloud.controlador;

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

    private Label errUser = new Label();
    private Label errPass = new Label();

    private PrincipalController principalController;

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
    public void setControladorPrincipal(PrincipalController principalController) {
        this.principalController = principalController;
    }

    /**
     * Metodo que cambia el menu de login al presionar un label
     * @param mouseEvent
     */
    public void irARegistro(MouseEvent mouseEvent) {
        principalController.cargarRegister();
    }

    /**
     * Metodo que inicia sesion si los datos son correctos
     * @param actionEvent
     */
    public void iniciarSesion(ActionEvent actionEvent) {

        if (comprobarCampos()){

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

        // Si todo esta bien elimina el mensaje
        } else vbPass.getChildren().remove(errPass);

        return rellenados;

    }
}
