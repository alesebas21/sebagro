package sebagro;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Sebagro {
    public static void main(String[] args) {
        String usuarioInput = JOptionPane.showInputDialog("Ingrese su usuario:");

        JPasswordField passwordField = new JPasswordField();
        Object[] mensaje = {"Ingrese su contraseña:", passwordField};
        int opcion = JOptionPane.showConfirmDialog(null, mensaje, "Autenticación", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String contraseniaInput = new String(passwordField.getPassword());

            Usuario autenticado = Usuario.autenticarUsuario(usuarioInput, contraseniaInput);
            if (autenticado != null) {
                int idRol = autenticado.getIdRol();
                if (idRol == 1) { 
                    JOptionPane.showMessageDialog(null, "Bienvenido Administrador: " + autenticado.getNombreUsuario());
                } else if (idRol == 2) { 
                    JOptionPane.showMessageDialog(null, "Bienvenido Jefe de Planta: " + autenticado.getNombreUsuario());
                } else {
                    JOptionPane.showMessageDialog(null, "Rol no reconocido.");
                }
                mostrarMenu(autenticado);
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Operación cancelada");
        }
    }

    private static void mostrarMenu(Usuario usuario) {
        boolean continuar = true;
        while (continuar) {
            if (usuario.getIdRol() == 1) { 
                continuar = mostrarMenuAdministrador();
            } else if (usuario.getIdRol() == 2) {
                continuar = mostrarMenuJefeDePlanta();
            } else {
                JOptionPane.showMessageDialog(null, "Rol no reconocido.");
                continuar = false;
            }
        }
    }

    private static boolean mostrarMenuAdministrador() {
        String[] opciones = {"ABM de Usuarios", "Ver Reportes", "Salir"};

        int elegido = JOptionPane.showOptionDialog(null, "Menú Administrador", "Elija una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (elegido == 0) {

            boolean continuarABM = true;
            while (continuarABM) {
                String[] abmOpciones = {"Crear Usuario", "Modificar Usuario", "Eliminar Usuario", "Volver"};
                int abmElegido = JOptionPane.showOptionDialog(null, "ABM de Usuarios", "Elija una opción",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, abmOpciones, abmOpciones[0]);

                switch (abmElegido) {
                case 0: 
                    String nuevoUsuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
                    String nuevaContrasenia = JOptionPane.showInputDialog("Ingrese la contraseña:");
                    String rolString = JOptionPane.showInputDialog("Ingrese el rol (1: Administrador, 2: Jefe de Planta):");
                    int nuevoIdRol = Integer.parseInt(rolString);
                    String nombreDelUsuario = JOptionPane.showInputDialog("Ingrese el nombre completo:");
                    
                    boolean usuarioCreado = Usuario.crearUsuario(nombreDelUsuario, nuevoUsuario, nuevaContrasenia, nuevoIdRol);
                    if (usuarioCreado) {
                        JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al crear el usuario.");
                    }
                    break;

                case 1: 
                    String usuarioAModificar = JOptionPane.showInputDialog("Ingrese el nombre del usuario a modificar:");
                    String nuevaContraseniaModificar = JOptionPane.showInputDialog("Ingrese la nueva contraseña:");
                    String nuevoRolString = JOptionPane.showInputDialog("Ingrese el nuevo rol (1: Administrador, 2: Jefe de Planta):");
                    int nuevoIdRolModificar = Integer.parseInt(nuevoRolString);
                    String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:"); 
                    
                    boolean usuarioModificado = Usuario.actualizarUsuario(usuarioAModificar, nuevaContraseniaModificar, nuevoIdRolModificar, nuevoNombre);
                    if (usuarioModificado) {
                        JOptionPane.showMessageDialog(null, "Usuario modificado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar el usuario.");
                    }
                    break;

                    case 2: 
                        String usuarioAEliminar = JOptionPane.showInputDialog("Ingrese el nombre del usuario a eliminar:");
                        boolean usuarioEliminado = Usuario.eliminarUsuario(usuarioAEliminar);
                        if (usuarioEliminado) {
                            JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.");
                        }
                        break;

                    case 3: 
                        continuarABM = false;
                        break;
                }
            }
        } else if (elegido == 1) {
            JOptionPane.showMessageDialog(null, "Mostrando reportes...");
        } else if (elegido == 2) {
            return false;
        }
        return true;
    }


    private static boolean mostrarMenuJefeDePlanta() {
        String[] opciones = {"Control", "Calidad", "Balanza", "Autorización", "Reportes", "Administrar Choferes", "Salir"};
        int elegido = JOptionPane.showOptionDialog(null, "Menú Jefe de Planta", "Elija una opción",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        switch (elegido) {
            case 0:
                mostrarMenuControl();
                break;
            case 1:
                Calidad.mostrarMenuCalidad();
                break;
            case 2:
                Balanza.mostrarMenuBalanza();
                break;
            case 3:
                Autorizacion.mostrarMenuAutorizacion();
                break;
            case 4:
                Reporte.mostrarMenuReportes();
                break;
            case 5:
            	Chofer.mostrarMenuAdministrarChoferes();
                break;
            case 6:
                return false; 
        }
        return true;
    }
    
    private static void mostrarMenuControl() {
        boolean continuar = true;
        while (continuar) {
            String[] opciones = {"Registrar Carta de Porte", "Modificar Carta de Porte", "Consultar Camiones", "Volver"};
            int elegido = JOptionPane.showOptionDialog(null, "Menú Control", "Elija una opción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            switch (elegido) {
                case 0:
                	int id = 0;
                    int idProducto = 0;
                    int idLocalidad = 0;
                    String nombreProducto = "";
                    String nombreLocalidad = "";
                    CartaPorte cartaPorte = new CartaPorte(id, idProducto, idLocalidad, nombreProducto, nombreLocalidad);
                    cartaPorte.registrarCartaDePorte();
                    break;
                case 1:
                    String ctg = JOptionPane.showInputDialog("Ingrese el CTG de la Carta de Porte que desea modificar:");
                    if (ctg != null && !ctg.trim().isEmpty()) {
                        cartaPorte = new CartaPorte();
                        cartaPorte.modificarCartaDePorte(ctg); 
                    } else {
                        JOptionPane.showMessageDialog(null, "El CTG no puede estar vacío.");
                    }
                case 2:
                    JOptionPane.showMessageDialog(null, "Ingresando a Consultar Camiones...");
                    break;
                case 3:
                    continuar = false; 
                    break;
            }
        }
    }
    
}
