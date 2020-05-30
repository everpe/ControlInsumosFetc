package bd;

/**
 * FECHA ==> 2019-06-01.
 * Posee todos los CRUD con la base de datos.
 * @author Ever Peña Ballesteros.
 * @version 1.0 .
 */
public class BaseDatos 
{
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;
    
    
    
    /**
     * Crud para la entidad Provider
     */
    private CrudProvider crudProvider ;

    private CrudCategory crudCategory;
    private CrudSupply crudSupply;
    
    private CrudPurchase crudPurchase;
    private CrudSupplyPurchase crudSupplyPurchase;
    private CrudMenu crudMenu;
    private CrudSupplyMenu crudSupplyMenu;
    /**
     * Prepara todos los cruds con la base de datos.
     * @param con Conexión con la base de datos.
     */
    public BaseDatos(Conexion con)
    {
        this.conexion = con;
        this.crudProvider=new CrudProvider(con);
        this.crudCategory= new CrudCategory(con);
        this.crudSupply= new CrudSupply(con);
        this.crudPurchase= new CrudPurchase(con);
        this.crudSupplyPurchase=new CrudSupplyPurchase(con);
        this.crudMenu= new CrudMenu(con);
        this.crudSupplyMenu=new CrudSupplyMenu(con);
    }
    
    /**
     * Obtiene la conexión con la base de datos del sistema.
     * @return Conexión con la base de datos.
     */
    public Conexion getConexion() 
    {
        return conexion;
    }   

    public CrudProvider getCrudProvider() {
        return this.crudProvider;
    }

    public CrudCategory getCrudCategory() {
        return crudCategory;
    }

    public CrudMenu getCrudMenu() {
        return crudMenu;
    }
    

    public void setCrudSupply(CrudSupply crudSupply) {
        this.crudSupply = crudSupply;
    }

    public CrudSupply getCrudSupply() {
        return crudSupply;
    }

    public CrudPurchase getCrudPurchase() {
        return crudPurchase;
    }

    public CrudSupplyPurchase getCrudSupplyPurchase() {
        return crudSupplyPurchase;
    }

    public CrudSupplyMenu getCrudSupplyMenu() {
        return crudSupplyMenu;
    }
    
    
    
    
     
        
}
