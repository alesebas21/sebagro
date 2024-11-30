package sebagro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Usuario {
    private String nombreUsuario;
    private String nombre;
    private int idRol;

    public Usuario(String nombreUsuario,String nombre ,int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre; 
        this.idRol = idRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getIdRol() {
        return idRol;
    }
    
    public String getNombre() {
        return nombre;
    }

    public static Usuario autenticarUsuario(String nombreUsuario, String contrasenia) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = conexion.getInstance().getConection();
            String sql = "SELECT id_rol, nombre FROM usuario WHERE nombre_usuario = ? AND contrasena = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreUsuario);
            preparedStatement.setString(2, contrasenia);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idRol = resultSet.getInt("id_rol");
                String nombre = resultSet.getString("nombre");
                return new Usuario(nombreUsuario, nombre, idRol);
            } else {
                return null; 
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        } finally {
     
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean crearUsuario(String nombre, String nombreUsuario, String contrasenia, int idRol) {
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO usuario (nombre, nombre_usuario, contrasena, id_rol) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, nombre); 
            preparedStatement.setString(2, nombreUsuario);
            preparedStatement.setString(3, contrasenia);
            preparedStatement.setInt(4, idRol);
            int filasInsertadas = preparedStatement.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al crear el usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminarUsuario(String nombreUsuario) {
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "DELETE FROM usuario WHERE nombre_usuario = ?")) {

            preparedStatement.setString(1, nombreUsuario);
            int filasEliminadas = preparedStatement.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizarUsuario(String nombreUsuario, String nuevaContrasenia, int nuevoIdRol, String nuevoNombre) {
        try (Connection connection = conexion.getInstance().getConection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "UPDATE usuario SET contrasena = ?, id_rol = ?, nombre = ? WHERE nombre_usuario = ?")) {

            preparedStatement.setString(1, nuevaContrasenia);
            preparedStatement.setInt(2, nuevoIdRol);
            preparedStatement.setString(3, nuevoNombre); 
            preparedStatement.setString(4, nombreUsuario);
            int filasActualizadas = preparedStatement.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }
    
}
