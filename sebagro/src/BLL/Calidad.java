package BLL;

import DLL.CalidadDAO;
import javax.swing.*;

public class Calidad {

    public static void mostrarMenuCalidad() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Ingresar Calidad", "Modificar Calidad", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Calidad", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (elegido) {
                case 0:
                    registrarCalidad();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Ingresando a Modificar Calidad...");
                    break;
                case 2:
                    continuar = false;
                    break;
            }
        }
    }

    private static void registrarCalidad() {
        String patente = JOptionPane.showInputDialog("Ingrese la Patente del Camión:");
        CalidadDAO calidadDAO = new CalidadDAO();
        int idMovimiento = calidadDAO.verificarEstadoCamion(patente);

        if (idMovimiento == -1) {
            JOptionPane.showMessageDialog(null, "Camión no encontrado o no está en estado 'Pendiente Calado'.");
            return;
        }

        String gradoInput = JOptionPane.showInputDialog("Ingrese el Grado de Calidad (0, 1, 2 o 3):");
        int grado;
        try {
            grado = Integer.parseInt(gradoInput);
            if (grado < 0 || grado > 3) {
                JOptionPane.showMessageDialog(null, "Grado inválido. Debe ser 0, 1, 2 o 3.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida para el grado de calidad.");
            return;
        }

        if (calidadDAO.registrarCalidad(idMovimiento, grado)) {
            int nuevoEstado = (grado == 0) ? 6 : 5;
            calidadDAO.actualizarEstadoMovimiento(idMovimiento, nuevoEstado);
            JOptionPane.showMessageDialog(null, "Calidad registrada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la calidad.");
        }
    }
}
