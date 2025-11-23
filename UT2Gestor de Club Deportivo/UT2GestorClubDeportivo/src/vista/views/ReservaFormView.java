package vista.views;

import modelo.*;
import servicio.ClubDeportivo;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


/**
 * @author Ignacio
 */

public class ReservaFormView extends GridPane {
    /**
     * Se pedirá todos los datos del Reserva.
     * Se muestra un ComboBox con los ID de Socio.
     * Se muestra un ComboBox con los ID de Pista.
     * Se muestra un DatePicker para seleccionar la fecha.
     * TextFiel de hora que permitirá el huso horario convencional de 00:00 a 23:59.
     * Un botón para ejecutar la acción, añadirá nueva Reserva a la Base de Datos.
     * Se muestra un Spiner con los diferentes minutos de uso de la pista.
     * @param club donde se gestiona las operaciones con la BD
     * @throws SQLException
     */

    public ReservaFormView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        TextField idReserva = new TextField();

        ComboBox<Socio> idSocio = new ComboBox();
        //esto pone los elementos en el ComboBox
        idSocio.getItems().addAll(club.getSocios());
        ComboBox<Pista> idPista = new ComboBox();
        //esto pone los elementos en el ComboBox
        idPista.getItems().addAll(club.getPistas());
        DatePicker fecha = new DatePicker(LocalDate.now());
        TextField hora = new TextField("10:00");
        Spinner<Integer> duracion = new Spinner<>(30, 300, 60, 30);
        TextField precio = new TextField("10.0");
        Button crear = new Button("Reservar");

        addRow(0, new Label("idReserva*"), idReserva);
        addRow(1, new Label("Socio*"), idSocio);
        addRow(2, new Label("Pista*"), idPista);
        addRow(3, new Label("Fecha*"), fecha);
        addRow(4, new Label("Hora inicio* (HH:mm)"), hora);
        addRow(5, new Label("Duración (min)"), duracion);
        addRow(6, new Label("Precio (€)"), precio);
        add(crear, 1, 7);


        crear.setOnAction(e -> {
            try {
                LocalTime t = LocalTime.parse(hora.getText());

              Reserva r = new Reserva(idReserva.getText(), idSocio.getValue().getIdSocio(), idPista.getValue().getIdPista(),
                      fecha.getValue(), t, duracion.getValue(), Double.parseDouble(precio.getText()));
                boolean ok=true;
              ok = club.realizarReserva(r);
              if (ok) showInfo("Reserva realizada correctamente. ");
              else showInfo("No se realizó la reserva. Comprueba su disponibilidad.");
            } catch (Exception ex) {
                showError("No se realizó la reserva. \n"+ex.getMessage());
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
