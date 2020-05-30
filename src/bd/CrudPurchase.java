/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import clases.Category;
import clases.Provider;
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
public class CrudPurchase {
     
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;

    public CrudPurchase(Conexion con) {
        this.conexion=con;
    }
    
    
    
  /**
     * Crea una nueva compra en la base de datos.
     * @param purchase Compra que se desea registrar en la base de datos.
     * @return True-->Se registró la compra, else-->False.
     */
    public boolean createPurchase(Purchase purchase)
    {
        if(purchase == null)
            return false;
        
        boolean bandera = true;
        
        LocalDateTime date = purchase.getCreateAt();
        String obs = purchase.getObservation();
        double value = purchase.getValue();
        
        System.out.println("CRUD PURCHASE ID PUR::"+purchase.getId());
        int id_provider = Integer.parseInt(purchase.getProvider().getId());
       
        String consulta = "INSERT INTO purchase (created_at, observaion, value, id_provider) "
                + "VALUES (?, ?, ?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, date+"");
            ps.setString(2, obs);
            ps.setDouble(3, value);
            ps.setInt(4, id_provider);
            ps.executeUpdate();            
        } catch (SQLException e) 
        {
            System.out.println("Problems Creating Purchase"+e.getMessage());
            bandera = false;
        }
        return bandera;        
    }
    
    
    /**
     * Obtiene todas las compras y le adjunta su provedor.
     * @return 
     */
     public ArrayList<Purchase> getAllPurchases()
    {
        ArrayList<Purchase> purchases = new ArrayList<>();
        
//        String query = "SELECT * FROM purchase";

        String query="SELECT c.id AS id_compra, c.created_at AS fecha_compra,"
                +"c.observaion AS observation_compra, c.id_provider AS idP_compra, c.value As valor_compra,"
                +"p.id AS id_provider, p.name AS name_provider, p.nit AS nit_provider,"
                + "p.cellphone AS cellphone_provider,p.direction AS direction_provider,"
                + "p.email AS email_provider FROM purchase AS c INNER JOIN provider AS p ON c.id_provider = p.id";
        
        Supply supply = null;
        
        int id;
        LocalDateTime created_at;
        String observation;
        double value;
        
        int id_provider;
        String name_provider;
        String nit_provider;
        String cellphone_provider;
        String direction_provider;
        String email_provider;
        try 
        {
            CrudCategory crudCat  = new CrudCategory(conexion);
            ResultSet rs = this.conexion.getStatement().executeQuery(query);
            while(rs.next())
            {
                id_provider=rs.getInt("id_provider");
                name_provider=rs.getString("name_provider");
                nit_provider=rs.getString("nit_provider");
                cellphone_provider=rs.getString("cellphone_provider");
                direction_provider=rs.getString("direction_provider");
                email_provider=rs.getString("email_provider");
     
                Provider prov = new   Provider(id_provider+"", name_provider, nit_provider, 
                        cellphone_provider, direction_provider, email_provider);
                
                id=rs.getInt("id_compra");
                String aux=rs.getString("fecha_compra"+"");
                created_at=LocalDateTime.parse(aux);
                observation=rs.getString("observation_compra");
                value=rs.getDouble("valor_compra");
                //inicialmente lista las compras sin insumos luego las llena con la bd.
                Purchase pur= new Purchase(id+"", created_at, observation, value, prov, null);

                purchases.add(pur);
                System.out.println("Desde Crud Pruchase Listando"+id_provider);
            }
              //Llenando las compras con sus elementos para poder trabajar con los objects del restaurante.
            this.getAllElementsFromPurchases(purchases);
            
        } catch (Exception e) 
        {
            System.out.println("Problems getting List of Compras"+e.getMessage());
            return null;
        }
        return purchases;
    }
   
    
    /**
     * Obtiene los insumos involucrados en cada una de las compras del restaurante,
     * se ejecuta al momento de listar todas las compras que tiene el negocio.
     * @param purchases Lista de compras del negocio.
     */
    public void getAllElementsFromPurchases(ArrayList<Purchase> purchases)
    {
        
        int lenghtPurchase = purchases.size();
        ArrayList<Supply> supplies;
        //compra que se va a iterar
        Purchase pur;
     
        //Información del Insumo.
        Supply sup;
        String idSupply;
        String nameSupply;
        double weightSupply;//peso que se llena con el del insumo comprado
        double price_purchase;
        
        //Categoria del Insumo
        int idCat;
        String nameCat;
        String unityMeasure;
        
        for(int i = 0; i < lenghtPurchase; i++) 
        {
          
            supplies = new ArrayList<>();
            pur = purchases.get(i);
            System.out.println("ID pURCHASE::"+pur.getId());
            String consulta = "SELECT sp.id AS id_insumo_comprado, sp.id_purchase AS id_compra_sp,"
                    + " sp.weight AS peso_comprado, sp.unit_price AS precio_unitario,"
                    +"s.id  AS id_insumo, s.name AS nombre_insumo, s.id_category AS id_categoria\n,"
                    + "c.name AS nombre_categoria, c.unity_measure As unidad_medida\n" 
               +"FROM purchase_supply AS sp \n"//saca insumos comprados de cada compra
                +"INNER JOIN supply AS s ON sp.id_supply = s.id \n"//a cada insumo comprado le saca su insumo
                + "INNER JOIN category AS c ON c.id = s.id_category WHERE id_compra_sp = ?";
            //"//a cada insumo le saca su Categoria
            try 
            {
                PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
               //itera las compras del restaurante.
      
                ps.setString(1, pur.getId());
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
                    weightSupply=rs.getDouble("peso_comprado");
                    price_purchase=rs.getDouble("precio_unitario");
                    sup=new Supply(idSupply, nameSupply, weightSupply, cat);
                    sup.setPrice_purchase(price_purchase);
             
                    supplies.add(sup);
                }                
                    //Añadiendo los insumos del array a la compra
                    pur.setInsumos(supplies);
            } catch (Exception e) 
            {
                System.out.println("Problems Defining Supplies for Purchases::"+e.getMessage());                
            }                        
        }        
    }   
    
}
