
package clases;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *Compra de insumos que realiza el restaurante.
 * @author ever
 */
public class Purchase 
{
    private String id;
    private LocalDateTime createAt;
    private String observation;
    double value;
    //Puede tener muchos proveedores una misma compra
    private Provider provider;
    private ArrayList<Supply> insumos;

    public Purchase(String id, LocalDateTime fechaHora, String observacion, double valor, Provider provider, ArrayList<Supply> supplies) {
        this.id = id;
        this.createAt = fechaHora;
        this.observation = observacion;
        this.value = valor;
        this.provider = provider;
        
        this.insumos = new ArrayList<>();

        
        
    }

    /**
     * Agrega un insumo a la compra comprabando si existe ese id de insumo 
     * y si lo está solo aumenta el peso sino lo añade al Array.
     * @param insumo
     * @return 
     */
    public boolean agregarInsumo(Supply insumo)
    {
        return this.checkSupply(insumo);
    }
    
      /**
     * Verifica que el insumo que se agrega no esté ya en la compra, 
     * si esta entonces le modifica el peso y precio con lo que escriban en el Dialog.
     */
    public boolean checkSupply(Supply supply){
        boolean bandera=false;
        for (int i = 0; i < this.insumos.size(); i++) {
           Supply sup=insumos.get(i);
           if(supply.getId().equals(sup.getId())){
 
                sup.setWeight(supply.getWeight());
               //y le modifica el precio por el que haya colocado en el dialoguito.
               sup.setPrice_purchase(supply.getPrice_purchase());
               bandera=true;
           }
        }
        if(bandera==false){
            this.insumos.add(supply);
            System.out.println("Si agrega");
        }
        return bandera;
    }
    

    
    public boolean eliminar(Supply insumo)
    {
        for (int i = 0; i < this.insumos.size(); i++)
        {
            Supply insu=insumos.get(i);
            if(insu.getId().equalsIgnoreCase(insumo.getId()))//si tienen el mismo id
            {
                this.insumos.remove(i);
                return true;
            }   
        }
        return false;
    }
    
    
    
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Supply> getInsumos() {
        return insumos;
    }

    public String getObservation() {
        return observation;
    }

   
    public double getValue() {
        return value;
    }

    public Provider getProvider() {
        return provider;
    }
    

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
        
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInsumos(ArrayList<Supply> insumos) {
        this.insumos = insumos;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


    public void setValue(double value) {
        this.value = value;
    }
    
    
    
    
}
