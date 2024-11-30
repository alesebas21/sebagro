package DLL;

import BLL.Chofer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ChoferDAO {

    public static void agregarChofer(String nombre, String cuit) {

        String sql = "INSERT INTO chofer (nombre, CUIT) VALUES (?, ?)";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, cuit);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar chofer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void modificarChofer() {
        String cuit = JOptionPane.showInputDialog("Ingrese el CUIT del chofer a modificar:");

        String sql = "UPDATE chofer SET nombre = ? WHERE CUIT = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
            preparedStatement.setString(1, nuevoNombre);
            preparedStatement.setString(2, cuit);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Chofer modificado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Chofer no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar chofer: " + e.getMessage());
        }
    }

    public static void eliminarChofer() {
        String cuit = JOptionPane.showInputDialog("Ingrese el CUIT del chofer a eliminar:");

        String sql = "DELETE FROM chofer WHERE CUIT = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cuit);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Chofer eliminado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Chofer no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar chofer: " + e.getMessage());
        }
    }

    public static void consultarChoferes() {
        String sql = "SELECT * FROM chofer";
        StringBuilder lista = new StringBuilder("Lista de Choferes:\n");

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_chofer");
                String nombre = resultSet.getString("nombre");
                String cuit = resultSet.getString("CUIT");
                lista.append("ID: ").append(id)
                     .append(", Nombre: ").append(nombre)
                     .append(", CUIT: ").append(cuit).append("\n");
            }
            JOptionPane.showMessageDialog(null, lista.length() > 0 ? lista.toString() : "No hay choferes registrados.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar choferes: " + e.getMessage());
        }
    }

    public static void penalizarChofer() {
        String cuit = JOptionPane.showInputDialog("Ingrese el CUIT del chofer a penalizar:");

        String motivo = JOptionPane.showInputDialog("Ingrese el motivo de la penalización:");
        String fechaDesde = JOptionPane.showInputDialog("Ingrese la fecha desde (yyyy-MM-dd):");
        String fechaHasta = JOptionPane.showInputDialog("Ingrese la fecha hasta (yyyy-MM-dd):");

        String sql = "UPDATE chofer SET penalizacion = ?, motivo = ?, fecha_desde = ?, fecha_hasta = ? WHERE CUIT = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "Infracción");
            preparedStatement.setString(2, motivo);
            preparedStatement.setString(3, fechaDesde);
            preparedStatement.setString(4, fechaHasta);
            preparedStatement.setString(5, cuit);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Chofer penalizado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(null, "Chofer no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al penalizar chofer: " + e.getMessage());
        }
    }

    public static List<Chofer> consultarPenalizaciones() {
        List<Chofer> penalizados = new ArrayList<>();
        String sql = "SELECT * FROM chofer WHERE penalizacion IS NOT NULL";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_chofer");
                String nombre = resultSet.getString("nombre");
                String motivo = resultSet.getString("motivo");
                String fechaDesde = resultSet.getString("fecha_desde");
                String fechaHasta = resultSet.getString("fecha_hasta");
                Chofer chofer = new Chofer(id, nombre, null, motivo, "Infracción", fechaDesde, fechaHasta);
                penalizados.add(chofer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penalizados;
    }
}
