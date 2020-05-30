
package clases;

/**
 * FECHA ==> 2020-05-12.
 * Proveedores de productos del negocio.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Provider {
    

    /**
     * Id unico autoincremental con el cual se identifica un proveedor.
     */
    private String id;
    
    /**
     * Nombre con el que se identifica un proveedor.
     */
    private String name;
    
    /**
     * NIT del proveedor.
     */
    private String nit;
    
    /**
     * Número teléfonico del proveedor.
     */
    private String cellphone;

    private String email;
    private String direction;
    
    /**
     * Construye un proveedor con todos sus datos.
     * @param codigo Código con el cual se identifica el proveedor en el negocio.
     * @param nombre Nombre que identifica al proveedor.
     * @param Nit Nit con el cuál esta registrado el proveedor ante el gobierno.
     * @param telefono Número de contacto del proveedor.
     */
    public Provider(String id, String name,String nit,String cellphone,String direction,String email) 
    {
      this.nit=nit;
      this.id=id;
      this.name=name;
      this.cellphone=cellphone;
      this.direction=direction;
      this.email=email;
    } 

    public String getId() {
        return id;
    }

    public String getNit() {
        return nit;
    }

    public String getName() {
        return name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getDirection() {
        return direction;
    }

    public String getEmail() {
        return email;
    }

    
    
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}

