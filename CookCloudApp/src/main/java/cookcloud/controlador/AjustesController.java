package cookcloud.controlador;

import cookcloud.modelo.Usuario;
import cookcloud.servicios.UserService;
import cookcloud.utils.UtilsPass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class AjustesController {

    @FXML
    public TextField tfNombre;
    @FXML
    public TextField tfNuevaPass;
    @FXML
    public TextField tfNuevaPassRepetedia;
    @FXML
    public VBox vbCampoUser;
    @FXML
    public VBox vbNewPass;
    @FXML
    public VBox vbNewPass2;

    private UserService userService = new UserService();

    private GeneralController generalController;
    private Usuario user;

    private Label errUser = new Label();
    private Label errNewPass = new Label();
    private Label errNewPass2 = new Label();

    @FXML
    public void initialize() {

        // Configuramos los mensajes de error
        errUser.getStyleClass().add("error");
        errUser.setWrapText(true);
        errNewPass.getStyleClass().add("error");
        errNewPass.setWrapText(true);
        errNewPass2.getStyleClass().add("error");
        errNewPass2.setWrapText(true);

    }

    /**
     * Metodo que comprueba el campo de nombre nuevo y actualiza el nombre en la base de datos
     */
    public void cambiarNombreDeUsuario() {

        // Comprobamos que el nombre no este vacío
        if (!tfNombre.getText().trim().isEmpty()) {

            // Comprobamos que el nombre no esté registrado en la base de datos
            if (!userService.comprobarNombreUser(tfNombre.getText())) {
                vbCampoUser.getChildren().remove(errUser);
                userService.cambiarNombreDeUsuario(user.getId_usuario(),tfNombre.getText());
                user.setUsuario(tfNombre.getText());
                tfNombre.clear();
            }else{
                vbCampoUser.getChildren().remove(errUser);
                vbCampoUser.getChildren().add(errUser);
                errUser.setText("Nombre no disponible");
            }

        }else{
            vbCampoUser.getChildren().remove(errUser);
            vbCampoUser.getChildren().add(errUser);
            errUser.setText("Campo vacío");
        }

    }

    /**
     * Metodo que cambia la contraseña del usuario en caso de que los campos estén bien rellenados
     */
    public void cambiarContraseña() {

        // Si los campos estan correctos se cifra la contraseña y se actualiza en la base de datos
        if (comprobarNuevaPass()){

            String pass = UtilsPass.cifrarPass(tfNuevaPass.getText());

            userService.cambiarContrasenia(user.getId_usuario(),pass);

            tfNuevaPass.clear();
            tfNuevaPassRepetedia.clear();
        }

    }

    /**
     * Metodo que comprueba que los campos de la nueva contraseña estén correctamente rellenados
     * @return true en caso de que estén correctamente rellenados
     */
    private boolean comprobarNuevaPass() {

        boolean valido = true;

        if (tfNuevaPass.getText().trim().isEmpty()) {

            errNewPass.setText("Contraseña vacia");
            vbNewPass.getChildren().remove(errNewPass);
            vbNewPass.getChildren().add(errNewPass);
            valido = false;

            // Muestra un mensaje de error en caso de que la contraseña no coincida con el patron: 8 caracteres, 1 mayúscula y 1 número
        } else if (!tfNuevaPass.getText().matches("^(?=.*[A-Z])(?=.*\\d).{8,}$")) {

            errNewPass.setText("Contraseña debe tener 8 caracteres(1 mayúscula y 1 numero)");
            vbNewPass.getChildren().remove(errNewPass);
            vbNewPass.getChildren().add(errNewPass);
            valido = false;

            // Si está bien elimina el mensaje
        } else vbNewPass.getChildren().remove(errNewPass);

        // Si la segunda contraseña esta vacía muestra un mensaje de error
        if (tfNuevaPassRepetedia.getText().trim().isEmpty()) {

            errNewPass2.setText("Campo vacia");
            vbNewPass2.getChildren().remove(errNewPass2);
            vbNewPass2.getChildren().add(errNewPass2);
            valido = false;

            // Muestra un mensaje de error si la primera contraseña y la segunda son diferentes
        } else if (!tfNuevaPassRepetedia.getText().equals(tfNuevaPass.getText())) {

            errNewPass2.setText("La contraseña no coincide");
            vbNewPass2.getChildren().remove(errNewPass2);
            vbNewPass2.getChildren().add(errNewPass2);
            valido = false;

            // Si está bien elimina el mensaje
        } else vbNewPass2.getChildren().remove(errNewPass2);

        return valido;

    }

    /**
     * Metodo que borra el usuario de la base de datos después de realizar una doble confirmacion
     */
    public void borrarCuenta() {

        // Configuramos el primer alerta de confirmación
        Alert confirmarElim = new Alert(Alert.AlertType.CONFIRMATION);
        confirmarElim.setTitle("Confirmación");
        confirmarElim.setHeaderText(null);
        confirmarElim.setContentText("¿Quieres ELIMINAR esta cuenta?");
        Stage alertStage = (Stage) confirmarElim.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));

        // lanzamos el alert y guardamos el boton que ha presionado el usuario
        Optional<ButtonType> confirmacion = confirmarElim.showAndWait();

        // si el boton es el de aceptar  se elimina la receta y volvemos a la vista d mis recetas
        if (confirmacion.get() == ButtonType.OK) {

            // Configuramos el segundo alerta de confirmación
            Alert confirmarElim2 = new Alert(Alert.AlertType.CONFIRMATION);
            confirmarElim2.setTitle("Confirmación");
            confirmarElim2.setHeaderText(null);
            confirmarElim2.setContentText("Toda tu información sera eliminada y no se podrá recuperar ¿Seguro que quieres eliminar?");
            Stage alertStage2 = (Stage) confirmarElim2.getDialogPane().getScene().getWindow();
            alertStage2.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));

            // lanzamos de nuevo el alert y guardamos el boton que ha presionado el usuario
            Optional<ButtonType> confirmacion2 = confirmarElim2.showAndWait();

            // si el boton es el de aceptar  se elimina la receta y volvemos a la vista d mis recetas
            if (confirmacion2.get() == ButtonType.OK) {

                userService.eliminarCuenta(user.getId_usuario());
                generalController.cerrarSesion();

            }

        }

    }

    public void setGeneralController(GeneralController generalController) {
        this.generalController = generalController;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
