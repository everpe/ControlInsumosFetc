
package principal;

import bd.BaseDatos;
import bd.Conexion;
import bd.CrudProvider;
import clases.Category;
import clases.Menu;
import clases.Provider;
import clases.Purchase;
import clases.Supply;
import clases.Restaurant;
import interfaz.Login;
import java.util.ArrayList;

/**
 *
 * @author ever
 */
public class MainClases {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion conexion= new Conexion();
        BaseDatos bd=new BaseDatos(conexion);
        CrudProvider crudProvider= bd.getCrudProvider();
      
//        Provider old=crudProvider.showProvider("0");
//        System.out.println("Show El provedor es: "+old.getName());
        ArrayList<Provider>providers=crudProvider.getAllProviders();

        ArrayList<Supply>supplies=bd.getCrudSupply().getAllSupplies();
        ArrayList<Purchase>purchases=bd.getCrudPurchase().getAllPurchases();
        ArrayList<Menu>menus=bd.getCrudMenu().getAllMenus();
        
        
        Restaurant restaurante=new Restaurant(bd,supplies, purchases, null,providers,menus);
//        
        Login login = new Login(restaurante);
        login.setVisible(true);
        
        
        
        
//        
//        Provider now =new Provider("0", "Justo&Bueno", "0000000", "3115458787","just&good@mail.com","Manizales Caldas");
//        crudProvider.updateProvider(old, now);
//            
//        Categoria cat1= new Categoria("0001", "Carne", "Kg");
//        Categoria cat2= new Categoria("0002", "Vegetales", "Kg");
//        Categoria cat3= new Categoria("0003", "Granos", "Kg");
//         
//        Insumo insumo1 = new Insumo("0001","Arroz",2000,1.0,cat3);
//        Insumo insumo2 = new Insumo("0002","Pollo",5000,1.0,cat1);
//        Insumo insumo3 = new Insumo("0003","Carne",8000,1.0,cat1);
//        
//        Proveedor proveedor= new Proveedor("0001", "Tiendas Ara", "Salamina", "ara@gmail.com", "3-8888801");
//        Proveedor proveedor2= new Proveedor("0002", "Tiendas D1", "Salamina C", "D1@gmail.com", "4-682390001");
//        
//        ArrayList<Insumo>insumos= new ArrayList<>();
//        insumos.add(insumo1);
//        insumos.add(insumo2);
////        insumos.add(insumo3);
//        
//        ArrayList<Insumo>insumos22= new ArrayList<>();
//        insumos22.add(insumo3);
//
//        
//        
//        
//        
//        ArrayList<Insumo>insumosMenu= new ArrayList<>();
//        insumosMenu.add(insumo1);
//        insumosMenu.add(insumo2);
//        ArrayList<Insumo>insumosMenu2= new ArrayList<>();
//        insumosMenu2.add(insumo3);
//        insumosMenu2.add(insumo2);
//       
//        Menu menu= new Menu("0001", 10000, "Arroz con Pollo", "Ejecutivo", insumosMenu);
//        Menu menu2= new Menu("0001", 20000, "Carne A la Plancha", "Carta", insumosMenu2);
//        
//    
//        
//     
//        
//        
//        
//        Salida salida= new Salida("0001", LocalDateTime.now(),30000 , "Venta",null);
////        Salida salida2= new Salida("0002", LocalDateTime.now(),30000 , "Venta",menus);
//        salida.agregarMenu(menu);
////        salida.agregarMenu(menu);
//
//        restaurante.agregarSalida(salida);
//        restaurante.agregarSalida(salida);
//        
////        for (int i = 0; i < restaurante.getSalidas().size(); i++) 
////        {
////            Salida salid=restaurante.getSalidas().get(i);
////            ArrayList<Insumo>isuss=salid.getInsumosGastados();
////            System.out.println("La salida: "+salid.getId()+"- "+salid.getInsumosGastados().size());
////            for (int j = 0; j < isuss.size(); j++)
////            {
////                Insumo in=isuss.get(j);
////                System.out.println(in.getNombre()+": "+in.getPeso());
////            }
////            
////        }
//        
//        
//        
//        
//        
//        
//        
//        Compra compra = new Compra("0001", LocalDateTime.now(), "ninguna", 15000, proveedor, insumos);//Arroz,pollo,carne
////        Compra compra2 = new Compra("0002", LocalDateTime.now(), "ninguna", 8000, proveedor2, insumos22);//carne
////        Insumo arroz = new Insumo("0001","Arroz",2000,5.0,cat3);
////        compra.agregarInsumo(arroz);//Arroz
////        compra.agregarInsumo(arroz);
////        compra.agregarInsumo(arroz);
////        compra.eliminar(arroz);
//
//////    
//  
//    
////        
//        restaurante.addPurchase(compra);
//        restaurante.addPurchase(compra);
////        restaurante.addPurchase(compra2);
////        restaurante.addPurchase(compra2);
//        
//
//
//
//// MUESTRA INSUMOS POR COMPRA DEL RESTAURANTE
////        for (int i = 0; i < restaurante.getCompras().size(); i++) {
////            Compra comp1=restaurante.getCompras().get(i);
////            System.out.println("La compra "+ comp1.getId() +": "+comp1.getInsumos().size());
////            ArrayList<Insumo>insumosComp1=comp1.getInsumos();
////            for (int j = 0; j < insumosComp1.size(); j++)
////            {
////                Insumo in=insumosComp1.get(j);
////                System.out.println(in.getNombre()+"---"+in.getPeso());   
////            }
////        }
//        
//        
////        Muestra insumos totales restaurante
//        for (int i = 0; i < restaurante.getInsumos().size(); i++) {
//            Insumo insu =restaurante.getInsumos().get(i);
//            System.out.println(insu.getNombre()+insu.getPeso());
//            
//        }
//        
        
    }
    
}
