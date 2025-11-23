package servicio;

import java.sql.*;
import java.util.ArrayList;

import modelo.*;


/**
 * @author Ignacio
 */
public class ClubDeportivo {

    private Connection conexion;
    private final String url = "jdbc:mysql://localhost:3306/club_dama";
    private final String usuario = "root";
    private final String password= "Huarke2026";


    public ClubDeportivo() throws SQLException {
        conexion= DriverManager.getConnection(url, usuario, password );
    }

    /**
     *Obtiene la lista completa de socios registrados en la base de datos.
     * @return una lista de Socio
     * @throws SQLException
     */
    public ArrayList<Socio> getSocios() throws SQLException {
        ArrayList<Socio> socios = new ArrayList<>();
        String sql = "SELECT id_socio, dni, nombre, apellidos, telefono, email FROM socios";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Socio socio = new Socio(
                    rs.getString("id_socio"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("email")
            );
            socios.add(socio);
        }
        return socios;
    }

    /**
     *
     * Recupera todas las pistas del club, incluyendo su disponibilidad
     * y el deporte asociado.
     * @return lista de Pista
     * @throws SQLException
     */

    public ArrayList<Pista> getPistas() throws SQLException {
        ArrayList<Pista> pistas = new ArrayList<>();

        String sql = "SELECT id_pista, deporte, descripcion, disponible FROM pistas";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {

            // Convertir el String de la BD al enum correspondiente
            Pista.Deporte deporte = switch (rs.getString("deporte")) {
                case "tenis" -> Pista.Deporte.TENIS;
                case "pádel" -> Pista.Deporte.PADEL;
                case "fútbol sala" -> Pista.Deporte.FUTBOL_SALA;
                default -> throw new SQLException("Valor inesperado para deporte: " + rs.getString("deporte"));
            };

            // Crear pista
            Pista pista = new Pista(
                    rs.getString("id_pista"),
                    deporte,
                    rs.getString("descripcion"),
                    rs.getBoolean("disponible")
            );

            pistas.add(pista);
        }

        rs.close();
        pst.close();
        return pistas;
    }

    /**
     * Obtiene todas las reservas realizadas en el club.
     * @return la lista de Reserva
     * @throws SQLException
     */


    public ArrayList<Reserva> getReservas() throws SQLException {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT id_reserva, id_socio, id_pista, fecha, hora_inicio, duracion_min, precio\n" +
                "FROM reservas;";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Reserva reserva = new Reserva(
                    rs.getString("id_reserva"),
                    rs.getString("id_socio"),
                    rs.getString("id_pista"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora_inicio").toLocalTime(),
                    rs.getInt("duracion_min"),
                    rs.getDouble("precio")
            );
            reservas.add(reserva);
        }

        return reservas;
    }

    /**
     * Inserta un nuevo socio en la base de datos,
     * siempre y cuando su ID no exista ya.
     *
     * @param socios los datos a insertar
     * @return true si el socio se insertó correctamente, false si el ID ya existía.
     * @throws SQLException
     */
    public boolean insertarSocios(Socio socios) throws SQLException{

        String sql = "INSERT INTO socios\n" +
                "(id_socio, dni, nombre, apellidos, telefono, email)\n" +
                "VALUES(?, ?, ?, ?, ?, ?);";

        String sql2 = "select id_socio\n" +
                      "from socios\n"+
                        "where id_socio = ?";

        PreparedStatement pst2 = conexion.prepareStatement(sql2);
        pst2.setString(1, socios.getIdSocio());
        ResultSet rs2 = pst2.executeQuery();

        if(rs2.next() && rs2.getInt(1) > 0){
            return false;
        }

           PreparedStatement pst = conexion.prepareStatement(sql);
           pst.setString(1, socios.getIdSocio());
           pst.setString(2, socios.getDni());
           pst.setString(3, socios.getNombre());
           pst.setString(4, socios.getApellidos());
           pst.setString(5, socios.getTelefono());
           pst.setString(6, socios.getEmail());
            int filas = pst.executeUpdate();
            if(filas>0){
                pst.close();
             return true;
            }
        pst.execute();
        return false;
    }

