package BLL;

import javax.swing.*;

import DLL.conexion;

import java.sql.*;

public class CartaPorte {
    private int id;
    private int idProducto;
    private int idLocalidad;
    private String nombreProducto;
    private String nombreLocalidad;
    private Connection connection;

    
    public CartaPorte(int id, int idProducto, int idLocalidad, String nombreProducto, String nombreLocalidad) {
        this.setId(id);
        this.setIdProducto(idProducto);
        this.setIdLocalidad(idLocalidad);
        this.setNombreProducto(nombreProducto);
        this.setNombreLocalidad(nombreLocalidad);
    }

    public void registrarCartaDePorte() {
        String patente = JOptionPane.showInputDialog("Ingrese la Patente del Camión:");
        String cuitChofer = JOptionPane.showInputDialog("Ingrese el CUIT del Chofer:");
        
        
        String tipoMovimientoInput = JOptionPane.showInputDialog("Ingrese el Tipo de Movimiento 1(Carga) o 2(Descarga):");
        int tipoMovimiento;
        try {
            tipoMovimiento = Integer.parseInt(tipoMovimientoInput);
            if (tipoMovimiento != 1 && tipoMovimiento != 2) {
                JOptionPane.showMessageDialog(null, "Tipo de movimiento debe ser 1(Carga) o 2(Descarga).");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para el tipo de movimiento.");
            return;
        }

        try (Connection connection = conexion.getInstance().getConection()) {
            int idChofer = buscarIdChoferPorCuit(cuitChofer, connection);
            if (idChofer == -1) {
                JOptionPane.showMessageDialog(null, "Chofer no encontrado con el CUIT proporcionado.");
                return;
            }

            int idTransporte = insertarTransporte(patente, connection);
            if (idTransporte == -1) {
                JOptionPane.showMessageDialog(null, "Error al insertar en la tabla transporte.");
                return;
            }

            int idLocalidadOrigen = seleccionarLocalidad("Origen", connection);
            if (idLocalidadOrigen == -1) return;

            int idLocalidadDestino = seleccionarLocalidad("Destino", connection);
            if (idLocalidadDestino == -1) return;

            int idProducto = seleccionarProducto(connection);
            if (idProducto == -1) return;

            String ctg = JOptionPane.showInputDialog("Ingrese el CTG:");
            String fecha = JOptionPane.showInputDialog("Ingrese la Fecha (YYYY-MM-DD):");
            String cuitSociedad = JOptionPane.showInputDialog("Ingrese el CUIT de la Sociedad:");
            String contraparte = JOptionPane.showInputDialog("Ingrese la Contraparte:");

            int idSociedad = buscarIdSociedad(cuitSociedad, connection);
            if (idSociedad == -1) {
                JOptionPane.showMessageDialog(null, "Sociedad no encontrada con el CUIT proporcionado.");
                return;
            }

      
            int idEstado = (tipoMovimiento == 2) ? 1 : 5; 
            String insertQuery = "INSERT INTO movimiento (id_localidad_origen, id_localidad_destino, id_producto, ctg, fecha, id_sociedad, id_contraparte, id_tipo_movimiento, id_estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            int idMovimiento = -1; 

            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setInt(1, idLocalidadOrigen);
                insertStatement.setInt(2, idLocalidadDestino);
                insertStatement.setInt(3, idProducto);
                insertStatement.setString(4, ctg);
                insertStatement.setString(5, fecha);
                insertStatement.setInt(6, idSociedad);
                insertStatement.setString(7, contraparte);
                insertStatement.setInt(8, tipoMovimiento);
                insertStatement.setInt(9, idEstado);

                
                insertStatement.executeUpdate();

                
                try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idMovimiento = generatedKeys.getInt(1); // Guardar el ID generado
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del movimiento.");
                        return;
                    }
                }

                
                int idChoferTransporte = insertarChoferTransporte(idChofer, idTransporte, connection);
                if (idChoferTransporte != -1) {
                    String updateQuery = "UPDATE movimiento SET id_chofer_transporte = ? WHERE id_movimiento = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, idChoferTransporte);
                        updateStatement.setInt(2, idMovimiento);
                        updateStatement.executeUpdate();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo obtener el ID del chofer transporte.");
                }

                JOptionPane.showMessageDialog(null, "Carta de Porte registrada exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar la carta de porte: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    public CartaPorte() {
    }
    
    public void modificarCartaDePorte(String ctg) {
        try (Connection connection = conexion.getInstance().getConection()) {
            String consultaSQL = "SELECT id_movimiento, id_localidad_origen, id_localidad_destino, id_producto, fecha, id_sociedad, id_contraparte, id_tipo_movimiento, id_estado FROM movimiento WHERE ctg = ?";
            PreparedStatement consulta = connection.prepareStatement(consultaSQL);
            consulta.setString(1, ctg);
            
            ResultSet resultado = consulta.executeQuery();
            
            if (resultado.next()) {
                int idMovimiento = resultado.getInt("id_movimiento");
                int idLocalidadOrigen = resultado.getInt("id_localidad_origen");
                int idLocalidadDestino = resultado.getInt("id_localidad_destino");
                int idProducto = resultado.getInt("id_producto");
                String fecha = resultado.getString("fecha");
                int idSociedad = resultado.getInt("id_sociedad");
                String contraparte = resultado.getString("id_contraparte");
                int tipoMovimiento = resultado.getInt("id_tipo_movimiento");
                int estado = resultado.getInt("id_estado");

                boolean continuarModificacion = true;
                while (continuarModificacion) {                
                    String mensaje = "Seleccione el campo que desea modificar:\n"
                            + "1. Localidad Origen (Actual: " + idLocalidadOrigen + ")\n"
                            + "2. Localidad Destino (Actual: " + idLocalidadDestino + ")\n"
                            + "3. Producto (Actual: " + idProducto + ")\n"
                            + "4. Fecha (Actual: " + fecha + ")\n"
                            + "5. Sociedad (Actual: " + idSociedad + ")\n"
                            + "6. Contraparte (Actual: " + contraparte + ")\n"
                            + "7. Tipo de Movimiento (Actual: " + tipoMovimiento + ")\n"
                            + "8. Estado (Actual: " + estado + ")\n"
                            + "9. Salir";
                    
                    String opcion = JOptionPane.showInputDialog(mensaje);
                    if (opcion == null || opcion.equals("9")) {
                        continuarModificacion = false;
                        break;
                    }
                    
                   
                    String nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor para " + getCampoNombre(opcion) + ":");
                    if (nuevoValor == null) return; 
                    
                    String campo = "";
                    
                    switch (opcion) {
                        case "1":
                            campo = "id_localidad_origen";
                            idLocalidadOrigen = Integer.parseInt(nuevoValor);
                            break;
                        case "2":
                            campo = "id_localidad_destino";
                            idLocalidadDestino = Integer.parseInt(nuevoValor);
                            break;
                        case "3":
                            campo = "id_producto";
                            idProducto = Integer.parseInt(nuevoValor);
                            break;
                        case "4":
                            campo = "fecha";
                            fecha = nuevoValor;
                            break;
                        case "5":
                            campo = "id_sociedad";
                            idSociedad = Integer.parseInt(nuevoValor);
                            break;
                        case "6":
                            campo = "id_contraparte";
                            contraparte = nuevoValor;
                            break;
                        case "7":
                            campo = "id_tipo_movimiento";
                            tipoMovimiento = Integer.parseInt(nuevoValor);
                            break;
                        case "8":
                            campo = "id_estado";
                            estado = Integer.parseInt(nuevoValor);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción inválida.");
                            continue;
                    }
                    
                    String updateSQL = "UPDATE movimiento SET " + campo + " = ? WHERE id_movimiento = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                        updateStatement.setString(1, nuevoValor);
                        updateStatement.setInt(2, idMovimiento);
                        updateStatement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Campo " + getCampoNombre(campo) + " actualizado correctamente.");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al actualizar el campo " + getCampoNombre(campo) + ": " + e.getMessage());
                    }
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna Carta de Porte con el CTG proporcionado.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
    private String getCampoNombre(String opcion) {
        switch (opcion) {
            case "1": return "Localidad Origen";
            case "2": return "Localidad Destino";
            case "3": return "Producto";
            case "4": return "Fecha";
            case "5": return "Sociedad";
            case "6": return "Contraparte";
            case "7": return "Tipo de Movimiento";
            case "8": return "Estado";
            default: return "Campo Desconocido";
        }
    }



    private int buscarIdChoferPorCuit(String cuitChofer, Connection connection) {
        String query = "SELECT id_chofer FROM chofer WHERE cuit = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cuitChofer);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_chofer");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int insertarTransporte(String patente, Connection connection) {

        if (existePatente(patente, connection)) {
            System.out.println("" + patente);
            return obtenerIdTransporte(patente, connection); 
        }

        String insertQuery = "INSERT INTO transporte (patente) VALUES (?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, patente);
            insertStatement.executeUpdate();

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    private boolean existePatente(String patente, Connection connection) {
        String selectQuery = "SELECT COUNT(*) FROM transporte WHERE patente = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, patente);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    private int obtenerIdTransporte(String patente, Connection connection) {
        String selectQuery = "SELECT id_transporte FROM transporte WHERE patente = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, patente);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_transporte"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    private int seleccionarLocalidad(String tipo, Connection connection) {
        String query = "SELECT id_localidad, nombre FROM localidades";
        StringBuilder localidades = new StringBuilder("Seleccione la Localidad " + tipo + ":\n");

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_localidad");
                String nombre = resultSet.getString("nombre");
                localidades.append(id).append(": ").append(nombre).append("\n");
            }

            String seleccion = JOptionPane.showInputDialog(localidades.toString());
            if (seleccion != null && !seleccion.isEmpty()) {
                try {
                    return Integer.parseInt(seleccion);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida. Por favor, ingrese un número.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int seleccionarProducto(Connection connection) {
        String query = "SELECT id_producto, descripcion FROM producto";
        StringBuilder productos = new StringBuilder("Seleccione un Producto:\n");

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_producto");
                String nombre = resultSet.getString("descripcion");
                productos.append(id).append(": ").append(nombre).append("\n");
            }

            String seleccion = JOptionPane.showInputDialog(productos.toString());
            if (seleccion != null && !seleccion.isEmpty()) {
                try {
                    return Integer.parseInt(seleccion);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada no válida. Por favor, ingrese un número.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int buscarIdSociedad(String cuitSociedad, Connection connection) {
        String query = "SELECT id_sociedad FROM sociedades WHERE cuit = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cuitSociedad);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_sociedad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int insertarChoferTransporte(int idChofer, int idTransporte, Connection connection) {
        String insertChoferTransporteQuery = "INSERT INTO chofer_transporte (id_chofer, id_transporte) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertChoferTransporteQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, idChofer);
            insertStatement.setInt(2, idTransporte);
            insertStatement.executeUpdate();

         
            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getNombreLocalidad() {
		return nombreLocalidad;
	}

	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
	}
    

}


