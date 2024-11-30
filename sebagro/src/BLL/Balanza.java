package BLL;

import DLL.BalanzaDAO;
import javax.swing.JOptionPane;

public class Balanza {

    private static BalanzaDAO balanzaDAO;

    static {
        balanzaDAO = new BalanzaDAO();
    }

    public static void mostrarMenuBalanza() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Registrar Pesaje", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Balanza", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (elegido == 0) {
                String patenteInput = JOptionPane.showInputDialog("Ingrese la Patente:");
                if (patenteInput != null && !patenteInput.isEmpty()) {
                    String descripcionEstado = balanzaDAO.verificarEstadoPatente(patenteInput);
                    if (descripcionEstado != null && descripcionEstado.equals("Pendiente Balanza")) {
                        int idTipoMovimiento = balanzaDAO.obtenerTipoMovimientoPorPatente(patenteInput);

                        String pesoBrutoInput, pesoTaraInput;
                        if (idTipoMovimiento == 2) { 
                            pesoBrutoInput = JOptionPane.showInputDialog("Ingrese el peso bruto:");
                            pesoTaraInput = JOptionPane.showInputDialog("Ingrese el peso tara:");
                        } else { 
                            pesoTaraInput = JOptionPane.showInputDialog("Ingrese el peso tara:");
                            pesoBrutoInput = JOptionPane.showInputDialog("Ingrese el peso bruto:");
                        }

                        try {
                            double pesoTara = Double.parseDouble(pesoTaraInput);
                            double pesoBruto = Double.parseDouble(pesoBrutoInput);

                            int idMovimiento = balanzaDAO.obtenerIdMovimientoPorPatente(patenteInput);
                            balanzaDAO.registrarPesaje(idMovimiento, pesoTara, pesoBruto);
                            JOptionPane.showMessageDialog(null, "Pesaje registrado exitosamente.");

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
}
