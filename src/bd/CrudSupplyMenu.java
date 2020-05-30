/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import clases.Menu;
import clases.Purchase;
import clases.Supply;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ever
 */
public class CrudSupplyMenu {
    
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;

    /**
     * Inicialiaza un CRUD con la tabla elemento_comprado(Entidad Débil).
     * @param con Conexión con la base de datos.
     */
    public CrudSupplyMenu(Conexion con) 
    {
        this.conexion = con;
    }
    
    
    /**
     * Inserta en la entidad débil los insumos registrados en cada menu que se crea.
     * @param menu Compra de la cual se desea registrar sus insumos.
     * @return True-->Inserto insumos, else-->False.
     */
    public boolean insertElementsFromMenu(Menu menu)
    {
        if(menu == null)
            return false;
        
        boolean bandera = true;
        Supply sup;
        ArrayList<Supply> supplies = menu.getSupplies();
        int numElem = supplies.size();
        
        //Datos que se registraran de los Insumos.
        
        int id_menu =Integer.parseInt( menu.getId());
        int id_supply;
        double weight;

        String consulta = "INSERT INTO supply_menu (id_menu,id_supply,weight) VALUES (?, ?, ?)";
        for(int i = 0; i < numElem; i++) 
        {     
            sup = supplies.get(i);
            id_supply =Integer.parseInt( sup.getId());
            weight = sup.getWeight();
            try 
            {
                PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
                ps.setInt(1, id_menu);
                ps.setInt(2, id_supply);
                ps.setDouble(3, weight);

                ps.executeUpdate();
            } catch (SQLException e) 
            {
                System.out.println("Problems Creating Debil Entity SupplyMenu"+e.getMessage());
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
 
    