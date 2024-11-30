package BLL;

public class Producto {
    private int id;
    private String descripcion;

    public Producto(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
