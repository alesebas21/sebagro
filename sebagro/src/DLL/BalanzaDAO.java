package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BalanzaDAO {

    public int obtenerIdMovimientoPorPatente(String patente) {
        String movimientoQuery = "SELECT m.id_movimiento FROM transporte t " +
                                 "JOIN chofer_transporte ct ON t.id_transporte = ct.id_transporte " +
                                 "JOIN movimiento m ON ct.id_chofer_transporte = m.id_chofer_transporte " +
                                 "WHERE t.patente = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement movimientoStatement = connection.prepareStatement(movimientoQuery)) {

            movimientoStatement.setString(1, patente);
            ResultSet resultSet = movimientoStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_movimiento");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el ID del movimiento: " + e.getMessage());
        }
    }

    public String verificarEstadoPatente(String patente) {
        String estadoQuery = "SELECT e.descripcion FROM transporte t " +
                             "JOIN chofer_transporte ct ON t.id_transporte = ct.id_transporte " +
                             "JOIN movimiento m ON ct.id_chofer_transporte = m.id_chofer_transporte " +
                             "JOIN estado e ON m.id_estado = e.id_estado " +
                             "WHERE t.patente = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement estadoStatement = connection.prepareStatement(estadoQuery)) {

            estadoStatement.setString(1, patente);
            ResultSet resultSet = estadoStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("descripcion");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al verificar el estado: " + e.getMessage());
        }
    }

    public void registrarPesaje(int idMovimiento, double pesoTara, double pesoBruto) {
        String insertPesajeQuery = "INSERT INTO pesaje (id_movimiento, peso_tara, peso_bruto) VALUES (?, ?, ?)";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement insertStatement = connection.prepareStatement(insertPesajeQuery)) {

            insertStatement.setInt(1, idMovimiento);
            insertStatement.setDouble(2, pesoTara);
            insertStatement.setDouble(3, pesoBruto);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar el pesaje: " + e.getMessage());
        }
    }

    public int obtenerTipoMovimientoPorPatente(String patente) {
        String tipoMovimientoQuery = "SELECT m.id_tipo_movimiento FROM transporte t " +
                                     "JOIN chofer_transporte ct ON t.id_transporte = ct.id_transporte " +
                                     "JOIN movimiento m ON ct.id_chofer_transporte = m.id_chofer_transporte " +
                                     "WHERE t.patente = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement tipoMovimientoStatement = connection.prepareStatement(tipoMovimientoQuery)) {

            tipoMovimientoStatement.setString(1, patente);
            ResultSet resultSet = tipoMovimientoStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_tipo_movimiento");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el tipo de movimiento: " + e.getMessage());
        }
    }
}
