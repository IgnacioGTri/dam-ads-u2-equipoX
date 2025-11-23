package vista;

import modelo.*;
import servicio.ClubDeportivo;
import vista.views.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.sql.SQLException;

/**
 * @author Ignacio
 */
public class MainApp extends Application {

    private ClubDeportivo club;
    private BorderPane root;
    private Label status;


    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

       try {
           club = new ClubDeportivo();
           //      LLamo al metodo de la lógica para cargar los datos del fichero
       } catch (SQLException e) {
           showError("Error de conexión" + e.getMessage());
           showInfo("Conectado");
       }
        root = new BorderPane();
        root.setTop(buildMenuBar());
        status = new Label("Listo");
        status.setPadding(new Insets(4));
        root.setBottom(status);

        // Vista por defecto
        root.setCenter(new DashboardView(club));

        Scene scene = new Scene(root, 960, 640);
        stage.setTitle("Club DAMA Sports");
        stage.setScene(scene);
        stage.show();
    }


    private MenuBar buildMenuBar() {
        MenuBar mb = new MenuBar();

        Menu socios = new Menu("Socios");
        MenuItem altaSocio = new MenuItem("Alta socio");
        altaSocio.setOnAction(e -> root.setCenter(new SocioFormView(club)));
        MenuItem bajaSocio = new MenuItem("Baja socio");
        bajaSocio.setOnAction(e -> {
            try {
                root.setCenter(new BajaSocioView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        socios.getItems().addAll(altaSocio, bajaSocio);

        Menu pistas = new Menu("Pistas");
        MenuItem altaPista = new MenuItem("Alta pista");
        altaPista.setOnAction(e -> root.setCenter(new PistaFormView(club)));
        MenuItem cambiarDisp = new MenuItem("Cambiar disponibilidad");
        cambiarDisp.setOnAction(e -> {
            try {
                root.setCenter(new CambiarDisponibilidadView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        pistas.getItems().addAll(altaPista, cambiarDisp);

        Menu reservas = new Menu("Reservas");
        MenuItem crearReserva = new MenuItem("Crear reserva");
        crearReserva.setOnAction(e -> {
            try {
                root.setCenter(new ReservaFormView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        MenuItem cancelarReserva = new MenuItem("Cancelar reserva");
        cancelarReserva.setOnAction(e -> {
            try {
                root.setCenter(new CancelarReservaView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        reservas.getItems().addAll(crearReserva, cancelarReserva);

        Menu ver = new Menu("Ver");
        MenuItem dashboard = new MenuItem("Resumen");
        dashboard.setOnAction(e -> {
            try {
                root.setCenter(new DashboardView(club));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        ver.getItems().addAll(dashboard);

        Menu archivo = new Menu("Archivo");

        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> {
            try {

            } catch (Exception ignored) {}
            Platform.exit();
        });
        archivo.getItems().addAll(/*guardar,*/ new SeparatorMenuItem(), salir);

        mb.getMenus().addAll(archivo, socios, pistas, reservas, ver);
        return mb;
    }


    public void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        try {
         //   LLamo al méotodo del modelo para guardar los datos
        } catch (Exception ignored) {}
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
