package sebagro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Autorizacion {

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

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vista_movimientos_pendientes_autorizacion");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String patente = resultSet.getString("patente");
                String nombreSociedad = resultSet.getString("nombre_sociedad");
                String nombreProducto = resultSet.getString("nombre_producto");
                String nombreChofer = resultSet.getString("nombre_chofer");
                String fecha = resultSet.getString("fecha");
                reportePendientes.append("Patente: ").append(patente)
                        .append(" - Sociedad: ").append(nombreSociedad)
                        .append(" - Producto: ").append(nombreProducto)
                        .append(" - Chofer: ").append(nombreChofer)
                        .append(" - Fecha: ").append(fecha).append("\n");
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
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE movimiento m " +
                     "JOIN chofer_transporte ct ON m.id_chofer_transporte = ct.id_chofer_transporte " +
                     "JOIN transporte t ON ct.id_transporte = t.id_transporte " +
                     "SET m.id_estado = CASE " +
                     "WHEN m.id_tipo_movimiento = 1 THEN 7 " + 
                     "WHEN m.id_tipo_movimiento = 2 THEN 5 END " + 
                     "WHERE m.id_estado = 6 AND t.patente = ?")) {
            
            statement.setString(1, patente);
            int rowsUpdated = statement.executeUpdate();
            
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
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE movimiento m " +
                     "JOIN chofer_transporte ct ON m.id_chofer_transporte = ct.id_chofer_transporte " +
                     "JOIN transporte t ON ct.id_transporte = t.id_transporte " +
                     "SET m.id_estado = 8 WHERE m.id_estado = 6 AND t.patente = ?")) {
            
            statement.setString(1, patente);
            int rowsUpdated = statement.executeUpdate();
            
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
