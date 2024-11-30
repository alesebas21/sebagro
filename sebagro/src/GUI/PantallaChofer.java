package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DLL.ChoferDAO;

public class PantallaChofer extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombreField;
    private JTextField cuitField;
    private JButton agregarButton;
    private JButton cancelarButton;

    public PantallaChofer() {
        setTitle("Agregar Chofer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lvlNombre = new JLabel("Nombre:");
        lvlNombre.setBounds(53, 0, 101, 47);
        JLabel lblCuit = new JLabel("CUIT:");
        lblCuit.setBounds(53, 52, 134, 47);
        nombreField = new JTextField();
        nombreField.setBounds(197, 11, 177, 36);
        cuitField = new JTextField();
        cuitField.setBounds(197, 57, 177, 36);
        agregarButton = new JButton("Agregar");
        agregarButton.setBounds(23, 121, 164, 29);
        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(197, 121, 163, 29);
        getContentPane().setLayout(null);

        getContentPane().add(lvlNombre);
        getContentPane().add(nombreField);
        getContentPane().add(lblCuit);
        getContentPane().add(cuitField);
        getContentPane().add(agregarButton);
        getContentPane().add(cancelarButton);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText().trim();
                String cuit = cuitField.getText().trim();

                if (nombre.isEmpty() || cuit.isEmpty()) {
                    JOptionPane.showMessageDialog(PantallaChofer.this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ChoferDAO.agregarChofer(nombre, cuit); 
                    JOptionPane.showMessageDialog(PantallaChofer.this, "Chofer agregado exitosamente!");
                    dispose(); 
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PantallaChofer().setVisible(true));
    }
}
