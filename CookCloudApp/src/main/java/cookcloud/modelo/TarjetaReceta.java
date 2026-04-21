package cookcloud.modelo;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TarjetaReceta extends VBox {

    private Receta receta;
    private Label titulo;
    private Label resumen;
    private Separator separator = new Separator();

    public TarjetaReceta(Receta receta) {

        this.receta = receta;

        titulo = new Label(receta.getTitulo());
        titulo.setWrapText(true);
        titulo.getStyleClass().add("tituloTarjetaReceta");

        resumen = new Label(receta.getResumen());
        resumen.setWrapText(true);
        resumen.getStyleClass().add("resumenTarjetaReceta");

        this.getChildren().addAll(titulo, separator,resumen);
        this.getStyleClass().add("tarjetaReceta");

    }

    /**
     * Metodo que establecera el nombre del creador de la receta en la parte inferior de la tarjeta
     */
    public void mostrarCreador(){

        Label creador = new Label(receta.getUsuario().getUsuario());
        HBox hbUsuario = new HBox(creador);
        hbUsuario.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(hbUsuario);

    }

    public Receta getReceta() {
        return receta;
    }

}
