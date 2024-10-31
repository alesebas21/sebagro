package sebagro;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chofer {
    private int id;
    private String nombre;
    private String motivoPenalizacion;
    private String penalizacion;
    private String fechaDesde;
    private String fechaHasta;
    private String cuit;

    public Chofer(int id, String nombre, String cuit, String motivoPenalizacion, String penalizacion, String fechaDesde, String fechaHasta) {
        this.setId(id);
        this.setNombre(nombre);
        this.setCuit(cuit);
        this.setMotivoPenalizacion(motivoPenalizacion);
        this.setPenalizacion(penalizacion);
        this.setFechaDesde(fechaDesde);
        this.setFechaHasta(fechaHasta);
    }

    public static void agregarChofer() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del chofer:");
        String cuit = JOptionPane.showInputDialog("Ingrese el CUIT del chofer:");

        String sql = "INSERT INTO chofer (nombre, CUIT) VALUES (?, ?)";

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, cuit);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Chofer agregado exitosamente!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar chofer: " + e.getMessage());
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

    public static void consultarPenalizaciones() {
        String sql = "SELECT * FROM chofer WHERE penalizacion IS NOT NULL";
        StringBuilder lista = new StringBuilder("Lista de Choferes Penalizados:\n");

        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_chofer");
                String nombre = resultSet.getString("nombre");
                String motivo = resultSet.getString("motivo");
                String fechaDesde = resultSet.getString("fecha_desde");
                String fechaHasta = resultSet.getString("fecha_hasta");

                lista.append("ID: ").append(id)
                     .append(", Nombre: ").append(nombre)
                     .append(", Motivo: ").append(motivo)
                     .append(", Desde: ").append(fechaDesde)
                     .append(", Hasta: ").append(fechaHasta)
                     .append("\n");
            }
            JOptionPane.showMessageDialog(null, lista.length() > 0 ? lista.toString() : "No hay choferes penalizados.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar penalizaciones: " + e.getMessage());
        }
    }

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPenalizacion() {
		return penalizacion;
	}

	public void setPenalizacion(String penalizacion) {
		this.penalizacion = penalizacion;
	}

	public String getMotivoPenalizacion() {
		return motivoPenalizacion;
	}

	public void setMotivoPenalizacion(String motivoPenalizacion) {
		this.motivoPenalizacion = motivoPenalizacion;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
}
