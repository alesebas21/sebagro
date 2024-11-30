package sebagro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Balanza {

	public static void registrarPesaje(String patente, double pesoTara, double pesoBruto) {
	    int idMovimiento = obtenerIdMovimientoPorPatente(patente);

	    if (idMovimiento == -1) {
	        JOptionPane.showMessageDialog(null, "No se encontró un movimiento para la patente ingresada.");
	        return;
	    }

	    String insertPesajeQuery = "INSERT INTO pesaje (id_movimiento, peso_tara, peso_bruto) VALUES (?, ?, ?)";
	    
	    try (Connection connection = conexion.getInstance().getConection();
	         PreparedStatement insertStatement = connection.prepareStatement(insertPesajeQuery)) {

	        insertStatement.setInt(1, idMovimiento);
	        insertStatement.setDouble(2, pesoTara);
	        insertStatement.setDouble(3, pesoBruto);
	        insertStatement.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Pesaje registrado exitosamente.");

	        // Actualizar el estado en función del tipo de movimiento
	        actualizarEstadoMovimiento(idMovimiento);
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al registrar el pesaje: " + e.getMessage());
	    }
	}


    public static String verificarEstadoPatente(String patente) {
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
                JOptionPane.showMessageDialog(null, "No se encontró la patente en la base de datos.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar el estado: " + e.getMessage());
            return null;
        }
    }

    // Método para mostrar el menú de balanza
    public static void mostrarMenuBalanza() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Registrar Pesaje", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Balanza", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (elegido == 0) {
                String patenteInput = JOptionPane.showInputDialog("Ingrese la Patente:");
                if (patenteInput != null && !patenteInput.isEmpty()) {
                    String descripcionEstado = verificarEstadoPatente(patenteInput);
                    if (descripcionEstado != null && descripcionEstado.equals("Pendiente Balanza")) {
                        int idTipoMovimiento = obtenerTipoMovimientoPorPatente(patenteInput);

                        String pesoBrutoInput, pesoTaraInput;
                        if (idTipoMovimiento == 2) { // Descarga
                            pesoBrutoInput = JOptionPane.showInputDialog("Ingrese el peso bruto:");
                            pesoTaraInput = JOptionPane.showInputDialog("Ingrese el peso tara:");
                        } else { // Carga
                            pesoTaraInput = JOptionPane.showInputDialog("Ingrese el peso tara:");
                            pesoBrutoInput = JOptionPane.showInputDialog("Ingrese el peso bruto:");
                        }

                        try {
                            double pesoTara = Double.parseDouble(pesoTaraInput);
                            double pesoBruto = Double.parseDouble(pesoBrutoInput);

                            registrarPesaje(patenteInput, pesoTara, pesoBruto);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error: Por favor, ingrese valores numéricos válidos.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El movimiento no se encuentra en un estado correcto. " +
                                                              "Estado actual: " + descripcionEstado);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese una patente válida.");
                }
            } else if (elegido == 1) {
                continuar = false;
            }
        }
    }

    
    public static int obtenerIdMovimientoPorPatente(String patente) {
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
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del movimiento: " + e.getMessage());
            return -1; 
        }
    }
    
    public static void actualizarEstadoMovimiento(int idMovimiento) {
        String tipoMovimientoQuery = "SELECT id_tipo_movimiento FROM movimiento WHERE id_movimiento = ?";
        String actualizarEstadoQuery = "UPDATE movimiento SET id_estado = ? WHERE id_movimiento = ?";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement tipoMovimientoStatement = connection.prepareStatement(tipoMovimientoQuery);
             PreparedStatement actualizarEstadoStatement = connection.prepareStatement(actualizarEstadoQuery)) {

            tipoMovimientoStatement.setInt(1, idMovimiento);
            ResultSet resultSet = tipoMovimientoStatement.executeQuery();

            if (resultSet.next()) {
                int idTipoMovimiento = resultSet.getInt("id_tipo_movimiento");
                int nuevoEstado = (idTipoMovimiento == 2) ? 7 : 1; // 7 para Descarga (Finalizado) y 1 para Carga (Pendiente Calado)

                actualizarEstadoStatement.setInt(1, nuevoEstado);
                actualizarEstadoStatement.setInt(2, idMovimiento);
                actualizarEstadoStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Estado del movimiento actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el tipo de movimiento.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + e.getMessage());
        }
    }
    
    public static int obtenerTipoMovimientoPorPatente(String patente) {
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
                JOptionPane.showMessageDialog(null, "No se encontró el tipo de movimiento para la patente ingresada.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el tipo de movimiento: " + e.getMessage());
            return -1;
        }
    }


}
