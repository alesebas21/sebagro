package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BLL.Usuario;
import DLL.UsuarioDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField inpContrasena;
	private JTextField inpUsuario;
	
	public static void main (String[] args) {
		PantallaLogin frame = new PantallaLogin();
		frame.setVisible(true);
	}
	
	public PantallaLogin() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Nombre usuario");
		lblUsuario.setBounds(106, 47, 139, 14);
		contentPane.add(lblUsuario);
		
		inpContrasena = new JPasswordField();
		inpContrasena.setBounds(106, 153, 209, 20);
		contentPane.add(inpContrasena);
		
		JLabel lblContrasena = new JLabel("Password");
		lblContrasena.setBounds(106, 126, 209, 14);
		contentPane.add(lblContrasena);
		
		inpUsuario = new JTextField();
		inpUsuario.setBounds(106, 72, 209, 20);
		contentPane.add(inpUsuario);
		inpUsuario.setColumns(10);
		
		JLabel lblError = new JLabel("");
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(106, 225, 264, 14);
		contentPane.add(lblError);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String inpUsuarioText = inpUsuario.getText();
		        String inpContrasenaText = new String(inpContrasena.getPassword());

		        
		        if (inpUsuarioText.isEmpty() || inpContrasenaText.isEmpty()) {
		            lblError.setText("Hay campos vacios");
		            return;
		        }

		        Usuario autenticado = UsuarioDAO.autenticarUsuario(inpUsuarioText, inpContrasenaText);
		        if (autenticado != null) {
		            lblError.setText(""); 
		            dispose();
		            int idRol = autenticado.getIdRol();
		            if (idRol == 1) {
		                JOptionPane.showMessageDialog(null, "Bienvenido Administrador: " + autenticado.getNombreUsuario());
		            } else if (idRol == 2) {
		                JOptionPane.showMessageDialog(null, "Bienvenido Jefe de Planta: " + autenticado.getNombreUsuario());
		            } else {
		                JOptionPane.showMessageDialog(null, "Rol no reconocido.");
		            }
		            Sebagro.mostrarMenu(autenticado);
		        } else {
		            lblError.setText("Credenciales incorrectas");
		        }
		    }
		});


		btnNewButton.setBounds(167, 199, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Inicio de Sesión");
		lblNewLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		lblNewLabel.setBounds(106, 9, 223, 27);
		contentPane.add(lblNewLabel);
		

	}
}
