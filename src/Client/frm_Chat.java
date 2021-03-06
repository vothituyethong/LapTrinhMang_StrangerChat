package Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class frm_Chat extends javax.swing.JFrame {

    private String name;
    public Client client;
    public int number = 1;
    
    public frm_Chat() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("CHAT WITH STRANGER");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void appendChatBox(String message){
        chatBox.append("\n"+message+"\n");
    }
    
    public void setTxtName(String name){
        txtName.setText(name);
    }
    
    public void setTxtPartner(String partner){
        txtPartner.setText("Chat with "+partner);
    }
    
    public void resetChatBox(){
        chatBox.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatBox = new javax.swing.JTextArea();
        txtMes = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        txtPartner = new javax.swing.JLabel();
        txtName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        chatBox.setEditable(false);
        chatBox.setColumns(20);
        chatBox.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        chatBox.setRows(5);
        jScrollPane1.setViewportView(chatBox);

        txtMes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnSend.setBackground(new java.awt.Color(255, 102, 102));
        btnSend.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSend.setText("Send");
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSendMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSendMouseExited(evt);
            }
        });
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtPartner.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtPartner.setForeground(new java.awt.Color(255, 255, 255));
        txtPartner.setText("Name's partner");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setText("Your name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPartner)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtName))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMes, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPartner)
                    .addComponent(txtName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMes))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        if(!txtMes.getText().equals("")){
            String message = txtMes.getText();
            chatBox.append("\n"+message+"\n");
            try {
                client.send("MES_"+txtMes.getText());
            } catch (IOException ex) {
            }
            txtMes.setText("");
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        client.closeWindow();
        if(number ==2)
            number = 1;
    }//GEN-LAST:event_formWindowClosed

    private void btnSendMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseEntered
        // TODO add your handling code here:
        btnSend.setBackground(new java.awt.Color(243, 181, 195));
    }//GEN-LAST:event_btnSendMouseEntered

    private void btnSendMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseExited
        // TODO add your handling code here:
        btnSend.setBackground(new java.awt.Color(255,102,102));
    }//GEN-LAST:event_btnSendMouseExited

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JTextArea chatBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMes;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtPartner;
    // End of variables declaration//GEN-END:variables
}
