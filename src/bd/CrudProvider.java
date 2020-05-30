
package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import clases.Provider;
//import logica.Proveedor;
/**
 * FECHA ==> 2019-06-01.
 * Permite realizar las consultas en la base de datos de la tabla "proveedor",
 * la cual almacena el registro de todos los proveedores de productos 
 * que se venden en el negocio.
 * @author Ever Peña Ballesteros
 * @version 1.0.0
 */
public class CrudProvider 
{
    
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;
    
    /**
     * Inicialiaza la posibilidad de consultas con la tabla "proveedor".
     * @param con Conexión con la base de datos.
     */
    public CrudProvider(Conexion con)
    {
        this.conexion = con;      
    }
        
    
    /**
     * Registra un nuevo proveedor en la base de datos.
     * @param p Proveedor que se desea registrar en la base de datos.
     * @return True=>Se registro el proveedor False=>No se pudo registrar el proveedor.
     */
    public boolean createProvider(Provider p)
    {
//        String id = p.getId();
        String name = p.getName(); 
        String nit = p.getNit();
        String cellphone = p.getCellphone(); 
        String email = p.getEmail(); 
        String direction = p.getDirection(); 
        
        if( name.equals("") || nit.equals("") || cellphone.equals("")|| email.equals("")|| direction.equals(""))
            return false;        
        
        boolean bandera = true;
        String query = "INSERT INTO provider (name, nit, cellphone,email,direction)"
                + " VALUES ( ?, ?, ?,?,?)"; 
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(query);
//            ps.setString(1, id);
            ps.setString(1, name);
            ps.setString(2, nit);
            ps.setString(3, cellphone);
            ps.setString(4, email);
            ps.setString(5, direction);
            ps.executeUpdate();    
            System.out.println("Proveedor creado con exito");
        } catch (SQLException e) 
        {
            bandera = false;
            System.out.println("Problem Creating Provider"+e.getMessage());
        }                                
        
        return bandera;        
    }
    
    
    
    /**
     * Obtiene la lista de todos los proveedores registrados en la base de datos,
     * cuyo estado sea 1, osea activo.
     * la utilizo para mapear los datos iniciales.
     * @return Lista con los proveedores de la base de datos.
     */
    public ArrayList<Provider> getAllProviders()
    {
        ArrayList<Provider> providers = new ArrayList<>();
        
        String query = "SELECT * FROM provider WHERE state = 1 ";
        Provider prov = null;
        
        String id;
        String name;
        String nit;
        String cellphone;
        String email;
        String direction;
        try 
        {
//            PreparedStatement ps = this.conexion.getConexion().prepareStatement(query);
//            ps.setString(1, "1");
            ResultSet rs = this.conexion.getStatement().executeQuery(query);
            while(rs.next())
            {
                id = rs.getString("id");
                name = rs.getString("name");
                nit = rs.getString("nit");
                cellphone = rs.getString("cellphone");
                email = rs.getString("email");
                direction = rs.getString("direction");
                prov = new Provider(id, name, nit, cellphone,email,direction);
                providers.add(prov);
            }
            
        } catch (SQLException e) 
        {
            System.out.println("Problems getting List of Providers"+e.getMessage());
            return null;
        }
        return providers;
    }
    
    
    
    /**
     * Busca un proveedor por su código en la base de datos.
     * @param codigo Llave primaria con la que se identifica un proveedor.
     * @return Null=>No encuentra proveedor, de lo contrario=>Proveedor.
     */
    public Provider showProvider(String codigo)
    {
        if(codigo.equals(""))
        {
            return null;
        }
        
        String query = "SELECT * FROM provider";
        Provider prov = null;
          
        String id;
        String name;
        String nit;
        String cellphone;
        String email;
        String direction;
        
        try 
        {
            ResultSet rs = this.conexion.getStatement().executeQuery(query);
            while(rs.next())
            {
                id = rs.getString("id");
                //Si el código del proveedor buscado es encontrado.
                if(codigo.equals(id))
                {
                    id = rs.getString("id");
                    name = rs.getString("name");
                    nit = rs.getString("nit");
                    cellphone = rs.getString("cellphone");
                    email = rs.getString("email");
                    direction = rs.getString("direction");
                    prov = new Provider(id, name, nit, cellphone,email,direction);
                    break;
                }
            }            
        } catch (SQLException e) 
        {
            return null;
        }
        
        return prov;
    }
    
    
    
    
    
          
    /**
     * Actualiaza la información de contacto de un proveedor.
     * @param oldProveedor Proveedor al cual se le van a actualizar los datos.
     * @param newProveedor Proveedor que contiene la información para actualizar.
     * @return True=>Se modificó el proveedor. False=>No se modificó.
     */
    public boolean updateProvider(Provider oldProvider, Provider newProvider)
    {
        boolean bandera = true;
        if(oldProvider == null || newProvider == null)
            return false;
        
        String oldCode = oldProvider.getId();
        
        String id = newProvider.getId();
        String name = newProvider.getName();
        String nit = newProvider.getNit();
        String cell = newProvider.getCellphone();
        String email = newProvider.getEmail();
        String direction = newProvider.getDirection();
        
        String query = "UPDATE provider SET  name = ?, nit = ?, cellphone = ?, email = ?,"
                    + " direction = ?   WHERE id = ? ";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(query);
//            ps.setString(1, id);
            ps.setString(1, name);
            ps.setString(2, nit);
            ps.setString(3, cell);
            ps.setString(4, email);
            ps.setString(5, direction);
            
            ps.setString(6, oldCode);
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    
    
    /**
     * Le coloca el estado 0 al provedor de la table, simulando su eliminación
     * @param prov
     * @return si pudo elimnarlo o no.
     */
    public boolean deleteProvider(Provider prov){
        boolean bandera = true;
        if(prov == null  )
            return false;
        
      
        String id = prov.getId();
     
        String query = "UPDATE provider SET  state = ?  WHERE id = ? ";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(query);
//            ps.setString(1, id);
            ps.setString(1, "0");
            ps.setString(2, id);
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            System.out.println("Probleming Deleting provider"+e.getMessage());
            return false;
        }
        
        return bandera;
    }
    
    
    
    
    
//    /**
//     * Elimina un proveedore que coincida con el código pasado.
//     * @param prov Proveedor que se desea eliminar de la base de datos.
//     * @return True=>Se eliminó el proveedor. False=>No se pudo eliminar.
//     */
//    public boolean eliminarProveedor(Proveedor prov)
//    {
//        if(prov == null)
//            return false;
//        
//        String codigo = prov.getCodigo();
//        boolean bandera = true;
//        String consulta = "DELETE FROM proveedor WHERE codigo = ?";
//        try 
//        {
//            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
//            ps.setString(1,  codigo);
//            ps.executeUpdate(); 
//        } catch (SQLException e) 
//        {
//            return false;
//        }
//        
//        return bandera;
//    }
//    

}

