package vista.views;

import servicio.ClubDeportivo;
import modelo.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * @author Ignacio
 */

public class CambiarDisponibilidadView extends GridPane {

    /**
     * Se muestra en el ComboBox con todos las pistas registrados.
     * Tengo una CheckBox que indicara si la Pista está disponible o no.
     * Un botón para ejecutar la acción, eliminando al Socio de la Base de Datos.
     * @param club donde se gestiona las operaciones con la BD
     * @throws SQLException
     */

    public CambiarDisponibilidadView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Pista> id = new ComboBox();
        id.getItems().addAll(club.getPistas());
        CheckBox disponible = new CheckBox("Disponible");
        Button cambiar = new Button("Aplicar");

        addRow(0, new Label("idPista"), id);
        addRow(1, new Label("Estado"), disponible);
        add(cambiar, 1, 2);

        cambiar.setOnAction(e -> {
            try {
                Pista pistaSeleccionada = id.getValue();

                if (pistaSeleccionada == null) {
                    showError("Seleccione una pista");
                    return;
                }

                boolean nuevoValor = disponible.isSelected();

                boolean ok = club.cambiarDisponibilidadPista(pistaSeleccionada, nuevoValor);

                if (ok) showInfo("Disponibilidad cambiada correctamente");

            } catch (Exception ex) {
                showError(ex.getMessage());
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
