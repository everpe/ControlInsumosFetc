
package clases;

import bd.BaseDatos;
import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author ever
 */
public class Restaurant 
{
    //insumos generales que existen en el restaurante.
    private ArrayList<Supply>supplies=new ArrayList<>();
    private ArrayList<Provider>providers;
    private ArrayList<Purchase>purchases=new ArrayList<>();
    private ArrayList<Output>outputs;
    private ArrayList<Menu>menus;
    
    
    private BaseDatos bd;
    
    public Restaurant(BaseDatos bd,ArrayList<Supply>insumos,ArrayList<Purchase> compras,
                        ArrayList<Output> salidas,ArrayList<Provider>providers,
                        ArrayList<Menu>menus) {
        this.bd=bd;
        this.supplies = insumos;
        this.purchases = compras;
        this.menus=menus;
        this.outputs = new ArrayList<>();
        
        this.providers=providers;
        System.out.println("Proveedores llegaron :"+providers.size());
        System.out.println("Spllies llegaron : "+insumos.size());
    }
    
    
    
    
    /**
     * Crea una compra en la bd y la agrega al arreglo del restaurante.
     * @param purchase
     * @return 
     */
    public boolean addPurchase(Purchase purchase){
        ArrayList<Supply>insumosCompra=purchase.getInsumos();
        
        boolean bandera=this.bd.getCrudPurchase().createPurchase(purchase);
        
        //le agrego el id para poder crear la entidad debil suppliesPurchase(insumosComprados)
        purchase.setId((this.getPurchases().size()+1)+"");
        if(bandera){
            //cada que crea una compra crea los insumos comprados.(débil)
            this.bd.getCrudSupplyPurchase().insertElementsFromPurchase(purchase);
            //aumenta la cantidad de cada insumo del restaurante que incluye la compra.
            this.addSuppliesPurchase(insumosCompra);
        }
        this.updateDataBase();  
        return bandera;
    }

    /**
     * Recibe los insumos de una compra y le aumenta el peso de esos insumos que 
     * existen en la B.D con el peso que tienen los nuevos Insumos de la compra.
     * y le suma el peso a ese insumo;.
     * @param insumo 
     * @return  
     */
    public boolean addSuppliesPurchase(ArrayList<Supply> suppliesPurchase)
    {
        //le envío todos los productos que ya están en el restaurante y los de la compra.
        boolean bandera=this.bd.getCrudSupplyPurchase().modifySuppliesPurchaseRestaurant
        (this.getSupplies(), suppliesPurchase);
        return bandera;
    }
    
    
    /**
     * Recibo un menu de la interfaz, lo creo en la bd y creo la entidad débil 
     * SupplyMenu de insumos destinados para cada menu que se crea.
     * @param menu
     * @return 
     */
    public boolean addMenu(Menu menu){
             
        boolean bandera=this.bd.getCrudMenu().createMenu(menu);
        
        //le agrego el id para poder crear la entidad debil suppliesMenu(insumosMenu)
        menu.setId((this.getMenus().size()+1)+"");
        if(bandera){
            this.bd.getCrudSupplyMenu().insertElementsFromMenu(menu);
        }
        this.menus.add(menu);
        this.updateDataBase();  
        return bandera;
    }
    
    
    
    
    
    /**
     * Refresca la base de datos cuando se hace un CRUD de algun elemento
     * menor perteneciente al sistema.
     */
    public void updateDataBase()
    {
        this.providers = this.bd.getCrudProvider().getAllProviders();
        this.supplies=this.getBd().getCrudSupply().getAllSupplies();
        this.purchases=this.getBd().getCrudPurchase().getAllPurchases();
        this.menus=this.getBd().getCrudMenu().getAllMenus();
//        this.categorias = this.bd.getCrudCategorias().obtenerCategorias();
//        this.marcas = this.bd.getCrudMarcas().obtenerMarcas();
//        this.unidadesMedidas = this.bd.getCrudUnidades().obtenerUnidadesMedidas();
    } 
    
    
    
