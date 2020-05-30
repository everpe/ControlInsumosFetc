
package dialogos;

import clases.Category;
import clases.Provider;
import clases.Purchase;
import clases.Restaurant;
import clases.Supply;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import logicainterfaz.RenderTable;
import logicainterfaz.TableController;

/**
 *
 * @author ever
 */
public class DialogNewPurchase extends javax.swing.JDialog {

    //componentes graficos
    private final ImageIcon incoEdit = new ImageIcon("src/img/iconEdit2.png");
    private final ImageIcon incoDelete = new ImageIcon("src/img/iconDelete2.png");
    private JButton edit;
    private JButton delete;
    private final Font font1= new Font("Cambria", 0, 18);
    private final Font font2= new Font("Arial", 0, 17);
    
    //componentes Table
    private TableController tableController;
    private DefaultTableModel modelo;
    //Lógica
    private Restaurant restaurant;
    private ArrayList<Provider>providers;//combo providers
    private ArrayList<Supply>supplies;//combo insumos 
    private Purchase purchase;//Compra que se va a crear
    private double totalValuePurchase;//total de esa compra
    

    
    /**
     * Creates new form DialogPurchase
     */
    public DialogNewPurchase(java.awt.Frame parent, boolean modal,Restaurant restaurant) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        
        this.supplies=restaurant.getSupplies();
        this.providers=restaurant.getProviders();
        this.totalValuePurchase=0;
        this.restaurant=restaurant;
        this.purchase=new Purchase("0000", LocalDateTime.now(), "", 0, null, null);
        
        
        //      Pintando Combos
        this.paintCombSupplies(supplies);
        this.paintCombProviders(providers);
        this.tableController=new TableController(this.restaurant);
        this.lblValorTotal.setText(totalValuePurchase+"");
        
        
        
        this.createTable(purchase.getInsumos());
        

    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    
    
    
    

    /**
     * Pinta los insumos registrados en el sistema, disponibles para comprar más de esos.
     * @param supplies 
     */
    public void paintCombSupplies(ArrayList<Supply> supplies)
    {
        String aux = "";                
        int numCats = supplies.size();
        this.combSupplies.removeAllItems();
        Supply sup;
        this.combSupplies.addItem("----Seleccionar Insumo----");
        for(int i = 0; i < numCats; i++) 
        {
            sup = supplies.get(i);
            aux = sup.getId()+"-"+sup.getName();
            this.combSupplies.addItem(aux);
        }
    }
    
    /**
     * Pinta los proveedores con que cuenta el sistema necesarios para crear Compra.
     * @param providers 
     */
    public void paintCombProviders(ArrayList<Provider> providers)
    {
        String aux = "";                
        int numCats = providers.size();
        System.out.println("Dialog Purchase::"+numCats);
        this.combProviders.removeAllItems();
        Provider prov;
        this.combProviders.addItem("----Seleccionar Proveedor----");
        for(int i = 0; i < numCats; i++) 
        {
        
            prov = providers.get(i);
//            System.out.println("Desde DialogNewPurchase::"+prov.getName());
            aux = prov.getId()+"-"+prov.getName();
            this.combProviders.addItem(aux);
        }
    }
    
    
    /**
     * Da configuración y estilos a la Tabla y sus botones
     */
    public void initTable(){
        delete= new JButton("");
        delete.setIcon(incoDelete);
        delete.setName("btnDelete");
        delete.setBackground(new Color(217, 99, 126 ));
        
        
        table.setDefaultRenderer(Object.class, new RenderTable());
        //Define Columnas y desabilita edicion.
        this.modelo = new DefaultTableModel(
                        new Object[][]{{"","","","","","",""}},
                        new Object[] {"id","Nombre","Peso","U_Medida","Precio_Unitario",
                            "Categoria","Precio_Total","E"} 
                    ){
                        public boolean isCellEditable(int rowIndex, int vColIndex) {
                            return false;
                        }
                    };
        JTableHeader header = table.getTableHeader();
        header.setFont(font1);
        header.setForeground(new Color(255,255,255));
        header.setBackground(new Color(73, 93, 101 ));
        
        table.setFont(font2);
    }
    
    
    /**
     * Define el ancho de las columnas de la tabla
     */
    public void defineWidthColumnsTable(){
        System.out.println(this.table.getColumnCount()+"tamañooooooooooooo");
        this.table.getColumnModel().getColumn(0).setPreferredWidth(30);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(163);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(50);
        
        //unidad de medida
        this.table.getColumnModel().getColumn(3).setPreferredWidth(92);

        this.table.getColumnModel().getColumn(4).setPreferredWidth(130);
        this.table.getColumnModel().getColumn(5).setPreferredWidth(92);
        this.table.getColumnModel().getColumn(6).setPreferredWidth(135);
        this.table.getColumnModel().getColumn(7).setPreferredWidth(35); 
    }
    
    
    /**
     * Hace el barrido de la información contenida en una tabla.
     */
    public void clearDataFromTable()
    {        
        int a =modelo.getRowCount()-1;
        for(int i=a; i>=0; i--)
        {
            modelo.removeRow(i);
        }  
    }
    /**
     * borra los productos que tenga signada la compra.
     */
    public void clearSuppliesPurchase(){
        this.purchase.getInsumos().clear();
        System.out.println("DESPUES DE BARRER QUEDÓ"+purchase.getInsumos().size());
        this.clearDataFromTable();
    }
    
   
      
