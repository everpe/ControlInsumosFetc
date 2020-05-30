package dialogos;

import bd.BaseDatos;
import bd.Conexion;
import clases.Category;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import clases.Provider;
import clases.Supply;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 * FECHA ==> 2019-07-12.
 * Ventana para agregar un nuevo proveedor al negocio.
 * @author Luis Alejandro Gómez C.
 * @version 1.0.1.
 */
public class DialogSupply extends javax.swing.JDialog 
{
    /**
     * Proveedor que se desea agregar al negocio.
     */
    private Supply  supply;
    
    /**
     * Ventana donde se ingresaran los datos necesarios para registrar
     * un nuevo proveedor de surtido en el negocio.
     * @param parent Interfaz principal del sistema
     * @param modal para esta interfaz debe ser "true".
     * @param 1=> crear provider, 2=> editar un provider.
     */
    public DialogSupply(java.awt.Frame parent, boolean modal,int action,ArrayList<Category>categories){
        super(parent, modal);        
        initComponents();
        
        this.setLocationRelativeTo(parent);
        if(action==1){
            this.supply = null;
            this.paintCombCategories(categories);
//          this.clearContent();
        }else if(action==2){
            this.paintCombCategories(categories);
            this.initContentEdit();
        }
    }
    
    public void clearContent(){
        this.txtName.setText("");  
        this.txtWeight.setText("");
        this.lblUnity.setText("");
    }
//    
    
    
    public void initContentEdit(){
        if(this.supply!=null){
            this.txtName.setText(supply.getName());
            this.txtWeight.setText(supply.getWeight()+"");    
            
            Category cat=supply.getCategory();
            //llenando el cob con la Cat del Supply
            String aux = cat.getId()+"-"+cat.getName()+"-"+cat.getUnityMeasure();
            this.conbCategories.setSelectedItem(aux);
            
            this.lblUnity.setText(cat.getUnityMeasure());
            
        }else{
            System.out.println("No se pudó inicializar el Dialogsupply");
        }
    }
    /**
     * Obtiene el proveedor que se esta registrando en esta ventana.
     * @return Proveedor creado por esta ventana.
     */
    public Supply getSupply() {
        return supply;
    }

    /**
     * Modifica el insumo en esta ventana, se utiliza cuando 
     * se desea modificar un proveedor ya registrado en el sistema.
     * @param proveedor Proveedor que se pintara en la ventana.
     */
    public void setSupply(Supply suply) {
        System.out.println("SetSupplyyyyyy");
        this.supply = suply;
        if(suply!=null)
        this.initContentEdit();
    }
    
    /**
     * Hace todas las validaciones para crear un nuevo Supply en el  sistema.
     * @return Si es valido o no el Insumo que se intenta registrar.
     */
    public boolean checkSupply()
    {
        String name = this.txtName.getText();
        String weight = this.txtWeight.getText().replace(" ", "");
        
        boolean bandera = false;        
        //Se crea un Insumo seimpre con todos los datos.
        if(!name.equals("")||!weight.equals("")
            ||conbCategories.getSelectedItem().equals("----Seleccionar Categoria----"))
        {
            try
            {            
                String cat=conbCategories.getSelectedItem().toString();
                String []cat2=cat.split("-");
                int id=Integer.parseInt(cat2[0]);
                Category category= new Category(id, cat2[1], cat2[2]);
                
                double weightSupply=Double.parseDouble(weight);
                this.supply = new Supply("100",name, weightSupply,category); 
                
                bandera = true;
            }
            catch(Exception e) 
            {
                JOptionPane.showMessageDialog(null,"Error, Compruebe Los Datos Ingresados");
            }  
                                            
        }
        else
            JOptionPane.showMessageDialog(null,"Debe Ingresar Todos Los Datos "
                                            + "Para Registrar Un Insumo");
       
        return bandera;        
    }

    
    
    
    /**
     * 
     * @param categories 
     */
    public void paintCombCategories(ArrayList<Category> categories)
    {
        String aux = "";                
        int numCats = categories.size();
        this.conbCategories.removeAllItems();
        Category cat;
        this.conbCategories.addItem("----Seleccionar Categoria----");
        for(int i = 0; i < numCats; i++) 
        {
            cat = categories.get(i);
            aux = cat.getId()+"-"+cat.getName()+"-"+cat.getUnityMeasure();
            this.conbCategories.addItem(aux);
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

        panelContenedor = new javax.swing.JPanel();
        panelEncabezado = new javax.swing.JPanel();
        txtEncabezado = new javax.swing.JLabel();
        txtSalir = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtWeight = new javax.swing.JTextField();
        lblDirection = new javax.swing.JLabel();
        conbCategories = new javax.swing.JComboBox<>();
        lblName1 = new javax.swing.JLabel();
        lblUnity = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        panelContenedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        panelEncabezado.setBackground(new java.awt.Color(36, 52, 81));

        txtEncabezado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        txtEncabezado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seleccion.png"))); // NOI18N
        txtEncabezado.setText("      Agregar Insumo");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEncabezadoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSalir)
                .addContainerGap())
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addComponent(txtSalir)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblName.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        lblName.setText("Nombre:");

        txtName.setFont(new java.awt.Font("Cambria", 0, 16)); // NOI18N

        btnAceptar.setBackground(new java.awt.Color(20, 60, 108));
        btnAceptar.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
        });
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(176, 95, 95));
        btnCancelar.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtWeight.setFont(new java.awt.Font("Cambria", 0, 16)); // NOI18N

        lblDirection.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        lblDirection.setText("Peso:");

        conbCategories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblName1.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        lblName1.setText("Categoria");

        lblUnity.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        lblUnity.setText("g");

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContenedorLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDirection, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelContenedorLayout.createSequentialGroup()
                                .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblUnity, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtName)
                            .addComponent(conbCategories, 0, 309, Short.MAX_VALUE)))
                    .addGroup(panelContenedorLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(btnCancelar)
                        .addGap(48, 48, 48)
                        .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(conbCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName1))
                .addGap(36, 36, 36)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUnity, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDirection))
                .addGap(34, 34, 34)
                .addGroup(panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelContenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_txtSalirMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        boolean bandera = this.checkSupply();
        //Si ingreso un insumo correcto pasa a intentar actualizarlo en la db.
        if(bandera)
            this.dispose();        
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> conbCategories;
    private javax.swing.JLabel lblDirection;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblUnity;
    private javax.swing.JPanel panelContenedor;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JLabel txtEncabezado;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtSalir;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration//GEN-END:variables
}
