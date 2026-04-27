package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import cookcloud.servicios.EmailService;
import cookcloud.servicios.UserService;
import cookcloud.utils.UtilsPass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Random;

public class RecoverPassController {

    @FXML
    public VBox vbEmail;
    @FXML
    public TextField tfCorreo;
    @FXML
    public TextField tfCode;
    @FXML
    public PasswordField tfPass;
    @FXML
    public PasswordField tfPass2;
    @FXML
    public VBox vbNewPass;
    @FXML
    public VBox vbNewPass2;
    @FXML
    public VBox vbCode;

    private UserService userService = new UserService();
    private EmailService emailService = new EmailService();
    private Random random = new Random();

    private Label errEmail = new Label();
    private Label errPass = new Label();
    private Label errPass2 = new Label();
    private Label errCode = new Label();

    private GeneralController generalController;
    private Usuario usuario;
    private int nVerificacion;

    @FXML
    public void initialize() {

        errEmail.getStyleClass().add("error");
        errPass.getStyleClass().add("error");
        errPass2.getStyleClass().add("error");
        errCode.getStyleClass().add("error");

    }

    /**
     * metodo para volver al login
     */
    public void irALogin() {
        generalController.cargarLogin();
    }

    /**
     * Metodo que comprueba que el correo esta bien escrito y que existe en la base de datos para enviar el código de verificación
     */
    public void correoDeRecuperacion() {

        //Comprobamos que el correo esta bien escrito
        if (tfCorreo.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            vbEmail.getChildren().remove(errEmail);

            // comprobamos que exista en la base de datos
            if (userService.comprobarEmailUser(tfCorreo.getText())) {

                // generamos el codigo de verificacion, obtenemos al usuario y le enviamos un correo con el codigo
                nVerificacion = random.nextInt(100000,999999);
                usuario = userService.buscarUsuarioPorEmail(tfCorreo.getText());
                emailService.enviarCorreoRecuperacion(nVerificacion,tfCorreo.getText());
                generalController.cargarChangePass(nVerificacion,usuario);

            }else {
                errEmail.setText("Usuario no existe");
                vbEmail.getChildren().remove(errEmail);
                vbEmail.getChildren().add(errEmail);
            }
        }else{
            errEmail.setText("Email no valido. EJEMPLO: nombreCorreo@dominio.com");
            vbEmail.getChildren().remove(errEmail);
            vbEmail.getChildren().add(errEmail);
        }

    }

    /**
     * Metodo que si los campos están correctos cambia la contraseña del usuario y vuelve al login
     */
    public void cambiarPass() {

        if(comprobarCampos()){

            System.out.println("Todo perfe vamos a cambiar la contraseña");

            String pass = UtilsPass.cifrarPass(tfPass.getText());

            userService.cambiarContrasenia(usuario.getId_usuario(), pass);

            irALogin();

        }

    }

    /**
     * Metodo que comprueba el campo de código y los de contraseña nueva
     * @return true en caso de que estén bien rellenados
     */
    private boolean comprobarCampos() {

        boolean valido = true;
        int code = 0;

        if (!tfCode.getText().matches("^[0-9]{6}$")) {

            errCode.setText("Código no valido");
            vbCode.getChildren().remove(errCode);
            vbCode.getChildren().add(errCode);
            valido = false;

        // si está bien pasamos a comprobar si es igual al generado
        } else {

            // Parseamos la cadena a numeros
            try {
                code = Integer.parseInt(tfCode.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // Comprobamos si son iguales los numeros
            if (code != nVerificacion){

                errCode.setText("Código incorrecto");
                vbCode.getChildren().remove(errCode);
                vbCode.getChildren().add(errCode);
                valido = false;

            }else vbCode.getChildren().remove(errCode);

        }

        // Muestra un mensaje de error en caso de que la contraseña no coincida con el patron: 8 caracteres, 1 mayúscula y 1 número
        if (!tfPass.getText().matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {

            errPass.setText("Contraseña debe tener 8 caracteres(1 mayúscula y 1 numero)");
            vbNewPass.getChildren().remove(errPass);
            vbNewPass.getChildren().add(errPass);
            valido = false;

        // Comprobamos que no sea la misma contraseña que ya tiene
        } else if(UtilsPass.verificarPass(tfPass.getText(),usuario.getPassw())){

            errPass.setText("No puedes volver a poner la contraseña actual");
            vbNewPass.getChildren().remove(errPass);
            vbNewPass.getChildren().add(errPass);
            valido = false;

        } else vbNewPass.getChildren().remove(errPass);

        // Si la segunda contraseña esta vacía muestra un mensaje de error
        if (tfPass2.getText().trim().isEmpty()) {

            errPass2.setText("Contraseña vacia");
            vbNewPass2.getChildren().remove(errPass2);
            vbNewPass2.getChildren().add(errPass2);
            valido = false;

        // Muestra un mensaje de error si la primera contraseña y la segunda son diferentes
        } else if (!tfPass2.getText().equals(tfPass.getText())) {

            errPass2.setText("La contraseña no coincide");
            vbNewPass2.getChildren().remove(errPass2);
            vbNewPass2.getChildren().add(errPass2);
            valido = false;

        // Si está bien elimina el mensaje
        } else vbNewPass2.getChildren().remove(errPass2);

        return valido;

    }

    /**
     * Metodo que vuelve a la vista de correo de verificación
     */
    public void volver() {
        generalController.cargarRecoverPass();
    }

    public void setGeneralController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setnVerificacion(int nVerificacion) {
        this.nVerificacion = nVerificacion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
