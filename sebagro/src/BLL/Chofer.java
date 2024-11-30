package BLL;

import javax.swing.JOptionPane;

import DLL.ChoferDAO;

public class Chofer {
    private int id;
    private String nombre;
    private String cuit;
    private String motivoPenalizacion;
    private String penalizacion;
    private String fechaDesde;
    private String fechaHasta;

    public Chofer(int id, String nombre, String cuit, String motivoPenalizacion, String penalizacion, String fechaDesde, String fechaHasta) {
        this.id = id;
        this.nombre = nombre;
        this.cuit = cuit;
        this.motivoPenalizacion = motivoPenalizacion;
        this.penalizacion = penalizacion;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }
    
    
    public static void mostrarMenuAdministrarChoferes() {
        String[] opciones = {"ABM Choferes", "Penalizar Choferes", "Volver"};
        int elegido = JOptionPane.showOptionDialog(null, "Menú Administrar Choferes", "Elija una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        switch (elegido) {
            case 0:
                abmChoferes();
                break;
            case 1:
            	ChoferDAO.penalizarChofer();
                break;
            case 2:
                break;
        }
    }

    private static void abmChoferes() {
        String[] opciones = {"Agregar Chofer", "Modificar Chofer", "Eliminar Chofer", "Consultar Choferes", "Volver"};
        int elegido = JOptionPane.showOptionDialog(null, "Menú ABM Choferes", "Elija una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        switch (elegido) {
            case 0:
                System.out.println("Seleccionado: Agregar Chofer");
                new GUI.PantallaChofer().setVisible(true);
                break;
            case 1:
            	ChoferDAO.modificarChofer();
                break;
            case 2:
            	ChoferDAO.eliminarChofer();
                break;
            case 3:
            	ChoferDAO.consultarChoferes();
                break;
            case 4:
                break;
        }
    }
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getMotivoPenalizacion() {
        return motivoPenalizacion;
    }

    public void setMotivoPenalizacion(String motivoPenalizacion) {
        this.motivoPenalizacion = motivoPenalizacion;
    }

    public String getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(String penalizacion) {
        this.penalizacion = penalizacion;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}
