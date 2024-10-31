package sebagro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Reporte {

    public static void mostrarMenuReportes() {
        String[] opcionesReportes = {
            "Movimientos por Sociedad",
            "Total Movimientos por Producto y Sociedad",
            "Movimientos por Fecha y Sociedad",
            "Movimientos Pendientes de Autorización",
            "Stock Total por Sociedad y Producto",
            "Volver"
        };

        int elegido; 
        do {
         elegido = JOptionPane.showOptionDialog(null, "Menú Reportes", "Seleccione un reporte",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesReportes, opcionesReportes[0]);

        switch (elegido) {
            case 0:
                mostrarVista("SELECT * FROM vista_movimientos_por_sociedad", "Movimientos por Sociedad");
                break;
            case 1:
                mostrarVista("SELECT * FROM vista_total_movimientos_por_producto_sociedad", "Total Movimientos por Producto y Sociedad");
                break;
            case 2:
                mostrarVista("SELECT * FROM vista_movimientos_por_fecha_sociedad", "Movimientos por Fecha y Sociedad");
                break;
            case 3:
                mostrarVista("SELECT * FROM vista_movimientos_pendientes_autorizacion", "Movimientos Pendientes de Autorización");
                break;
            case 4:
                mostrarVista("SELECT * FROM vista_stock_total_por_sociedad_producto", "Stock Total por Sociedad y Producto");
                break;
            case 5:
                break;}
        } while (elegido != 5); 
    }

    private static void mostrarVista(String consultaSQL, String titulo) {
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(consultaSQL);
             ResultSet resultSet = statement.executeQuery()) {

            int columnCount = resultSet.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = resultSet.getMetaData().getColumnName(i + 1);
            }

            Object[][] data = new Object[0][columnCount]; 
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                data = addRow(data, row);
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos de la vista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Object[][] addRow(Object[][] original, Object[] newRow) {
        Object[][] newData = new Object[original.length + 1][newRow.length];
        System.arraycopy(original, 0, newData, 0, original.length);
        newData[original.length] = newRow;
        return newData;
    }
}