    /**
     * Metodo que crea y llena la Tabla con la información de los insumos de la 
     * compra actual.
     * @param insumos 
     */
    public void createTable(ArrayList<Supply>suppies)
    {
        this.initTable();
       
        //define modelo de tabla
        table.setModel(modelo);
        this.defineWidthColumnsTable();
        //Limpia datos viejos de la tabla
        this.clearDataFromTable();
        //Llena la Tabla con Providers del Restaurante.
        if(suppies.size()>0 && suppies!=null){
            try{
                for(int i=0;i<suppies.size();i++)
                {
                    Supply sup=suppies.get(i);
                    //valor de unidad x peso
                    double valuexWeight=sup.getPrice_purchase()*sup.getWeight();
                    
                    Object [] arreglo ={sup.getId(),sup.getName(),sup.getWeight()
                            ,sup.getCategory().getUnityMeasure(),sup.getPrice_purchase(),
                            sup.getCategory().getName(),valuexWeight,delete};    
                    modelo.addRow(arreglo);    
                }
                table.setModel(modelo);
                //Define el tamaño a las col del modelo
                
            }catch(Exception e){ 
                System.out.println("ERROR:pintando tabla proveedores"+e.getMessage());
            }
        }
        
    
    }
    
   
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEncabezado = new javax.swing.JPanel();
        txtEncabezado = new javax.swing.JLabel();
        txtSalir = new javax.swing.JLabel();
        panelFondo = new javax.swing.JPanel();
        combSupplies = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnAddPurchase = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnAddSupply = new javax.swing.JButton();
        btnClearTable = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        combProviders = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservation = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEncabezado.setBackground(new java.awt.Color(36, 52, 81));

        txtEncabezado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        txtEncabezado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccion.png"))); // NOI18N
        txtEncabezado.setText("Agregar Una Compra");

        txtSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconoCerrar.png"))); // NOI18N
        txtSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSalirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 476, Short.MAX_VALUE)
                .addComponent(txtSalir)
                .addContainerGap())
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtSalir)
        );

        getContentPane().add(panelEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 60));

        panelFondo.setBackground(new java.awt.Color(255, 255, 255));
        panelFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combSupplies.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        combSupplies.setForeground(new java.awt.Color(13, 68, 133));
        combSupplies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFondo.add(combSupplies, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 280, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(43, 37, 37));
        jLabel1.setText("Seleccionar Insumos De Compra:");
        panelFondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 30));

        btnAddPurchase.setBackground(new java.awt.Color(5, 165, 84));
        btnAddPurchase.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        btnAddPurchase.setForeground(new java.awt.Color(255, 255, 255));
        btnAddPurchase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compra.png"))); // NOI18N
        btnAddPurchase.setText("Agregar Compra");
        btnAddPurchase.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddPurchaseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddPurchaseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddPurchaseMouseExited(evt);
            }
        });
        panelFondo.add(btnAddPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 480, -1, 30));

        btnCancel.setBackground(new java.awt.Color(218, 69, 86));
        btnCancel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconBack.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelMouseExited(evt);
            }
        });
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        panelFondo.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 480, -1, 30));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Peso", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setMinWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        panelFondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 730, 210));

        btnAddSupply.setBackground(new java.awt.Color(24, 103, 156));
        btnAddSupply.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnAddSupply.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSupply.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar.png"))); // NOI18N
        btnAddSupply.setText("Agregar");
        btnAddSupply.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnAddSupply.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddSupplyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddSupplyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddSupplyMouseExited(evt);
            }
        });
        panelFondo.add(btnAddSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 20, -1, -1));

        btnClearTable.setBackground(new java.awt.Color(255, 153, 153));
        btnClearTable.setForeground(new java.awt.Color(51, 51, 51));
        btnClearTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconDelete2.png"))); // NOI18N
        btnClearTable.setText("Vaciar Tabla");
        btnClearTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearTableMouseClicked(evt);
            }
        });
        panelFondo.add(btnClearTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, 130, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alimentos.png"))); // NOI18N
        panelFondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        combProviders.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combProviders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combProvidersActionPerformed(evt);
            }
        });
        panelFondo.add(combProviders, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 220, 30));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/proveedores.png"))); // NOI18N
        jLabel3.setText("Proveedor:");
        panelFondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        jScrollPane2.setViewportView(txtObservation);

        panelFondo.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 350, 60));

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(121, 118, 118));
        jLabel4.setText("Observaciones o comentarios De La Compra");
        panelFondo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, 330, 20));

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 20)); // NOI18N
        jLabel5.setText("$Valor Total");
        panelFondo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 420, 140, 20));

        lblValorTotal.setBackground(new java.awt.Color(153, 153, 153));
        lblValorTotal.setFont(new java.awt.Font("Consolas", 1, 22)); // NOI18N
        lblValorTotal.setForeground(new java.awt.Color(0, 0, 0));
        lblValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblValorTotal.setText("Precio");
        lblValorTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelFondo.add(lblValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 190, 40));

        getContentPane().add(panelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 61, 830, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_txtSalirMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnAddSupplyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSupplyMouseEntered
       this.btnAddSupply.setBackground(new Color(0,0,128));
    }//GEN-LAST:event_btnAddSupplyMouseEntered

    private void btnAddSupplyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSupplyMouseExited
        this.btnAddSupply.setBackground(new Color(50,113,166));
    }//GEN-LAST:event_btnAddSupplyMouseExited

    
    /**
     * Event Click cuando se quiere agregar un nuevo producto, al arreglo de productos 
     * que tendrá la compra.
     * @param evt 
     */
    private void btnAddSupplyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSupplyMouseClicked
        //obtengo el insumo seleccionado
        Supply sup=this.tableController.getSupplyFromComb(combSupplies);
        
        if(sup!=null){
            //categoria del insumo seleccionado del restaurante
            Category cat=sup.getCategory();
            //crea uno nuevo para que no modifique el insumo que ya tiene el restaurante.
            Supply supply=new Supply(sup.getId(), sup.getName(), 1000, cat);
            
            //Dialogo de peso y precio
            DialogWeightPriceSupply diaWeight= new DialogWeightPriceSupply(null, true,supply.getName(),supply.getCategory().getUnityMeasure());
            
            
            double priceSugerido=this.definePriceDialog(supply);
            double weightSugerido=this.defineWeightDialog(supply);
            diaWeight.setPrice(priceSugerido);
            diaWeight.setWeight(weightSugerido);
            diaWeight.setVisible(true);
            
            double newWeight=diaWeight.getWeight();
            double price_purchase=diaWeight.getPrice();
           
            if(newWeight>0&&price_purchase>0){
                supply.setWeight(newWeight);
                supply.setPrice_purchase(price_purchase);

                this.purchase.agregarInsumo(supply);                
                this.createTable(this.purchase.getInsumos());
                
                this.combSupplies.setSelectedItem("----Seleccionar Insumo----");
                //suma valores unitarios de esa columna de la tabla.
                this.setTotalValue();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Debe Seleccionar Un Insumo De La Lista");
        }
        
        
    }//GEN-LAST:event_btnAddSupplyMouseClicked

    /**
     * Metodo para saber si un producto que se seleccionó ya esta el la lista de compra;
     * y si lo está entonces al dialogo de precio y peso le modifique el precio sugerido.
     * @param sup
     * @return 
     */
    public double definePriceDialog(Supply sup){
        double valorActual=0;
        for (int i = 0; i < this.purchase.getInsumos().size(); i++) {
            Supply aux=purchase.getInsumos().get(i);
            if(sup.getId().equals(aux.getId())){
                return aux.getPrice_purchase();
            }   
        }
        return valorActual;
    }
    
     /**
     * Metodo para saber si un producto que se seleccionó ya esta el la lista de compra;
     * y si lo está entonces al dialogo de precio y peso le modifique el peso sugerido.
     * @param sup
     * @return 
     */
    public double defineWeightDialog(Supply sup){
        double valorActual=0;
        for (int i = 0; i < this.purchase.getInsumos().size(); i++) {
            Supply aux=purchase.getInsumos().get(i);
            if(sup.getId().equals(aux.getId())){
                return aux.getWeight();
            }   
        }
        return valorActual;
    }
    /**
     * Define el valor total de la compra sumando lalos valores de la columna 6 
     * de la table y  lo pinta.
     */
    public void setTotalValue(){
        this.totalValuePurchase=0;
        if(purchase.getInsumos().size()>0){
            for (int i = 0; i < purchase.getInsumos().size(); i++) {
            this.totalValuePurchase+=Double.parseDouble(table.getModel().getValueAt(i, 6).toString());
            }
        }
        this.lblValorTotal.setText(this.totalValuePurchase+"");
    }
    /**
     * Event para al metodo que vacia la tabla.
     * @param evt 
     */
    private void btnClearTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearTableMouseClicked
