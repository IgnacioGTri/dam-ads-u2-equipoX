package modelo;

/**
 * @author Ignacio
 */

public class Pista {

    private final String idPista; // único, inmutable
    private Deporte deporte;
    private String descripcion;
    private boolean disponible;

    // ENUM coincide EXACTAMENTE con MySQL
    public enum Deporte {
        TENIS("tenis"),
        PADEL("pádel"),
        FUTBOL_SALA("fútbol sala");

        private final String valorBD;

        Deporte(String valorBD) {
            this.valorBD = valorBD;
        }

        public String getValorBD() {
            return valorBD;
        }

        @Override
        public String toString() {
            return valorBD; // útil para ComboBox
        }
    }

    /**
     *
     * @param idPista Identificador único de la Pista
     * @param deporte     Deporte asociado a la pista.
     * @param descripcion Descripción textual de la pista.
     * @param disponible  Disponibilidad inicial de la pista.
     *
     * @throws IdObligatorioException
     */
    public Pista(String idPista, Deporte deporte, String descripcion, boolean disponible)
            throws IdObligatorioException {

        if (idPista == null || idPista.isBlank()) {
            throw new IdObligatorioException("El id de la pista no puede ser vacío");
        }

        this.idPista = idPista;
        this.deporte = deporte;
        this.descripcion = descripcion;
        this.disponible = disponible;
    }

    public String getIdPista() {
        return idPista;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    @Override
    public String toString() {
        return String.valueOf(this.idPista);
    }
}
