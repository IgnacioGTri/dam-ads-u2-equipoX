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

public class SocioFormView extends GridPane {

    /**
     * Se pedirá todos los datos del socio
     * Un botón para ejecutar la acción, añadirá nuevo Socio a la Base de Datos.
     * @param club donde se gestiona las operaciones con la BD
     * @throws SQLException
     */

    public SocioFormView(ClubDeportivo club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        TextField id = new TextField();
        TextField dni = new TextField();
        TextField nombre = new TextField();
        TextField apellidos = new TextField();
        TextField tel = new TextField();
        TextField email = new TextField();
        Button crear = new Button("Crear");

        addRow(0, new Label("idSocio*"), id);
        addRow(1, new Label("DNI"), dni);
        addRow(2, new Label("Nombre"), nombre);
        addRow(3, new Label("Apellidos"), apellidos);
        addRow(4, new Label("Teléfono"), tel);
        addRow(5, new Label("Email"), email);
        add(crear, 1, 6);

        crear.setOnAction(e -> {
            try {
                boolean ok=true;
                 ok= club.insertarSocios(new Socio(id.getText(), dni.getText(), nombre.getText(), apellidos.getText(), tel.getText(), email.getText()));
               if (ok) showInfo("Socio insertado correctamente");
                else showError("Error al insertar socio.");
            } catch (Exception ex) {
                showError("Error al insertar socio. \n"+ ex.getMessage());
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
