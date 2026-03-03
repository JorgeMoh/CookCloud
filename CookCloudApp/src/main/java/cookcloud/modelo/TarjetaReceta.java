package cookcloud.modelo;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TarjetaReceta extends VBox {

    private Receta receta;
    private Label titulo;
    private Label resumen;

    public TarjetaReceta(Receta receta) {

        this.receta = receta;

        titulo = new Label(receta.getTitulo());
        titulo.getStyleClass().add("tituloTarjetaReceta");
        resumen = new Label(receta.getResumen());
        resumen.getStyleClass().add("resumenTarjetaReceta");

        this.getChildren().addAll(titulo, resumen);

    }

    public Receta getReceta() {
        return receta;
    }

}
