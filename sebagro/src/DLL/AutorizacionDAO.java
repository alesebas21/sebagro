package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorizacionDAO {

    public List<String[]> obtenerMovimientosPendientes() throws SQLException {
        List<String[]> movimientosPendientes = new ArrayList<>();

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM vista_movimientos_pendientes_autorizacion");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String[] movimiento = {
                    resultSet.getString("patente"),
                    resultSet.getString("nombre_sociedad"),
                    resultSet.getString("nombre_producto"),
                    resultSet.getString("nombre_chofer"),
                    resultSet.getString("fecha")
                };
                movimientosPendientes.add(movimiento);
            }
        }

        return movimientosPendientes;
    }

    public int actualizarEstadoAutorizado(String patente) throws SQLException {
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
            return statement.executeUpdate();
        }
    }

    public int actualizarEstadoRechazado(String patente) throws SQLException {
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE movimiento m " +
                     "JOIN chofer_transporte ct ON m.id_chofer_transporte = ct.id_chofer_transporte " +
                     "JOIN transporte t ON ct.id_transporte = t.id_transporte " +
                     "SET m.id_estado = 8 WHERE m.id_estado = 6 AND t.patente = ?")) {

            statement.setString(1, patente);
            return statement.executeUpdate();
        }
    }
}
