package BLL;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import DLL.AutorizacionDAO;

public class Autorizacion {

    private static AutorizacionDAO autorizacionDAO = new AutorizacionDAO();

    public static void mostrarMenuAutorizacion() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Registrar decisión", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Autorización", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (elegido) {
                case 0:
                    registrarDecision();
                    break;
                case 1:
                    continuar = false;
                    break;
            }
        }
    }

    private static void registrarDecision() {
        StringBuilder reportePendientes = new StringBuilder();
        reportePendientes.append("Movimientos pendientes de autorización:\n\n");

        try {
            List<String[]> movimientosPendientes = autorizacionDAO.obtenerMovimientosPendientes();

            for (String[] movimiento : movimientosPendientes) {
                reportePendientes.append("Patente: ").append(movimiento[0])
                        .append(" - Sociedad: ").append(movimiento[1])
                        .append(" - Producto: ").append(movimiento[2])
                        .append(" - Chofer: ").append(movimiento[3])
                        .append(" - Fecha: ").append(movimiento[4]).append("\n");
            }

            String patente = JOptionPane.showInputDialog(null, reportePendientes + "\nIngrese la patente para registrar su decisión:");

            if (patente != null && !patente.trim().isEmpty()) {
                String[] opciones = {"Autorizar", "Rechazar"};
                int decision = JOptionPane.showOptionDialog(null, "Seleccione la acción a realizar para la patente: " + patente,
                        "Registrar Decisión", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                if (decision == 0) {
                    autorizarCamion(patente);
                } else if (decision == 1) {
                    rechazarCamion(patente);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ingresó una patente válida.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar movimientos pendientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void autorizarCamion(String patente) {
        try {
            int rowsUpdated = autorizacionDAO.actualizarEstadoAutorizado(patente);

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Camión autorizado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un camión pendiente de autorización con esa patente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al autorizar el camión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void rechazarCamion(String patente) {
        try {
            int rowsUpdated = autorizacionDAO.actualizarEstadoRechazado(patente);

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Camión rechazado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un camión pendiente de autorización con esa patente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al rechazar el camión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
