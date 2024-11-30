package BLL;

public class Usuario {
    private String nombreUsuario;
    private String nombre;
    private int idRol;

    // Constructor
    public Usuario(String nombreUsuario, String nombre, int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre; 
        this.idRol = idRol;
    }

    // Getters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombre() {
        return nombre;
    }
}
