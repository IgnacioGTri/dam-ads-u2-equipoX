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

public class BajaSocioView extends GridPane {

    /**
     * Se muestra en el ComboBox con todos los socios registrados.
     * Un botón para ejecutar la acción, eliminando al Socio de la Base de Datos.
     * @param club donde se gestiona las operaciones con la BD
     * @throws SQLException
     */
    public BajaSocioView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<Socio> id = new ComboBox<>();
        Button baja = new Button("Dar de baja");
        //esto pone los elementos en el ComboBox
        id.getItems().addAll(club.getSocios());

        addRow(0, new Label("Socio"), id);
        add(baja, 1, 1);

        baja.setOnAction(e -> {

        try{
            boolean ok = true;
            ok = club.borrarSocios(id.getValue());
            if (ok) showInfo("Socio borrado correctamente");
            else  showError("Socio borrado no encontrado.");
        }catch(Exception ex){
            showError("Fallo al borrar. Socio con reserva activa.");
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