      /**
     * Agrega la salida al restaurante y resta el peso de insumos existententes
     * @param salida 
     */
    public void agregarSalida(Output salida)
    {
        ArrayList<Supply>insumosSalida=salida.getSuppliesConsumed();
        for (int i = 0; i < insumosSalida.size(); i++)
        {
            Supply insumoSalida=insumosSalida.get(i);
            this.restarInsumoSalida(insumoSalida.getWeight(), insumoSalida.getId());
        }
        this.outputs.add(salida);
        
    }
    public boolean restarInsumoSalida(double cantidad,String codigo)
    {
        for (int i = 0; i < this.supplies.size(); i++)
        {
            Supply ins=supplies.get(i);
            if(ins.getId().equalsIgnoreCase(codigo))
            {
                supplies.get(i).setWeight(supplies.get(i).getWeight()-cantidad);
                return true;
            }
        }
        return false;
    }
    
    
    
 
    
    
    /**
     * Obtiene un Insumo del arreglo de insumos del restaurante que está previamente 
     * lleno con los insumos de la bd.
     * @param id_supply; el id de insumo que se desea obener
     * @return el insumo que del arreglo
     */
    public Supply getSupplyById(String id_supply){
        Supply sup=null;
        for (int i = 0; i < supplies.size(); i++) {
            sup=supplies.get(i);
            if(sup.getId().equals(id_supply))
                return sup;
        }
        return sup;
    }
    
    public Menu getMenuById(String id_menu){
        if(id_menu!=null&&!id_menu.equals("")){
             Menu menu=null;
            for (int i = 0; i < menus.size(); i++) {
                menu=menus.get(i);
                if(menu.getId().equals(id_menu))
                    return menu;
            }
        }
       
     return null;
    }
    
    /**
     * Obtiene un proveedor de la lista de proveedores que se obtuvó de la bd en "Main".
     * @param id_provider
     * @return 
     */
    public Provider getProviderById(String id_provider){
        Provider prov=null;
        for (int i = 0; i < providers.size(); i++) {
            prov=providers.get(i);
            if(prov.getId().equals(id_provider))
                return prov;
        }
        return prov;
    }
    
    
    
    /**
     * Obtine una compra por su id dentro del arreglo.
     * @param id_purchase
     * @return 
     */
    public Purchase getPurchaseById(String id_purchase){
        Purchase pur=null;
        for (int i = 0; i < this.purchases.size(); i++) {
            pur=purchases.get(i);
            if(pur.getId().equals(id_purchase))
                return pur;
        }
        return pur;
    }
    
    
    public boolean deleteMenu(Menu menu){
        return this.bd.getCrudMenu().deleteMenu(menu.getId());
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public ArrayList<Supply> getSupplies() {
        return supplies;
    }

    public ArrayList<Output> getOutputs() {
        return outputs;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }
    

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void setSupplies(ArrayList<Supply> supplies) {
        this.supplies = supplies;
    }

    public void setOutputs(ArrayList<Output> outputs) {
        this.outputs = outputs;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<Provider> providers) {
        this.providers = providers;
    }

    public void setBd(BaseDatos bd) {
        this.bd = bd;
    }

    public BaseDatos getBd() {
        return bd;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   //LOGIC CLASES OLD.     
//    /**
//     * Agrega la salida al restaurante y resta el peso de insumos existentent
//     * @return 
//    public boolean agregarInsumo(Insumo insumo)
//    {
//        for (int i = 0; i < insumos.size(); i++) {
//            if(insumos.get(i).getId().equalsIgnoreCase(insumo.getId()))
//            {
//               insumos.get(i).setPeso(insumos.get(i).getPeso()+insumo.getPeso());
//               return true;
//            }
//        }
//        insumos.add(insumo);
//        return false;  
//    }
//    
    
   

//    public ArrayList<Insumo> sumarInsumosMenusSalida(ArrayList<Menu>menus)
//    {
//        boolean bandera=false;
//        ArrayList<Insumo>insumosSalida=menus.get(0).getInsumos();
//        
//        System.out.println("inicia con:"+insumosSalida.size());
//        for (int i = 0; i < menus.size(); i++) {
//            Menu menu1=menus.get(i);
//            ArrayList<Insumo>insumosMenu=menu1.getInsumos();//insumos de cada menu
//        
//            for (int j = 0; j < insumosMenu.size(); j++)
//            {
//                Insumo insumoMenu=insumosMenu.get(j);
//                if(insumosSalida.size()> 0)
//                {
//                    for (int k = 0; k < insumosSalida.size(); k++) {
//                        
//                        if(insumoMenu.getId().equalsIgnoreCase(insumosSalida.get(k).getId()))
//                        {
//                            insumosSalida.get(k).setPeso(insumosSalida.get(k).getPeso()+insumoMenu.getPeso());
//                        } 
//                        else if(){
//                            insumosSalida.add(insumoMenu);
//                        }
//                    }
//                }else{//Caso Base
//                    insumosSalida.add(insumoMenu);
//                }
//            }
//        }
//        return insumosSalida;
//    }

    
    
    
}
