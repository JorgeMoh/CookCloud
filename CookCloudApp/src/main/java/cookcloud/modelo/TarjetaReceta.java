package cookcloud.modelo;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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

    public Receta getReceta() {
        return receta;
    }

}
