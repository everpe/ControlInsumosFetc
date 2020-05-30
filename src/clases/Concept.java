
package clases;

/**
 *
 * @author ever
 */
public class Concept {
    
    private int id;
    //los conceptos pueden ser Venta,gasto para estudiantes,para empleados etc..
    private String concepto;

    public Concept(int id, String concepto) {
        this.id = id;
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
}
