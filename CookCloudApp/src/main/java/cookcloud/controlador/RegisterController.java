package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import cookcloud.servicios.EmailService;
import cookcloud.servicios.UserService;
import cookcloud.utils.UtilsPass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Random;

public class RegisterController {

    @FXML
    public VBox vbUser;
    @FXML
    public VBox vbEmail;
    @FXML
    public VBox vbPass;
    @FXML
    public VBox vbPass2;
    @FXML
    public TextField tfUser;
    @FXML
    public TextField tfEmail;
    @FXML
    public PasswordField tfPass;
    @FXML
    public PasswordField tfPass2;
    @FXML
    public VBox vbCode;
    @FXML
    public TextField tfCode;

    private Label errUser = new Label();
    private Label errEmail = new Label();
    private Label errPass = new Label();
    private Label errPass2 = new Label();
    private Label errCode = new Label();
    private Alert usuarioCreado = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorRegistro = new Alert(Alert.AlertType.ERROR);

    private BackgroundController backgroundController;
    private UserService userService = new UserService();
    private EmailService emailService = new EmailService();
    private Usuario userRegis;
    private Random random = new Random();
    private int nVerificaion;

    @FXML
    public void initialize() {

        // Configuramos el alert de confirmacion
        usuarioCreado.setTitle("Confirmación");
        usuarioCreado.setHeaderText(null);
        usuarioCreado.setContentText("¡Usuario creado con éxito!");

        // Configuramos el alert de error
        errorRegistro.setTitle("Error de registro");
        errorRegistro.setHeaderText(null);
        errorRegistro.setContentText("Es podible que alguien ya se haya registrado justo hace un momento" +
                ", por favor intentelo de nuevo \n\nPD: Pringao");

        // Configuaramos la clase de estilo de los mensajes de errores
        errUser.getStyleClass().add("error");
        errEmail.getStyleClass().add("error");
        errPass.getStyleClass().add("error");
        errPass2.getStyleClass().add("error");
        errCode.getStyleClass().add("error");

    }

    /**
     * Sincroniza el controlador principal con este
     * @param backgroundController variable del controlador principal
     */
    public void setControlador(BackgroundController backgroundController) {
        this.backgroundController = backgroundController;
    }

    /**
     * Metodo que cambia el menu de Register por el de login
     */
    @FXML
    public void irALogin() {
        backgroundController.cargarLogin();
    }

    /**
     * Metodo que registra a un usuario en caso de que todos los campos esten correctamente rellenados y no exista
     * en la base de datos
     * @param actionEvent
     */
    public void registrarUsuario(ActionEvent actionEvent) {

        // Comprobamos que los campos están correctamente rellenados
        if (comprobarCampos()) {

            // ciframos la contraseña
            String passCifrada = UtilsPass.cifrarPass(tfPass.getText());
            // Creamos el código de verificación aleatorio
            nVerificaion = random.nextInt(100000,999999);
            // Creamos el usuario
            userRegis = new Usuario(tfUser.getText(),tfEmail.getText(), passCifrada);
            // Enviamos un correo
            emailService.enviarCorreoVerificacion(nVerificaion,tfEmail.getText());
            // Pasamos a la vista de verificación de código
            backgroundController.cargarVerification(nVerificaion,userRegis);

        }

    }

