
package dialogos;

import clases.Category;
import clases.Menu;
import clases.Provider;
import clases.Purchase;
import clases.Restaurant;
import clases.Supply;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
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
public class DialogNewMenu extends javax.swing.JDialog {

    //componentes graficos
    private final ImageIcon incoDelete = new ImageIcon("src/img/iconDelete2.png");
    private JButton delete;
    private final Font font1= new Font("Cambria", 0, 18);
    private final Font font2= new Font("Arial", 0, 17);
    
    //componentes Table
    private TableController tableController;
    private DefaultTableModel modelo;
    //Lógica
    private Restaurant restaurant;
    private ArrayList<Supply>supplies;//combo insumos 
    private Menu menu;//Compra que se va a crear

    

    
    /**
     * Creates new form DialogPurchase
     */
    public DialogNewMenu(java.awt.Frame parent, boolean modal,Restaurant restaurant) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        
        this.supplies=restaurant.getSupplies();
        this.restaurant=restaurant;
        this.menu=new Menu("0000", 0.0, "", "",null);
        
        
        //      Pintando Combos
        this.paintCombSupplies(supplies);
        this.paintCombTypesMenu();
        this.tableController=new TableController(this.restaurant);
   
//        this.initTable();
        this.createTable(menu.getSupplies());
        

    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
     * Pinta los tipos de Menu.
     * @param providers 
     */
    public void paintCombTypesMenu()
    {
        this.combTiposMenu.removeAllItems();
        this.combTiposMenu.addItem("----Seleccionar Tipo Menú----");
        this.combTiposMenu.addItem("Ejecutivo");
        this.combTiposMenu.addItem("A la Carta");
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
                        new Object[] {"id","Nombre","Peso","U_Medida",
                            "Categoria","E"} 
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
        this.table.getColumnModel().getColumn(0).setPreferredWidth(30);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(163);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(50);
        //unidad de medida
        this.table.getColumnModel().getColumn(3).setPreferredWidth(92);
        this.table.getColumnModel().getColumn(4).setPreferredWidth(130);
        this.table.getColumnModel().getColumn(5).setPreferredWidth(92);
     
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
     * borra los productos que tenga asignado el menú.
     */
    public void clearSuppliesMenu(){
        this.menu.getSupplies().clear();
        System.out.println("DESPUES DE BARRER QUEDÓ"+menu.getSupplies().size());
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
            
                    
                    Object [] arreglo ={sup.getId(),sup.getName(),sup.getWeight()
                            ,sup.getCategory().getUnityMeasure(),
                            sup.getCategory().getName(),delete};    
                    modelo.addRow(arreglo);    
                }
                table.setModel(modelo);
                //Define el tamaño a las col del modelo
                
            }catch(Exception e){ 
                System.out.println("ERROR:pintando tabla proveedores"+e.getMessage());
            }
        }
    
    }
    
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelEncabezado = new javax.swing.JPanel();
        txtEncabezado = new javax.swing.JLabel();
        txtSalir = new javax.swing.JLabel();
        panelFondo = new javax.swing.JPanel();
        combSupplies = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnAddMenu = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnAddSupply = new javax.swing.JButton();
        btnClearTable = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPriceMenu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        combTiposMenu = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtNameMenu = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(752, 550));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelEncabezado.setBackground(new java.awt.Color(36, 52, 81));

        txtEncabezado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        txtEncabezado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccion.png"))); // NOI18N
        txtEncabezado.setText("Agregar Nuevo Menú");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 382, Short.MAX_VALUE)
                .addComponent(txtSalir)
                .addGap(20, 20, 20))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(txtSalir)
        );

        getContentPane().add(panelEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 60));

        panelFondo.setBackground(new java.awt.Color(255, 255, 255));
        panelFondo.setPreferredSize(new java.awt.Dimension(690, 350));
        panelFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combSupplies.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        combSupplies.setForeground(new java.awt.Color(13, 68, 133));
        combSupplies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFondo.add(combSupplies, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 240, 30));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(43, 37, 37));
        jLabel1.setText("Seleccionar Insumos Para el Menú:");
        panelFondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 30));

        btnAddMenu.setBackground(new java.awt.Color(5, 165, 84));
        btnAddMenu.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        btnAddMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnAddMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/compra.png"))); // NOI18N
        btnAddMenu.setText("Agregar Menú");
        btnAddMenu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAddMenuMouseExited(evt);
            }
        });
        btnAddMenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnAddMenuKeyReleased(evt);
            }
        });
        panelFondo.add(btnAddMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, -1, 30));

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
        panelFondo.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, -1, 30));

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

        panelFondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 660, 210));

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
        panelFondo.add(btnAddSupply, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        btnClearTable.setBackground(new java.awt.Color(255, 153, 153));
        btnClearTable.setForeground(new java.awt.Color(51, 51, 51));
        btnClearTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconDelete2.png"))); // NOI18N
        btnClearTable.setText("Vaciar");
        btnClearTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearTableMouseClicked(evt);
            }
        });
        panelFondo.add(btnClearTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 100, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alimentos.png"))); // NOI18N
        panelFondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("Nombre Del Menú:");
        panelFondo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        txtPriceMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtPriceMenu.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelFondo.add(txtPriceMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, 190, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Tipo de Menu:");
        panelFondo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        combTiposMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFondo.add(combTiposMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 190, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("$Precio Venta:");
        panelFondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, -1, -1));

        txtNameMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        txtNameMenu.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelFondo.add(txtNameMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 190, -1));

        getContentPane().add(panelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 61, 760, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_txtSalirMouseClicked

    /**
     * Event para al metodo que vacia la tabla.
     * @param evt 
     */
    private void btnClearTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearTableMouseClicked
        this.clearSuppliesMenu();
    }//GEN-LAST:event_btnClearTableMouseClicked

    private void btnAddSupplyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSupplyMouseExited
        this.btnAddSupply.setBackground(new Color(50,113,166));
    }//GEN-LAST:event_btnAddSupplyMouseExited

    private void btnAddSupplyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSupplyMouseEntered
        this.btnAddSupply.setBackground(new Color(0,0,128));
    }//GEN-LAST:event_btnAddSupplyMouseEntered

    /**
     * Event Click cuando se quiere agregar un nuevo producto, al arreglo de productos 
     * que tendrá  el Menú.
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

            //Dialogo de peso
            DialogWeightSupply diaWeight= new DialogWeightSupply(null, true,
                supply.getName(),supply.getCategory().getUnityMeasure());

            double weightSugerido=this.defineWeightDialog(supply);
            diaWeight.setWeight(weightSugerido);
            diaWeight.setVisible(true);
            //El nuevo peso que colocó en dialog
            double newWeight=diaWeight.getWeight();

            if(newWeight>0){
                supply.setWeight(newWeight);
                //tiene el metodo check que suma pesos.
                this.menu.agregarInsumo(supply);
                this.createTable(this.menu.getSupplies());

                this.combSupplies.setSelectedItem("----Seleccionar Insumo----");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Debe Seleccionar Un Insumo De La Lista");
        }
    }//GEN-LAST:event_btnAddSupplyMouseClicked

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
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        this.btnCancel.setBackground(new Color(204, 91, 110));
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        this.btnCancel.setBackground(new Color(238, 15, 52));
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnAddMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMenuMouseExited
        this.btnAddMenu.setBackground(new Color(12, 184, 85    ));
    }//GEN-LAST:event_btnAddMenuMouseExited

    private void btnAddMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMenuMouseEntered
        this.btnAddMenu.setBackground(new Color( 12, 139, 50));
    }//GEN-LAST:event_btnAddMenuMouseEntered
    
    private void btnAddMenuMouseClicked(java.awt.event.MouseEvent evt) {                                        
        this.btnAddMenu.setBackground(new Color( 12, 139, 50));
        
        String name=this.txtNameMenu.getText();
        String price=this.txtPriceMenu.getText();
        double priceMenu=0;
        String type=combTiposMenu.getSelectedItem()+"";
        if(this.menu.getSupplies().size()>0&&
                !name.equals("")&&!price.equals("")){
                try{
                    priceMenu=Double.parseDouble(price);
                    this.menu.setName(name);
                    this.menu.setPrice(priceMenu);
                    this.menu.setType(type);
                    this.dispose();
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Debe Ingresar un Valor "
                            + "Númerico en el precio de Menú");
                }
        }else{   
            JOptionPane.showMessageDialog(this, "Debes Llenar todos los Campos "
                    + "del nuevo Menú Agregar Productos ");
        }
    }
//GEN-FIRST:event_btnAddMenuMouseClicked
//GEN-LAST:event_btnAddMenuMouseClicked

    private void btnAddMenuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddMenuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddMenuKeyReleased

    
    /**
     * Metodo para saber si un producto que se seleccionó ya esta el la lista del menu;
     * y si lo está entonces al dialogo de peso le modifique el peso sugerido.
     * @param sup
     * @return 
     */
    public double defineWeightDialog(Supply sup){
        double valorActual=0;
        for (int i = 0; i < this.menu.getSupplies().size(); i++) {
            Supply aux=menu.getSupplies().get(i);
            if(sup.getId().equals(aux.getId())){
                return aux.getWeight();
            }   
        }
        return valorActual;
    }
    
    /**
     * Elimina el insumo del Menú  y vuelve a pintar la table.
     */
    private void deleteSupply(){
        Supply sup = this.tableController.getSupplyFromTableSupplies(this.table);
        if(sup == null)
            JOptionPane.showMessageDialog(this, "No Se Seleccionó Ningún Proveedor");
        else{            
            int opc = JOptionPane.showConfirmDialog(null, "Realmente Desea Eliminar El Insumo: "+sup.getName().toUpperCase(),
                    "Eliminar Insumo"+sup.getName().toUpperCase(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            //0 para si || 1 para no
            if (opc == 0){
                boolean ban=this.deleteSupplyFromPurchase(sup);
                //Si se elimino el proveedor de la base de datos.
                if(ban){
                    JOptionPane.showMessageDialog(this, "Insumo " + " Eliminado Correctamente");
                    this.createTable(this.menu.getSupplies());
                }else{
                    JOptionPane.showMessageDialog(this, "No Se Pudó Eliminar El Insumo de la compra");
                }
            }            
        }
    }
    
    /**
     * Elimina un insumo del arreglo de insumos que tiene el Menú.
     * @param supply
     * @return 
     */
    public boolean deleteSupplyFromPurchase(Supply supply){
        for (int i = 0; i < this.menu.getSupplies().size(); i++) {
            Supply sup= menu.getSupplies().get(i);
            //si el insumo que llega entá en el arreglo 
            if(supply.getId().equals(sup.getId()))
            {
                this.menu.getSupplies().remove(i);
                return true;
            }
            
        }
        return false;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMenu;
    private javax.swing.JButton btnAddSupply;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClearTable;
    private javax.swing.JComboBox<String> combSupplies;
    private javax.swing.JComboBox<String> combTiposMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelFondo;
    private javax.swing.JTable table;
    private javax.swing.JLabel txtEncabezado;
    private javax.swing.JTextField txtNameMenu;
    private javax.swing.JTextField txtPriceMenu;
    private javax.swing.JLabel txtSalir;
    // End of variables declaration//GEN-END:variables
}
