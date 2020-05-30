
package clases;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *Salida de menus(insumos)del restaurante unas pagas otras gratis.
 * @author ever
 */
public class Output
{
    private String id;
    private LocalDateTime createAt;
    private double  value ;
    private Concept concept;
    private ArrayList<Menu>menus;
    //Los insumos que se han gastado en los menus realizados.
    private ArrayList<Supply>suppliesConsumed;

    public Output(String id, LocalDateTime dateCretion, double value,Concept concept,ArrayList<Menu>menus) {
        this.id = id;
        this.createAt = dateCretion;
        this.concept=concept;
        this.value = value;
        this.menus=new ArrayList<>();
        this.suppliesConsumed=new ArrayList<>();
       

    }
    
    /**
     * Saca los 
     */
   /* public void iniciarInsumosGastados()
    {
        for (int i = 0; i < menus.size(); i++)
        {
            Menu menu=menus.get(i);
            ArrayList<Insumo>insumosMenu=menu.getInsumos();
            for (int j = 0; j < insumosMenu.size(); j++)
            {
                Insumo insumoMenu=insumosMenu.get(j);
                if(!insumosGastados.contains(insumoMenu))//si no lo contiene
                {
                    this.insumosGastados.add(insumoMenu);
                }else{//si si lo contiene
                    for (int k = 0; k < insumosGastados.size(); k++)
                    {
                        Insumo m=insumosGastados.get(k);
                        if(m.getId().equalsIgnoreCase(insumoMenu.getId()))
                        {
                            insumosGastados.get(k).setPeso(insumosGastados.get(k).getPeso()+insumoMenu.getPeso());
                        }
                    }
                }
            }
        }
//        this.definirInsumos();
    }*/
    

    /**
     * 
     * @param menu
     * @return 
     */
    public boolean agregarMenu(Menu menu)
    {
//        ArrayList<Insumo>insu=menu.getInsumos();
        if(this.concept.getConcepto().equals("Venta"));
        {
            this.value=value+menu.getPrice();
        }
       
        ArrayList<Supply>insumosMenu=menu.getSupplies();
        for (int i = 0; i < insumosMenu.size(); i++) {
            Supply ins=insumosMenu.get(i);
            this.agregarInsumo(ins);
        }
        this.menus.add(menu);
        
 
        return true;
    
    }
    
     /**
     * Agrega un insumo a la compra comprabando si existe ese id de insumo 
     * y si lo está soo aumenta el peso sino lo añade al Array.
     * @param insumo
     * @return 
     */
    public boolean agregarInsumo(Supply insumo)
    {
        for (int i = 0; i < suppliesConsumed.size(); i++) {
            if(suppliesConsumed.get(i).getId().equalsIgnoreCase(insumo.getId()))
            {
               suppliesConsumed.get(i).setWeight(suppliesConsumed.get(i).getWeight()+insumo.getWeight());
               return false;//No lo añadió porque ese id de insumo ya estaba
            }
        }
        suppliesConsumed.add(insumo);
        return true;
    }
    
    
    /**
     * Elimina un menu de la salida y verifica antes si el concepto de esa salida 
     * es por venta,si si le resta el valor de ese menu al valor total de la salida. 
     * @param menu, el menu que se desea eliminar.
     * @return si pudó eliminar el menú o no.
     */
    public boolean eliminarMenu(Menu menu)
    {
        if(this.concept.getConcepto().equalsIgnoreCase("Venta"))
        {
            this.value=value-menu.getPrice();
        }
        return this.menus.remove(menu);
    }

    public LocalDateTime getFechaHora() {
        return createAt;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Supply> getSuppliesConsumed() {
        return suppliesConsumed;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public double getValue() {
        return value;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.createAt = fechaHora;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSuppliesConsumed(ArrayList<Supply> suppliesConsumed) {
        this.suppliesConsumed = suppliesConsumed;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
}
