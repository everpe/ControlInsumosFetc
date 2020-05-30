
package clases;

/**
 *Representa un insumoi que se maneja en el sistema(Recordar ElInsumo Compredo es
 * el que tiene precio de compra)
 * @author ever
 */
public class Supply {
    
    private String id;
    private String name;
    private double weight;
    private Category category;
    
    private double price_purchase;

    public Supply(String id, String nombre,double peso,Category categoria) {
        this.id = id;
        this.name = nombre;
        this.category = categoria;
        this.weight = peso;
    }

    public double getPrice_purchase() {
        return price_purchase;
    }

    public void setPrice_purchase(double price_purchase) {
        this.price_purchase = price_purchase;
    }
    
    

    public Category getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    
   

    
    
}
