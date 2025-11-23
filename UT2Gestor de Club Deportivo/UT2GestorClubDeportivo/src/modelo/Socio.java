package modelo;

public class Socio {


    private final String idSocio; // único, inmutable
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;


    /**
     * Crea una nueva instancia de Socio.
     *
     * @param idSocio  Identificador único del socio (obligatorio).
     * @param dni      Documento nacional de identidad.
     * @param nombre   Nombre del socio.
     * @param apellidos Apellidos del socio.
     * @param telefono Teléfono de contacto.
     * @param email    Correo electrónico del socio.
     *
     * @throws IdObligatorioException si el idSocio es null o está vacío.
     */
    public Socio(String idSocio, String dni, String nombre, String apellidos, String telefono, String email) throws IdObligatorioException {
        if (idSocio==null ||idSocio.isEmpty()){
            throw new IdObligatorioException("El id del socio no puede ser vacío");
        }

        this.idSocio = idSocio;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return String.valueOf(this.idSocio);
    }
}
