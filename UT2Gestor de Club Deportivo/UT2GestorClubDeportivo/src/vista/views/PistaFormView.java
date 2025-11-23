package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * @author Ignacio
 */

public class PistaFormView extends GridPane {

    /**
     * Se pedirá todos los datos de Pista
     * Uno de los datos solo admitirá ('tenis', 'fútbol sala' y 'pádel')
     * Habrá una CheckBox para el marco Disponible
     * Un botón para ejecutar la acción, añadirá nuevo Socio a la Base de Datos.
     * @param club donde se gestiona las operaciones con la BD
     * @throws SQLException
     */

    public PistaFormView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        TextField id = new TextField();
        TextField deporte = new TextField();
        TextField descripcion = new TextField();
        CheckBox disponible = new CheckBox("Disponible");
        Button crear = new Button("Crear");

        addRow(0, new Label("idPista*"), id);
        addRow(1, new Label("Deporte"), deporte);
        addRow(2, new Label("Descripción"), descripcion);
        addRow(3, new Label("Operativa"), disponible);
        add(crear, 1, 4);

        crear.setOnAction(e -> {
            try {
                String d = deporte.getText().trim();

                Pista.Deporte deporteEnum;

                switch (d) {
                    case "tenis" -> deporteEnum = Pista.Deporte.TENIS;
                    case "pádel" -> deporteEnum = Pista.Deporte.PADEL;
                    case "fútbol sala" -> deporteEnum = Pista.Deporte.FUTBOL_SALA;
                    default -> {
                        showError("Deporte inválido. Debe ser: tenis, pádel o fútbol sala");
                        return;
                    }
                }

                Pista pista = new Pista(
                        id.getText(),
                        deporteEnum,
                        descripcion.getText(),
                        disponible.isSelected()
                );

                boolean ok = club.altaPista(pista);

                if (ok)
                    showInfo("Pista dada de alta correctamente.");
                else
                    showError("Error al insertar pista.");

            } catch (Exception ex) {
                showError("Error al insertar pista.(tenis, fútbol sala o pádel) \n"+ex.getMessage());
            }
        });
    }

        private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
