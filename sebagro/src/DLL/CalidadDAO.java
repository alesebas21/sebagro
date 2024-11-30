package DLL;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalidadDAO {

    public int verificarEstadoCamion(String patente) {
        String query = "SELECT id_movimiento FROM movimiento m " +
                       "JOIN CHOFER_TRANSPORTE CT ON CT.id_chofer_transporte = m.id_chofer_transporte " +
                       "JOIN TRANSPORTE T ON CT.ID_TRANSPORTE = T.ID_TRANSPORTE " +
                       "WHERE patente = ? AND id_estado = 1";
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public boolean registrarCalidad(int idMovimiento, int grado) {
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String insertCalidadQuery = "INSERT INTO calidad (id_movimiento, id_resultado_calidad, fecha) VALUES (?, ?, ?)";
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement insertStatement = connection.prepareStatement(insertCalidadQuery)) {
            insertStatement.setInt(1, idMovimiento);
            insertStatement.setInt(2, grado);
            insertStatement.setString(3, fechaActual);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void actualizarEstadoMovimiento(int idMovimiento, int nuevoEstado) {
        String updateQuery = "UPDATE movimiento SET id_estado = ? WHERE id_movimiento = ?";
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, nuevoEstado);
            updateStatement.setInt(2, idMovimiento);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
