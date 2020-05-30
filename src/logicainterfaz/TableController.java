/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicainterfaz;

import clases.Menu;
import clases.Provider;
import clases.Purchase;
import clases.Restaurant;
import clases.Supply;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;

/**
 *
 * @author ever
 */
public class TableController {

    Restaurant restaurant;
    public TableController(Restaurant rest) {
        this.restaurant=rest;
    }
    
    
    /**
     * Obtiene el proveedor que se selecciona de la tabla proveedores.
     * @param tabla Tabla en la cual se grafican los proveedores del sistema.
     * @return Null -->No se selecciono nada, Proveedor -->Proveedor seleccionado.
     */
    public Provider getProviderFromTable(JTable tabla)
    {
        Provider prov = null;
        int row = tabla.getSelectedRow();
        if(row != -1)
        {
            String id = tabla.getModel().getValueAt(row, 0).toString();
            String name = tabla.getModel().getValueAt(row, 1).toString();
            String nit = tabla.getModel().getValueAt(row, 2).toString();
            String phone = tabla.getModel().getValueAt(row, 3).toString();
            String email = tabla.getModel().getValueAt(row, 4).toString();
            String direction = tabla.getModel().getValueAt(row, 5).toString();
            
            System.out.println("el Id en table Controller es::"+id+name);
            prov = new Provider(id, name, nit, phone,email,direction);
        }
        
        if(prov!=null){
            return prov;
        }
        return null;
    }
    
    /**
     * Obtiene el insumo de la tabla de insumos
     * @param tabla
     * @return 
     */
    public Supply getSupplyFromTableSupplies(JTable tabla)
    {
        Supply sup = null;
        int row = tabla.getSelectedRow();
        if(row != -1)
        {
            String id = tabla.getModel().getValueAt(row, 0).toString();
            String name = tabla.getModel().getValueAt(row, 1).toString();
            String weight = tabla.getModel().getValueAt(row, 2).toString();
            
            sup=this.restaurant.getSupplyById(id);
        }
        if(sup!=null){
            return sup;
        }
        return null;
    }
    
    
    /**
     * Recibe un jComb y devuelve el insumo que representa el item seleccinado
     * @param jComb, el combo que viene del Dialogo de Compras 
     * @return 
     */
    public Supply getSupplyFromComb(JComboBox jComb){
        String data=jComb.getSelectedItem().toString();
        if(!data.equalsIgnoreCase("----Seleccionar Insumo----")){
            String array[]=data.split("-");
        
            System.out.println("Desde Table Controller:"+data);        
            return this.restaurant.getSupplyById(array[0]);
        }
        return null;
    }

      /**
     * Recibe un jList y devuelve el Menu que representa el item seleccinado
     * @param jlist, la Lista que viene del Dialogo Menus. 
     * @return 
     */
    public Menu getMenuFromList(JList jlist){
        String data=jlist.getSelectedValue()+"";
        if(!data.equalsIgnoreCase("")){
            
            String array[]=data.split("-");     
            System.out.println("Data0:::::"+array[0]);
            return this.restaurant.getMenuById(array[0]);
        }
        return null;
    }
    
    
    /**
     * Obtiene el proveedor que se seleccionó para una compra del arreglo de 
     * proveedores que tiene el restaurante
     * @param jComb
     * @return 
     */
    public Provider getProviderFromComb(JComboBox jComb){
         String data=jComb.getSelectedItem().toString();
        if(!data.equalsIgnoreCase("----Seleccionar Proveedor----")){
            String array[]=data.split("-");
        
            System.out.println("Desde Table Controller:"+data);        
            return this.restaurant.getProviderById(array[0]);
        }
        return null;
    } 
    
    
    /**
     * Obtiene Una compra de la tabla con la Lista de Compras
     */
    
    public Purchase getPurchaseFromTable(JTable table){
        Purchase pur = null;
        int row = table.getSelectedRow();
        if(row != -1)
        {
            String id = table.getModel().getValueAt(row, 0).toString();
            pur=this.restaurant.getPurchaseById(id);
        }
        return pur;
    }
    
}
