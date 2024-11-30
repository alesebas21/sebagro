package BLL;

import javax.swing.JOptionPane;

public class GestorChoferes {

    public static void mostrarMenuAdministrarChoferes() {
        String[] opciones = {"ABM Choferes", "Penalizar Choferes", "Volver"};
        int elegido = JOptionPane.showOptionDialog(null, "Menú Administrar Choferes", "Elija una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        switch (elegido) {
            case 0:
                abmChoferes();
                break;
            case 1:
                penalizarChofer();
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
                agregarChofer();
                break;
            case 1:
                modificarChofer();
                break;
            case 2:
                eliminarChofer();
                break;
            case 3:
                consultarChoferes();
                break;
            case 4:
                break;
        }
    }

    private static void agregarChofer() {
        // Implementación del método para agregar chofer
    }

    private static void modificarChofer() {
        // Implementación del método para modificar chofer
    }

    private static void eliminarChofer() {
        // Implementación del método para eliminar chofer
    }

    private static void consultarChoferes() {
        // Implementación del método para consultar choferes
    }

    private static void penalizarChofer() {
        // Implementación del método para penalizar un chofer
    }
}
