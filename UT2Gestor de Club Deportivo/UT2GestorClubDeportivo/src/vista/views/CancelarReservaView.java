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
public class CancelarReservaView extends GridPane {

    /**
     * Tengo una CheckBox con todas las ID de la Reserva.
     * Tengo un botón que al ejecutarse realiza la operación y elimina la Reserva de la Base de datos.
     * @param club donde se gestiona las operaciones con la BD.
     * @throws SQLException
     */
    public CancelarReservaView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Reserva> idReserva = new ComboBox();
        //llenar el comboBox
        idReserva.getItems().addAll(club.getReservas());
        Button cancelar = new Button("Cancelar reserva");


        addRow(0, new Label("Reserva"), idReserva);
        add(cancelar, 1, 1);

        cancelar.setOnAction(e -> {
            try {
                boolean ok=true;
                ok= club.cancelarReserva(idReserva.getValue());
                if(ok) showInfo("Reserva Cancelada correctamente. ");
                else showError("Reserva Cancelada no encontrada. ");
            } catch (Exception ex) {
                showError("Reserva Cancelada no encontrada. \n" +ex.getMessage());
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
