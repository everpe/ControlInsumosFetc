package clases;

import java.util.ArrayList;

/**
 *Menus que va a tener el restaurante
 * @author ever
 */
public class Menu 
{
    private String id;
    private String name;
    private double price;
    
    private String type;
    private ArrayList<Supply> supplies;

    public Menu(String id, double precio, String nombre, String tipo, ArrayList<Supply> insumos) {
        this.id = id;
        this.price = precio;
        this.name = nombre;
        this.type = tipo;
        this.supplies = new ArrayList<>();
    }

    /**
     * Agrega un insumo al Menu comprabando si existe ese id de insumo 
     * y si lo está solo aumenta el peso sino lo añade al Array.
     * @param insumo
     * @return 
     */
    public boolean agregarInsumo(Supply insumo)
    {
        return this.checkSupply(insumo);
    }
    
      /**
     * Verifica que el insumo que se agrega no esté ya en el menú.
     * si esta entonces le modifica el peso con lo que escriban en el Dialog.
     */
    public boolean checkSupply(Supply supply){
        boolean bandera=false;
        for (int i = 0; i < this.supplies.size(); i++) {
           Supply sup=supplies.get(i);
           if(supply.getId().equals(sup.getId())){
               //y le modifica el peso por el que haya colocado en el dialoguito.
               sup.setWeight(supply.getWeight());
               bandera=true;
           }
        }
        if(bandera==false){
            this.supplies.add(supply);
            System.out.println("Si agrega");
        }
        return bandera;
    }
    
    
    
    
    
    
    
    
    public void eliminarInsumo(Supply insumo){
        this.supplies.remove(insumo);
    }
            
    public String getId() {
        return id;
    }

    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
