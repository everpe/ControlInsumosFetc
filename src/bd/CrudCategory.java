package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import clases.Category;

/**
 * FECHA ==> 2019-06-04.
 * Permitira realizar el CRUD de categorias en la base de datos.
 * @author Ever Peña Ballesteros
 * @version 1.0 .
 */
public class CrudCategory 
{
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;
    
    /**
     * Inicializa un CRUD con la tabla categoria.
     * @param con Conexión con la base de datos.
     */
    public CrudCategory(Conexion con)
    {
        this.conexion = con;        
    }
    
    /**
     * Crea una nueva categoria de productos en la base de datos.
     * @param category Categoria que se desea añadir a la base de datos.
     * @return True=>Se pudo crear la categoria, False=>No se pudo crear.
     */
    public boolean createCategory(Category category)
    {        
        String nombre = category.getName();
        String unityMeasure = category.getUnityMeasure();
        
        if(nombre.equals(""))
            return false;
        
        boolean bandera = true;
        String consulta = "INSERT INTO category (name, unity_measure)"
                           + " VALUES (?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, nombre);
            ps.setString(2, unityMeasure);
            ps.executeUpdate();            
        } catch (SQLException e) 
        {
            bandera = false;
        }                                
        return bandera;  
    }
    
    
    
    
    
    /**
     * Obtiene la lista de todos las categorias registradas en la base de datos,
     * la utilizo para mapear los datos iniciales.
     * @return Lista con las categorias de la base de datos.
     */
    public ArrayList<Category> getAllCategories()
    {
        ArrayList<Category> categories = new ArrayList<>();
        
        String consulta = "SELECT * FROM category";
        Category cat = null;
        int cod;
        String nombre;
        String unityMeasure;
        
        try 
        {
            ResultSet rs = this.conexion.getStatement().executeQuery(consulta);
            while(rs.next())
            {
                cod = rs.getInt("id");
                nombre = rs.getString("name");
                unityMeasure = rs.getString("unity_measure");
                cat = new Category(cod, nombre, unityMeasure);
                categories.add(cat);
            }            
        } catch (SQLException e) 
        {
            System.out.println("ERROR Getting All Categories"+e.getMessage());
            return null;
        }
        return categories;
    }
    
    /**
     * Busca una categoria por su código en la base de datos.
     * @param id nombre con el cual se identifica la categoria.
     * @return Null=>No encuentra categoria, de lo contrario=>Categoria.
     */
    public Category getCategoryById(int id)
    {
        if(id<0)
        {
            return null;
        }
        
        String query = "SELECT * FROM category WHERE id = ?";
        Category cat = null;
 
        String name;
        String unityMeasure;
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(query);
            ps.setInt(1, id);           
            ResultSet rs = ps.executeQuery(); 
            name=rs.getString("name");
            unityMeasure=rs.getString("unity_measure");
            
            cat= new Category(id, name, unityMeasure);
//            while(rs.next())
//            {
//                id = rs.getInt("codigo");                
//                name = rs.getString("descripcion");
//                cat = new Categoria(id, id, name); 
//            }                                  
        } catch (SQLException e) 
        {
            System.out.println("Problems Getting CategoryById"+e.getMessage());
            return null;
        }
        
        return cat;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Actualiaza la información de una categoria.
     * @param oldCate Categoria que se quiere actualizar.
     * @param newCate Nueva categoria que reemplazara los datos de la anterior.
     * @return True=>Se modificó la categoria. False=>No se modificó.
     */
//    public boolean actualizarCategoria(Categoria oldCate, Categoria newCate)
//    {
//        if(oldCate == null || newCate == null)
//            return false;
//        
//        boolean bandera = true;
//        int oldCode = oldCate.getCodigo();        
//        //int code = newCate.getCodigo();
//        String nomb = newCate.getNombre();
//        String desc = newCate.getDescripcion();
//        String consulta = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE codigo = ? ";
//        try 
//        {
//            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
//            ps.setString(1, nomb);
//            ps.setString(2, desc);
//            ps.setInt(3, oldCode);
//            ps.executeUpdate(); 
//        } catch (SQLException e) 
//        {
//            return false;
//        }
//        return bandera;
//    }
    
    
    
}