    /**
     * Elimina un socio de la base de datos según su ID.
     *
     * @param socios obtenemos el ID
     * @return true si el socio fue eliminado, false si no existía.
     * @throws SQLException
     */

    public boolean borrarSocios(Socio socios) throws SQLException{
        String sql = "DELETE FROM socios\n" +
                    "WHERE id_socio = ?;";

        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, socios.getIdSocio());

        int filas2 = pst.executeUpdate();
        if(filas2>0){
            pst.close();
            return true;
        }
        return false;
    }

    /**
     * Realiza la inserción de una nueva Pista.
     * @param pista contiene los datos a insertar.
     * @return true si la pista se insertó correctamente, false en caso contrario.
     * @throws SQLException
     */

    public boolean altaPista(Pista pista) throws SQLException{
        String sql= "INSERT INTO pistas\n" +
                "(id_pista, deporte, descripcion, disponible)\n" +
                "VALUES( ?, ?, ?, ?);";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, pista.getIdPista());
        pst.setString(2, String.valueOf(pista.getDeporte()));
        pst.setString(3, pista.getDescripcion());
        pst.setBoolean(4, pista.isDisponible());
        int filas = pst.executeUpdate();
        if(filas>0){
            pst.close();
            return true;
        }

        return false;
    }

    /**
     * Modifica la disponibilidad de una pista (libre/ocupada).
     * @param pista coge el ID de pista.
     * @param disponible  nuevo estado de disponibilidad (true = disponible).
     * @return true si la pista fue actualizada correctamente.
     * @throws SQLException
     */

    public boolean cambiarDisponibilidadPista(Pista pista, boolean disponible) throws SQLException {
        String sql = "UPDATE pistas SET disponible = ? WHERE id_pista = ?";

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            pst.setBoolean(1, disponible);
            pst.setString(2, pista.getIdPista());
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Registra una nueva reserva en la base de datos.
     * @param reservas usa los datos de la reserva a insertar.
     * @return true si la reserva se registró correctamente.
     * @throws SQLException
     */

    public boolean realizarReserva(Reserva reservas) throws SQLException{
        String sql = "INSERT INTO club_dama.reservas\n" +
                "(id_reserva, id_socio, id_pista, fecha, hora_inicio, duracion_min, precio)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        String sql3 = "SELECT disponible FROM pistas WHERE id_pista = ?";
        PreparedStatement pst3 = conexion.prepareStatement(sql3);
        pst3.setString(1, reservas.getIdPista());
        ResultSet rs3 = pst3.executeQuery();

        if(rs3.next()){
            if(!rs3.getBoolean("disponible")){
                return false;
            }
        }


        String sql2 ="SELECT id_pista, fecha, hora_inicio\n" +
                "FROM reservas\n" +
                "WHERE id_pista = ? and fecha = ? and hora_inicio = ?;";

        PreparedStatement pst2 = conexion.prepareStatement(sql2);
        pst2.setString(1, reservas.getIdPista());
        pst2.setString(2, reservas.getFecha().toString());
        pst2.setString(3, reservas.getHoraInicio().toString());
        ResultSet rs2 = pst2.executeQuery();

        if(rs2.next()){
            return false;
        }

        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, reservas.getIdReserva());
        pst.setString(2, reservas.getIdSocio());
        pst.setString(3, reservas.getIdPista());
        pst.setDate(4, Date.valueOf(reservas.getFecha()));
        pst.setTime(5, Time.valueOf(reservas.getHoraInicio()));
        pst.setInt(6, reservas.getDuracionMin());
        pst.setDouble(7, reservas.getPrecio());

        int filas = pst.executeUpdate();
        if(filas>0){
            pst.close();
            return true;
        }
        pst.execute();

        return false;
    }


    /**
     * Cancela una reserva existente en la base de datos.
     * @param reservas obtiene el ID de la reserva a eliminar.
     * @return true si la reserva fue eliminada correctamente.
     * @throws SQLException
     */
    public boolean cancelarReserva(Reserva reservas) throws SQLException{

        String sql= "DELETE FROM reservas\n" +
                    "WHERE id_reserva= ?;";

        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, reservas.getIdReserva());

        int filas = pst.executeUpdate();
        if(filas>0){
            pst.close();
            return true;
        }

        return false;
    }

}