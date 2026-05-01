package cookcloud.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import cookcloud.modelo.Ingrediente;
import cookcloud.modelo.Receta;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UtilsPDF {

    /**
     * Metodo que exporta una receta a pdf
     * @param receta receta que vamos a exportar
     * @param ingredientes ingredientes de la receta
     * @param rutaSalida ruta donde se va a guardar la receta
     */
    public static void exportarReceta(Receta receta, List<Ingrediente> ingredientes, String rutaSalida) {

        try {

            // Cargamos la plntilla
            String html = cargarPlantilla("/cookcloud/template/Receta.html");
            String ingredientesHTML = "";

            // Guardamos los ingredientes en formato html
            for (Ingrediente ing : ingredientes) {
                ingredientesHTML += "<li>" + ing.getCantidad() + " " + ing.getNombre() + "</li>";
            }

            // Cargamos los datos la platilla
            html = html.replace("${nombre}", safe(receta.getTitulo()))
                    .replace("${descripcion}", safe(receta.getResumen()))
                    .replace("${autor}", safe(receta.getUsuario().getUsuario()))
                    .replace("${ingredientes}", ingredientesHTML)
                    .replace("${pasos}", safe(receta.getPasos()));

            // Construimos el pdf
            try (OutputStream os = new FileOutputStream(rutaSalida)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(html, null);
                builder.toStream(os);
                builder.run();
            } catch (FileNotFoundException e) {

                // Mostrar alerta en caso de que el archivo se este utilizanod
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                stageAlert.getIcons().add(new Image("/cookcloud/data/CookCloud_Logo.png"));
                alert.setTitle("Error al exportar");
                alert.setHeaderText(null);
                alert.setContentText("El archivo ya existente que desea reemplazar esta siendo utilizado");
                alert.showAndWait();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carga la plantilla
     * @param path ruta de la platilla
     * @return  plantilla en cadena de texto
     * @throws IOException
     */
    private static String cargarPlantilla(String path) throws IOException {

        InputStream is = UtilsPDF.class.getResourceAsStream(path);

        if (is == null) {
            throw new FileNotFoundException("No se encontró la plantilla: " + path);
        }

        // leemos el html y lo registramos en un string
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;

            // leemos linea a linea y añadimos un salto de linea
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        }
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
