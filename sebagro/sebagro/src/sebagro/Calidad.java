package sebagro;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calidad {

    public static void mostrarMenuCalidad() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Ingresar Calidad", "Modificar Calidad", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Calidad", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (elegido) {
                case 0:
                    registrarCalidad(); 
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Ingresando a Modificar Calidad...");
                    break;
                case 2:
                    continuar = false;
                    break;
            }
        }
    }

    private static void registrarCalidad() {
        String patente = JOptionPane.showInputDialog("Ingrese la Patente del Camión:");
        try (Connection connection = conexion.getInstance().getConection()) {
            int idMovimiento = verificarEstadoCamion(patente, connection);
            if (idMovimiento == -1) {
                JOptionPane.showMessageDialog(null, "Camión no encontrado o no está en estado 'Pendiente Calado'.");
                return;
            }

            String gradoInput = JOptionPane.showInputDialog("Ingrese el Grado de Calidad (0, 1, 2 o 3):");
            int grado;
            try {
                grado = Integer.parseInt(gradoInput);
                if (grado < 0 || grado > 3) {
                    JOptionPane.showMessageDialog(null, "Grado inválido. Debe ser 0, 1, 2 o 3.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida para el grado de calidad.");
                return;
            }

            String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String insertCalidadQuery = "INSERT INTO calidad (id_movimiento, id_resultado_calidad, fecha) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertCalidadQuery)) {
                insertStatement.setInt(1, idMovimiento);
                insertStatement.setInt(2, grado);
                insertStatement.setString(3, fechaActual);
                insertStatement.executeUpdate();

                int nuevoEstado = (grado == 0) ? 6 : 5;
                actualizarEstadoMovimiento(idMovimiento, nuevoEstado, connection);

                JOptionPane.showMessageDialog(null, "Calidad registrada exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la calidad: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    private static int verificarEstadoCamion(String patente, Connection connection) {
        String query = "SELECT id_movimiento FROM movimiento m\r\n"
        		+ "JOIN CHOFER_TRANSPORTE CT ON Ct.id_chofer_transporte = m.id_chofer_transporte\r\n"
        		+ "JOIN TRANSPORTE T ON CT.ID_TRANSPORTE = T.ID_TRANSPORTE \r\n"
        		+ "WHERE patente = ? AND id_estado = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, patente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_movimiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static void actualizarEstadoMovimiento(int idMovimiento, int nuevoEstado, Connection connection) {
        String updateQuery = "UPDATE movimiento SET id_estado = ? WHERE id_movimiento = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, nuevoEstado);
            updateStatement.setInt(2, idMovimiento);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
