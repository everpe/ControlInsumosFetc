
package clases;

/**
 *Categoria que va a tener cada insumo del Restaurante.
 * @author ever
 */
public class Category
{
    int id;
    String name;
    String unityMeasure;//kg,lb

    public Category(int id, String nombre, String unidadMedida) {
        this.id = id;
        this.name = nombre;
        this.unityMeasure = unidadMedida;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUnityMeasure() {
        return unityMeasure;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnityMeasure(String unityMeasure) {
        this.unityMeasure = unityMeasure;
    }
     
    
    
}
