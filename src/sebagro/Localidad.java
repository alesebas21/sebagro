package sebagro;

public class Localidad {
    private int id;
    private String nombre;

    public Localidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
