
package bd;

import clases.Category;
import clases.Provider;
import clases.Menu;
import clases.Purchase;
import clases.Supply;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author ever
 */
public class CrudMenu {
    
    
        
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;
    
    /**
     * Inicialiaza la posibilidad de consultas con la tabla "proveedor".
     * @param con Conexión con la base de datos.
     */
    public CrudMenu(Conexion con)
    {
        this.conexion = con;      
    }
    
    
    
    /**
     * Crea un nuevo Menu en la base de datos.
     * @param  Menu que se desea registrar en la base de datos.
     * @return True-->Se registró el menu , else-->False.
     */
    public boolean createMenu(Menu menu)
    {
        if(menu == null)
            return false;
        
        boolean bandera = true;
        

         
        double price=menu.getPrice();
        String name=menu.getName();
        String type=menu.getType();
        
        String consulta = "INSERT INTO menu (price, name, type) "
                + "VALUES (?, ?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setDouble(1, price);
            ps.setString(2, name);
            ps.setString(3, type);
            
            ps.executeUpdate();            
        } catch (SQLException e) 
        {
            System.out.println("Problems Creating Menu"+e.getMessage());
            bandera = false;
        }
        return bandera;        
    }
  
    
     /**
     * Obtiene todos los menus del restaurante
     * @return arreglo de menus del restaurante (bd).
     */
     public ArrayList<Menu> getAllMenus()
    {
        ArrayList<Menu> menus = new ArrayList<>();
        
        String query = "SELECT * FROM menu ORDER BY id";

        int id;
        double price;
        String name;
        String type;
        
        Menu menu=null;

        try {
            ResultSet rs = this.conexion.getStatement().executeQuery(query);
            while(rs.next())
            {
                id=rs.getInt("id");
                price=rs.getDouble("price");
                name=rs.getString("name");
                type=rs.getString("type");
                
                //Crea el menu sin insumos inicialmente
                menu= new Menu(id+"",  price,name,type, null);
                menus.add(menu);
                System.out.println("Desde Crud Pruchase Listando"+name);
            }
              //Llenando los Menus con sus insumos, para listar que insumos tiene cada menu. 
            this.getAllSuppliesFromMenus(menus);
            
        } catch (Exception e) 
        {
            System.out.println("Problems getting List of Menus"+e.getMessage());
            return null;
        }
        return menus;
    }
     
     
     
     
     
     
     
     
     /**
     * Obtiene los insumos involucrados en cada uno de los menus del restaurante,
     * se ejecuta al momento de listar(crud) todas las compras que tiene el negocio.
     * @param menus Lista de compras del negocio.
     */
    public void getAllSuppliesFromMenus(ArrayList<Menu> menus)
    {
        
        int lenghtArrayMenu = menus.size();
        ArrayList<Supply> supplies;
        //Menu que se va a iterar
        Menu menu;
     
        //Información del Insumo.
        Supply sup;
        String idSupply;
        String nameSupply;
        double weightSupply;//peso que se llena con el del insumo comprado
        
        //Categoria del Insumo
        int idCat;
        String nameCat;
        String unityMeasure;
        
        for(int i = 0; i < lenghtArrayMenu; i++) 
        {
          
            supplies = new ArrayList<>();
            menu = menus.get(i);
            //sm=>supply_menu, s=>supply; c=>category    
            String consulta = "SELECT sm.id_supply AS id_insumo_Menu, sm.id_menu AS id_menu,"
                    + " sm.weight AS peso_menudeado,"
                    +"s.id  AS id_insumo, s.name AS nombre_insumo, s.id_category AS id_categoria\n,"
                    + "c.name AS nombre_categoria, c.unity_measure As unidad_medida\n" 
               +"FROM supply_menu AS sm \n"//saca insumos destinados de cada Menu
                +"INNER JOIN supply AS s ON sm.id_supply = s.id \n"//a cada insumo comprado le saca su insumo
                +"INNER JOIN category AS c ON c.id = s.id_category WHERE id_menu = ?";
            //"//a cada insumo le saca su Categoria
            try 
            {
                PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
               //itera las compras del restaurante.
      
                ps.setString(1, menu.getId());
                ResultSet rs = ps.executeQuery();
                //crea todos los isumos de la compra y los guarda en el array
                while(rs.next())
                {
               
                    idCat = rs.getInt("id_categoria");
                    nameCat=rs.getString("nombre_categoria");
                    unityMeasure=rs.getString("unidad_medida");
                    Category cat=new Category(idCat,nameCat,unityMeasure);
                    
//                    //Insumo
                    idSupply=rs.getString("id_insumo");
                    nameSupply=rs.getString("nombre_insumo");
                    weightSupply=rs.getDouble("peso_menudeado");
                    sup=new Supply(idSupply, nameSupply, weightSupply, cat);
       
                    supplies.add(sup);
                }                
                    //Añadiendo los insumos del array a la compra
                    menu.setSupplies(supplies);
            } catch (Exception e) 
            {
                System.out.println("Problems Defining Supplies for Menus::"+e.getMessage());                
            }                        
        }
        
    }
    
    
    
    
    
        /**
     * Elimina un Menu de la bd del Restaurante.
     * @param code Código del producto que se desea eliminar.
     * @return True-->Eliminó, False-->No eliminó.
     */
    public boolean deleteMenu(String code)
    {
        if(code.equals(""))
            return false;
        
        boolean bandera = true;
        String consulta = "DELETE * FROM menu"+
                "INNER JOIN supply_menu AS s ON m.id = s.id_menu  WHERE id = ?";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, code);
            ps.executeUpdate();
        } catch (SQLException e) 
        {
            System.out.println("Problems deleting Menu :"+e.getMessage());
            bandera = false;
        }
        return bandera;        
    }
}