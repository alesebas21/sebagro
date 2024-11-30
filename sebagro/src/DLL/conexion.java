package DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/sebagro";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection conect;
    private static conexion instance;

    private conexion() {
        conectar();
    }

    private void conectar() {
        try {
            conect = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    public static conexion getInstance() {
        if (instance == null) {
            instance = new conexion();
        }
        return instance;
    }

    public Connection getConection() {
        try {
            if (conect == null || conect.isClosed()) {
                conectar(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al verificar la conexión: " + e.getMessage());
        }
        return conect;
    }
}