//        this.clearDataFromTable();
        this.clearSuppliesPurchase();
        this.setTotalValue();
    }//GEN-LAST:event_btnClearTableMouseClicked

    
    /**
     * Crea la compra y la define para enviarla al panel principal.
     * @param evt 
     */
    private void btnAddPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPurchaseMouseClicked
        Provider prov=this.tableController.getProviderFromComb(combProviders);
        ArrayList<Supply>supplies=purchase.getInsumos();
        
        if(prov!=null&&purchase.getInsumos().size()>0){
//            this.purchase= new Purchase("1000", LocalDateTime.now(), this.txtObservation.getText(),
//                 
            this.purchase.setObservation(this.txtObservation.getText());
            this.purchase.setProvider(prov);
            this.purchase.setValue(totalValuePurchase);
            System.out.println("Hay en compra insumos::"+purchase.getInsumos().size());
            this.dispose();
        }else{   
            JOptionPane.showMessageDialog(this, "Debe Seleccionar un Proveedor y Agregar Productos ");
        }
        
    }//GEN-LAST:event_btnAddPurchaseMouseClicked
 
    
    private void combProvidersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combProvidersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combProvidersActionPerformed

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        this.btnCancel.setBackground(new Color(238, 15, 52));
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        this.btnCancel.setBackground(new Color(204, 91, 110));
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnAddPurchaseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPurchaseMouseEntered
        this.btnAddPurchase.setBackground(new Color( 12, 139, 50));
    }//GEN-LAST:event_btnAddPurchaseMouseEntered

    private void btnAddPurchaseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPurchaseMouseExited
        this.btnAddPurchase.setBackground(new Color(12, 184, 85    ));
    }//GEN-LAST:event_btnAddPurchaseMouseExited

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    
    /**
     * Evento para eliminar un insumo de la tabla y de la compra,
     * @param evt 
     */
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int col=table.getColumnModel().getColumnIndexAtX(evt.getX());
        int row=evt.getY()/table.getRowHeight();
        
        //Verifica que lo que clikeo fué un boton
        if(row < table.getRowCount()  && row >= 0 && col < table.getColumnCount()
                && col >= 0){
            Object value=table.getValueAt(row, col);
            if(value instanceof JButton){
                ((JButton)value).doClick();
                JButton btn= (JButton)value;
                
               if(btn.getName().equals("btnDelete")){
                    this.deleteSupply();
                }
            }
//            
            
        }
    }//GEN-LAST:event_tableMouseClicked

    
    /**
     * Elimina el insumo de la compra y vuelve a pintar la table.
     */
    private void deleteSupply(){
        Supply sup = this.tableController.getSupplyFromTableSupplies(this.table);
//        System.out.println("LLega desde Table controllerr"+sup.getName()+sup.getId());
        if(sup == null)
            JOptionPane.showMessageDialog(this, "No Se Seleccionó Ningún Proveedor");
        else
        {            
            int opc = JOptionPane.showConfirmDialog(null, "Realmente Desea Eliminar El Insumo: "+sup.getName().toUpperCase(),
                    "Eliminar Insumo"+sup.getName().toUpperCase(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //0 para si || 1 para no
            if (opc == 0)
            {
                boolean ban=this.deleteSupplyFromPurchase(sup);
                //Si se elimino el proveedor de la base de datos.
                if(ban)
                {
                    JOptionPane.showMessageDialog(this, "Insumo " + " Eliminado Correctamente");
                    this.createTable(this.purchase.getInsumos());
                    this.setTotalValue();
//                    this.pintor.paintTableProviders(this.tequilazo.getProveedores(), this.modeloListaProveedores);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "No Se Pudó Eliminar El Insumo de la compra");
                }
            }            
        }
    }
    
    /**
     * Elimina un insumo del arreglo de insumos que tiene la compra.
     * @param supply
     * @return 
     */
    public boolean deleteSupplyFromPurchase(Supply supply){
        for (int i = 0; i < this.purchase.getInsumos().size(); i++) {
            Supply sup= purchase.getInsumos().get(i);
            //si el insumo que llega entá en el arreglo 
            if(supply.getId().equals(sup.getId()))
            {
                this.purchase.getInsumos().remove(i);
                return true;
            }
            
        }
        return false;
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPurchase;
    private javax.swing.JButton btnAddSupply;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClearTable;
    private javax.swing.JComboBox<String> combProviders;
    private javax.swing.JComboBox<String> combSupplies;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable table;
    private javax.swing.JLabel txtEncabezado;
    private javax.swing.JTextPane txtObservation;
    private javax.swing.JLabel txtSalir;
    // End of variables declaration//GEN-END:variables
}