    /**
     * Metodo que comprueba el contenido de los textfield
     * @return devuelve un true si los campos están correctamente rellenados o un false si no lo están
     */
    private boolean comprobarCampos() {

        boolean valido = true;

        // Si el campo de usuario esta vacío se muestra mensaje de error
        if (tfUser.getText().trim().isEmpty()) {

            errUser.setText("Usuario vacio");
            vbUser.getChildren().remove(errUser);
            vbUser.getChildren().add(errUser);
            valido = false;

        // Comprobamos que el nombre de usuario no está en uso
        } else if (userService.comprobarNombreUser(tfUser.getText())) {

            errUser.setText("El nombre de usuario ya existe");
            vbUser.getChildren().remove(errUser);
            vbUser.getChildren().add(errUser);
            valido = false;

        // Si esta bien se elimina el mensaje de error
        } else vbUser.getChildren().remove(errUser);

        // Si el campo de correo esta vacio se muestra mensaje de error
        if (tfEmail.getText().trim().isEmpty()) {

            errEmail.setText("Email vacio");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
            valido = false;

        // En caso de que el correo electrónico no este bien construido muestra mensaje de error
        } else if (!tfEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            errEmail.setText("Email no valido. EJEMPLO: nombreCorreo@dominio.com");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
            valido = false;

        // Comprobamos si ya se ha usado ese correo para un usuario
        } else if (userService.comprobarEmailUser(tfEmail.getText())) {

            errEmail.setText("Este correo ya tienen una cuenta asociada");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
            valido = false;

        // Si está correctamente rellenado se elimina el mensaje de error
        } else vbEmail.getChildren().remove(errEmail);

        // Si la contraseña esta vacía muestra un mensaje de error
        if (tfPass.getText().trim().isEmpty()) {

            errPass.setText("Contraseña vacia");
            vbPass.getChildren().remove(errPass);
            vbPass.getChildren().add(errPass);
            valido = false;

        // Muestra un mensaje de error en caso de que la contraseña no coincida con el patron: 8 caracteres, 1 mayúscula y 1 número
        } else if (!tfPass.getText().matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {

            errPass.setText("Contraseña debe tener 8 caracteres(1 mayúscula y 1 numero)");
            vbPass.getChildren().remove(errPass);
            vbPass.getChildren().add(errPass);
            valido = false;

        // Si está bien elimina el mensaje
        } else vbPass.getChildren().remove(errPass);

        // Si la segunda contraseña esta vacía muestra un mensaje de error
        if (tfPass2.getText().trim().isEmpty()) {

            errPass2.setText("Contraseña vacia");
            vbPass2.getChildren().remove(errPass2);
            vbPass2.getChildren().add(errPass2);
            valido = false;

        // Muestra un mensaje de error si la primera contraseña y la segunda son diferentes
        } else if (!tfPass2.getText().equals(tfPass.getText())) {

            errPass2.setText("La contraseña no coincide");
            vbPass2.getChildren().remove(errPass2);
            vbPass2.getChildren().add(errPass2);
            valido = false;

        // Si está bien elimina el mensaje
        } else vbPass2.getChildren().remove(errPass2);

        return valido;

    }

    /**
     * Metodo que nos permite volver a la vista register desde la cista verification
     * @param actionEvent
     */
    public void volver(ActionEvent actionEvent) {
        backgroundController.volverRegister(userRegis);
    }

    /**
     * Metodo que verifica el código enviado por correo
     * @param actionEvent
     */
    public void verificarYRegistrar(ActionEvent actionEvent) {

        // Si esta bien el código se registra al usuario
        if (verificarCode()){

            // Última comprobación de sí existe el nombre de usuario
            if (!userService.comprobarNombreUser(userRegis.getUsuario())){

                // Subimos el usuario a la base de datos
                userService.registrarUsuario(userRegis);
                // Lanzamos un aviso de confirmacion
                usuarioCreado.showAndWait();
                // Volvemos al login
                irALogin();

            } else {
                errorRegistro.showAndWait();
                backgroundController.volverRegister(userRegis);
            }

        }

    }

    /**
     * Metodo que verifica que el código introducido es igual al generado
     * @return Si es correcto devuelve true y en caso de que no devuelve false
     */
    private boolean verificarCode() {

        boolean correcto = true;
        int code =0;

        // Comprobamos que el campo esté lleno
        if (tfCode.getText().trim().isEmpty()){

            errCode.setText("Codigo vacio");
            vbCode.getChildren().remove(errCode);
            vbCode.getChildren().add(errCode);
            correcto = false;

        // Comprobamos que se an introducido números solamente
        } else if (!tfCode.getText().matches("^[0-9]{6}$")) {

            errCode.setText("Solo deben ser números");
            vbCode.getChildren().remove(errCode);
            vbCode.getChildren().add(errCode);
            correcto = false;

        // si está bien pasamos a comprobar si es igual al generado
        } else {

            // Parseamos la cadena a numeros
            try {
                code = Integer.parseInt(tfCode.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // Comprobamos si son iguales los numeros
            if (code != nVerificaion){

                errCode.setText("Código incorrecto");
                vbCode.getChildren().remove(errCode);
                vbCode.getChildren().add(errCode);
                correcto = false;

            }else vbCode.getChildren().remove(errCode);

        }

        return correcto;

    }

    /**
     * Setea el codigo de verificacion
     * @param code codigo que queremos cuardar
     */
    public void setNumbVerificacion(int code) {
        nVerificaion = code;
    }

    /**
     * Setea el usuario que vamos a registrar
     * @param user usuario que queremos registrar
     */
    public void setUserRegis(Usuario user) {
        userRegis = user;
    }

    /**
     * Recuperamos el contenido de los campos del nombre y email
     */
    public void setCampos() {

        tfUser.setText(userRegis.getUsuario());
        tfEmail.setText(userRegis.getEmail());

    }
}
