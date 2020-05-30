package logicainterfaz;

import clases.Restaurant;
import interfaz.Principal;
//import principal.InterfazPrincipal;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
//import logica.Tequilazo;

/**
 * FECHA ==> 2019-05-09.
 * Contiene las configuraciones basicas de la interfaz del login...
 * ...y ademas del estilo grafico realzia las funcionalidades...
 * ...de los eventos que se realizan en esta interfaz.
 * @author Ever Peña B.
 * @version 1.0 .
 */
public class LogicaInterfaz 
{    
    //Nombre y contraseña del usuario que tiene acceso al sistema.
    private final String nombreUsuario = "escuela";
    private final String contrasena = "escuela";
    
   
      
    /**
     * Fecha actual del sistema, cambia dependiendo la fecha del sistema.
     */
    private LocalDate fechaActual;
    
    /**
     * Ventana principal que se carga despues de hacer login correctamente.
     */
    private Principal ventanaPrincipal;
    
    /**
     * El negocio que se crea desde el Main.
     */
    private Restaurant restaurant;
    
    
    /**
     * Inicializa la lógica con tamaño de borde y color de ellos.
     * @param tamBorde Tamaño de los bordes.
     * @param colComp Color de los bordes y componentes.
     * @param restaurante .
     */
    public LogicaInterfaz(Restaurant restaurant)//,Tequilazo tequilazo)
    {
        this.fechaActual = null;
        //this.tequilazo=tequilazo;
        this.restaurant=restaurant;
        
    }
    

    /**
     * Inicializa el resto de los estilos para una correcta...
     * ...visualización de la ventana gráfica de login.
     * @param ventana Ventana gráfica a la que se le van a aplicar estilos(login).
     * @param nombre Campo donde el usuario introduce el nombre de usuario.
     * @param contrasena Campo donde el usuario introduce la contraseña. 
     */
    public void iniciarEstilosLogin(JFrame login, JTextField nombre, JPasswordField contrasena)
    {
        //fondo.setBorder(BorderFactory.createMatteBorder(tamBorde, tamBorde, tamBorde, tamBorde, colorBorde));
        nombre.setHorizontalAlignment(JTextField.CENTER);
        contrasena.setHorizontalAlignment(JTextField.CENTER);
        login.setLocationRelativeTo(null);
    }
    
    /**
     * Comprueba si el usuario y contraseña ingresados por el usuario son correctos.
     * @param ventana Ventana del login.
     * @param nombre Nombre de usuario que ingreso la persona.
     * @param contrasena Contraseña ingresada por la persona.
     * @return Si se logra iniciar sesión o por el contrario algun dato esta mal.
     * 0 ==> El nombre de usuario y contraseña CORRECTOS.
     * 1 ==> El nombre de usuario esta incorrecto.
     * 2 ==> La Contraseña es incorrecta.
     */
    public int comprobarContrasena(JFrame login, String nombre, String contrasena)//, LogicaInterfaz logica)
    {
        int bandera = 0;
        
        if(!nombre.equals(this.nombreUsuario))
            return 1;
        if(!contrasena.equals(this.contrasena))
            return 2;
        
      
        login.dispose();//cierra el login.
        this.ventanaPrincipal= new Principal(restaurant);
        System.out.println("a Logica llega"+restaurant.getProviders().size());
        this.ventanaPrincipal.setVisible(true);
        
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Principal(null).setVisible(true);
//                ventanaPrincipal.setLocationRelativeTo(null);
//            }
//        });
        
        return bandera;
    }
    
  
    
    /**
     * Pinta algunos de los componentes iniciales, tales como son la fecha del sistema.
     * @param fecha Contenedor grafico donde se pinta la fecha del sistema
     * @param encabezado Contenedor grafico para los componentes de la cabeza
     * @param inicio Contenedor de el boton "inicio" del sistema
     * //@param tabla Tabla visual para mostrar infrmación del negocio.
     */
    public void pintarComponentes(JLabel fecha, JLabel encabezado, JLabel inicio)
    {        
        //Colocando la fecha a la interfaz.
        fecha.setText(this.fechaActual.toString());
        encabezado.setText(inicio.getText());
        //Configurando estilos de la tabla.
        //tabla.getTableHeader().setBackground(colorBorde);
        //tabla.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 15));        
    }
    
    /**
     * Permite la interactividad con botones dentro de la interfaz, los sombrea.
     * @param boton Boton con el que se esta interactuando.
     * @param etiqueta Texto que contiene el boton.
     * @param indicador Indica si se debe aplicar o quitar el sombreado.
     */
    public void crearInteractividadBotones(JPanel boton, JLabel etiqueta, boolean indicador)
    {
        if(indicador)
        {
            boton.setBackground(new Color(0,153,153));
            etiqueta.setBackground(new Color(0,153,153));
        }
        else
        {
            boton.setBackground(new Color(255,255,255));
            etiqueta.setBackground(new Color(255,255,255));
        }
        
    }

    /**
     * Modifica el PATH del encabezado del sistema.
     * @param opcion Opción que se ha escogido
     * @param numOpcion Número que identifica cada opción en el menú
     * @param encabezado Contenedor del encabezado
     */
    public void modificarEncabezado(JLabel opcion, int numOpcion, JLabel encabezado)
    {
        encabezado.setText(opcion.getText());
        ImageIcon icon = null;
        
        switch(numOpcion)
        {
            //"INICIO"
            case 1:
                icon = new ImageIcon(getClass().getResource("/imagenes/inicio.png"));
                break;
                
            //"INVENTARIO"
            case 2:
                icon = new ImageIcon(getClass().getResource("/imagenes/inventario.png"));
                break;
                
            //"VENTAS"
            case 3:
                icon = new ImageIcon(getClass().getResource("/imagenes/venta.png"));
                break;
                
            //"COMPRAS"
            case 4:
                icon = new ImageIcon(getClass().getResource("/imagenes/compra.png"));
                break;
                
            //"PROVEEDORES"
            case 5:
                icon = new ImageIcon(getClass().getResource("/imagenes/proveedor.png"));
                break;
                
            //"AJUSTES"
            case 6:
                icon = new ImageIcon(getClass().getResource("/imagenes/iconoAjustes.png"));
                break;
                
            //"SCANNER"
            case 7:
                icon = new ImageIcon(getClass().getResource("/imagenes/Scanner.png"));
                break;                
        
            //"SALIR"
            case 0:
                JOptionPane.showMessageDialog(null, "Cerrar Sesión");
                break;
                
        }
        
        encabezado.setIcon(icon);        
    }
    
    /**
     * Modifica el contenido principal que visualiza el usuario.
     * @param contenedor Panel principal donde se pintara la información.
     * @param contenido Panel con la información que se quiere pintar.
     */
    public void refrescarContenido(JPanel contenedor, JPanel contenido)
    {
        contenedor.removeAll();
        contenedor.repaint();
        contenedor.revalidate();
        
        //Pintando
        contenedor.add(contenido);      
        contenedor.repaint();
        contenedor.revalidate();        
    }
    
    /**
     * Realiza la ultima comprobación cuando el usuario quiere salir del sistema.
     */
    public void comprobarSalida()
    {
        int opc = JOptionPane.showConfirmDialog(null, "Realmente desea salir del TEQUILAZO?",
                "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //0 para si || 1 para no
//        if (opc == 0)
//        {
//            try 
//            {
//                this.tequilazo.getBd().getConexion().getConexion().close();
//            } catch (SQLException e) 
//            {
//                System.out.println("Error Cerrando La Conexión");
//            }
//            
//            System.exit(0);
//        }
    }
    
    
}
