/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import clases.Purchase;
import clases.Supply;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ever
 */
public class CrudSupplyPurchase {
    
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;

    /**
     * Inicialiaza un CRUD con la tabla elemento_comprado(Entidad Débil).
     * @param con Conexión con la base de datos.
     */
    public CrudSupplyPurchase(Conexion con) 
    {
        this.conexion = con;
    }
    
    
    /**
     * Inserta en la entidad débil los insumos registrados en una compra.
     * @param purchase Compra de la cual se desea registrar sus insumos.
     * @return True-->Inserto insumos, else-->False.
     */
    public boolean insertElementsFromPurchase(Purchase purchase)
    {
        if(purchase == null)
            return false;
        
        boolean bandera = true;
        Supply sup;
        ArrayList<Supply> supplies = purchase.getInsumos();
        int numElem = supplies.size();
        
         //Datos que se registraran de los elementos.
        
        int idPurchase =Integer.parseInt( purchase.getId());
        int id_supply;
        double weight;
        double unit_price;
        String consulta = "INSERT INTO purchase_supply (id_purchase,id_supply,"
                + " weight, unit_price) VALUES (?, ?, ?, ?)";
        
        for(int i = 0; i < numElem; i++) 
        {     
            sup = supplies.get(i);
            id_supply =Integer.parseInt( sup.getId());
            weight = sup.getWeight();
            unit_price = sup.getPrice_purchase(); 
            
            try 
            {
                PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
                ps.setInt(1, idPurchase);
                ps.setInt(2, id_supply);
                ps.setDouble(3, weight);
                ps.setDouble(4, unit_price);
                ps.executeUpdate();
            } catch (SQLException e) 
            {
                System.out.println("Probles Creating Debil Entity SupplyPurchase"+e.getMessage());
                bandera = false;
            }                                   
        }
        
        return bandera;        
    }
    
    
    
    /**
     * Modifica el peso de un insumo luego de agregar más de 
     * ese insumo en una compra.
     * @param existentes Productos existentes en el inventario.
     * @param suppliesPurchase Productos de la compra.
     * @return True-->Se Modificó, else-->False.
     */
    public boolean modifySuppliesPurchaseRestaurant(ArrayList<Supply> existentes, 
            ArrayList<Supply> suppliesPurchase)
    {
        if(existentes.isEmpty() || suppliesPurchase.isEmpty()
                ||existentes==null||suppliesPurchase==null)
            return false;
        
        int lenghtAgregados = suppliesPurchase.size();
        //Datos de los elementos de la compra.
        Supply new_sup;
        String id_new_sup;
        
        int lenghtExistentes = existentes.size();
        //Datos de los elementos existentes en el inventario.
        Supply old_supply;
        String id_old_supply;
        
        boolean bandera = true;
        double weightT;
        String consulta = "UPDATE supply SET weight = ? WHERE id = ?";
                
        for(int i = 0; i < lenghtAgregados; i++) 
        {
            new_sup = suppliesPurchase.get(i);
            id_new_sup = new_sup.getId();
            //compara cada uno de los insumos de compra con los insuos que ya tiene el 
            //restaurante si soin iguales le suma el peso al insunmo del R.
            for(int j = 0; j < lenghtExistentes; j++) 
            {
                old_supply = existentes.get(j);
                id_old_supply = old_supply.getId();
                
                if(id_new_sup.equals(id_old_supply+""))
                {
                    weightT = old_supply.getWeight() + new_sup.getWeight();
                    try 
                    {
                        PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
                        ps.setDouble(1, weightT);
                        ps.setString(2, old_supply.getId());
                        ps.executeUpdate();
                        old_supply.setWeight(weightT);
                    } catch (SQLException e) 
                    {
                        System.out.println("Problems Updating Weigh Supplies Purchased:"+e.getMessage());
                        bandera = false;
                    }
                    break;
                }                
            }            
        }
        
        return bandera;        
    }
}
 
    