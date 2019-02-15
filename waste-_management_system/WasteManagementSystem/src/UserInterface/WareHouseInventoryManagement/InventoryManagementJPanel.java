/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.WareHouseInventoryManagement;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Inventory;
import Business.Organization.InventoryManagementOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.InventoryWorkRequest;
import Business.WorkQueue.WorkRequest;
import Business.WorkQueue.trashCollectWorkRequest;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author sumitjanawlekar
 */
public class InventoryManagementJPanel extends javax.swing.JPanel {

    /**
     * Creates new form InventoryManagementJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private InventoryManagementOrganization inventoryManagementOrganization;
    private Enterprise enterprise;
    private EcoSystem business;
    public InventoryManagementJPanel(JPanel userProcessContainer, UserAccount account, InventoryManagementOrganization organization, Enterprise enterprise, EcoSystem business) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.inventoryManagementOrganization = organization;
        this.enterprise = enterprise;
        this.business = business;
        populateInventory();
        populateTable();
    }
    public void populateInventory(){
       
        HashMap<String,Integer> mapInventory = new HashMap<>();
        int total  = 0;
        int temp = 0;

        for(WorkRequest request : inventoryManagementOrganization.getWorkQueue().getWorkRequestList()){
            if(((InventoryWorkRequest)request).getTrashType() != null){
                total += ((InventoryWorkRequest)request).getQuantity();
            }
            if(mapInventory.containsKey(((InventoryWorkRequest)request).getTrashType())){
                temp  = mapInventory.get(((InventoryWorkRequest)request).getTrashType());
                mapInventory.put(((InventoryWorkRequest)request).getTrashType(), temp + total);
            }
            else{
            mapInventory.put(((InventoryWorkRequest)request).getTrashType(),total);
            }
            total = 0;
        }
        inventoryManagementOrganization.getInventoryList().getInventoryDirectory().clear();
        for(String trash : mapInventory.keySet()){
            Inventory i = new Inventory();
            i.setTrashType(trash);
            i.setTotalQuantity(mapInventory.get(trash));
            inventoryManagementOrganization.getInventoryList().getInventoryDirectory().add(i);
        }
//        System.out.println("Trash   " + mapInventory.keySet() + "   Quantity    " +  mapInventory.values());
        
    }
    
    public void populateTable(){
        DefaultTableModel model = (DefaultTableModel)tblInventory.getModel();
        
        model.setRowCount(0);
        
        for(Inventory inventory : inventoryManagementOrganization.getInventoryList().getInventoryDirectory()){
            Object[] row = new Object[2];
            row[0] = inventory;
            row[1] = inventory.getTotalQuantity();

            model.addRow(row);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 51));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblInventory.setBackground(new java.awt.Color(204, 204, 255));
        tblInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trash Type", "Quantity"
            }
        ));
        jScrollPane1.setViewportView(tblInventory);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 375, 275));

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INVENTORY WORK AREA");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 554, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInventory;
    // End of variables declaration//GEN-END:variables
}
