/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Helpers.SwingUtils;
import Protocole.NetworkClient;
import Protocole.PacketCom;
import Protocole.RLP;
import Securite.MyKeys;
import Utils.Cryptage;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Greenlamp
 */
public class Main extends javax.swing.JDialog {

    /**
     * Creates new form Main
     */
    NetworkClient socket;
    MyKeys myKeys;
    Cryptage cryptage;

    public Main(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    Main(Connexion parent, NetworkClient socket, MyKeys myKeys) {
        super(parent, true);
        initComponents();
        this.socket = socket;
        this.myKeys = myKeys;
        this.cryptage = new Cryptage();
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
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        GbRoom = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        GvaliderBdRoom = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        GdateBdRoom = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        GlisteBdRoom = new javax.swing.JList();
        jLabel12 = new javax.swing.JLabel();
        GpRoom = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        GnumChambreArRoom = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        GnomArRoom = new javax.swing.JTextField();
        GvaliderArRoom = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        GdateArRoom = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        GcRoom = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        GnumChambreMisRoom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        GnomMisRoom = new javax.swing.JTextField();
        GvaliderMisRoom = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        GdateMisRoom = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Applic_Reservations");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        GvaliderBdRoom.setText("valider");
        GvaliderBdRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderBdRoomActionPerformed(evt);
            }
        });

        jLabel15.setText("date:");

        GdateBdRoom.setText("01/02/2013");

        jScrollPane1.setViewportView(GlisteBdRoom);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GvaliderBdRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GdateBdRoom)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateBdRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GvaliderBdRoom)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel12.setText("Liste des réservations pour le motel/village");

        javax.swing.GroupLayout GbRoomLayout = new javax.swing.GroupLayout(GbRoom);
        GbRoom.setLayout(GbRoomLayout);
        GbRoomLayout.setHorizontalGroup(
            GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GbRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        GbRoomLayout.setVerticalGroup(
            GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GbRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GbRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BDROOM", GbRoom);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setText("Numéro chambre:");

        GnumChambreArRoom.setText("1");

        jLabel8.setText("Nom du client:");

        GnomArRoom.setText("knuts");

        GvaliderArRoom.setText("valider");
        GvaliderArRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderArRoomActionPerformed(evt);
            }
        });

        jLabel17.setText("Date réservation:");

        GdateArRoom.setText("01/02/2013");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jLabel8)
                        .addComponent(GnumChambreArRoom)
                        .addComponent(GnomArRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
                    .addComponent(GvaliderArRoom)
                    .addComponent(jLabel17)
                    .addComponent(GdateArRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnumChambreArRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnomArRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateArRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GvaliderArRoom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setText("Les titulaires d'une réservation sont arrivés");

        javax.swing.GroupLayout GpRoomLayout = new javax.swing.GroupLayout(GpRoom);
        GpRoom.setLayout(GpRoomLayout);
        GpRoomLayout.setHorizontalGroup(
            GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GpRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(203, Short.MAX_VALUE))
        );
        GpRoomLayout.setVerticalGroup(
            GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GpRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GpRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ARROOM", GpRoom);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setText("Numéro chambre:");

        GnumChambreMisRoom.setText("1");

        jLabel11.setText("Nom du client:");

        GnomMisRoom.setText("knuts");

        GvaliderMisRoom.setText("valider");
        GvaliderMisRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GvaliderMisRoomActionPerformed(evt);
            }
        });

        jLabel16.setText("Date réservation:");

        GdateMisRoom.setText("01/03/2013");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(GnumChambreMisRoom)
                    .addComponent(GnomMisRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(GvaliderMisRoom)
                    .addComponent(jLabel16)
                    .addComponent(GdateMisRoom))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnumChambreMisRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GnomMisRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GdateMisRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GvaliderMisRoom)
                .addContainerGap())
        );

        jLabel14.setText("les titulaires d'une réservation ne sont pas arrivés au jour prévu");

        javax.swing.GroupLayout GcRoomLayout = new javax.swing.GroupLayout(GcRoom);
        GcRoom.setLayout(GcRoomLayout);
        GcRoomLayout.setHorizontalGroup(
            GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GcRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        GcRoomLayout.setVerticalGroup(
            GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GcRoomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GcRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MISROOM", GcRoom);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void GvaliderMisRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderMisRoomActionPerformed
        String numChambre = GnumChambreArRoom.getText();
        String nomClient = GnomArRoom.getText();
        String date = GdateArRoom.getText();

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {numChambre, nomClient, date, signatureResponsable};
        socket.send(new PacketCom(RLP.MISROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GvaliderMisRoomActionPerformed

    private void GvaliderArRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderArRoomActionPerformed
        String numChambre = GnumChambreArRoom.getText();
        String nomClient = GnomArRoom.getText();
        String date = GdateArRoom.getText();

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {numChambre, nomClient, date, signatureResponsable};
        socket.send(new PacketCom(RLP.ARROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GvaliderArRoomActionPerformed

    private void GvaliderBdRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GvaliderBdRoomActionPerformed
        String date = GdateBdRoom.getText();

        Signature signature;
        byte[] signatureResponsable = null;
        try {
            signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(myKeys.getClePrivee());
            signatureResponsable = signature.sign();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        Object[] infos = {date, signatureResponsable};
        socket.send(new PacketCom(RLP.BDROOM, (Object)infos));
        PacketCom packetRetour;
        try {
            packetRetour = socket.receive();
            traitementPacket(packetRetour);
        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_GvaliderBdRoomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main dialog = new Main(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel GbRoom;
    private javax.swing.JPanel GcRoom;
    private javax.swing.JTextField GdateArRoom;
    private javax.swing.JTextField GdateBdRoom;
    private javax.swing.JTextField GdateMisRoom;
    private javax.swing.JList GlisteBdRoom;
    private javax.swing.JTextField GnomArRoom;
    private javax.swing.JTextField GnomMisRoom;
    private javax.swing.JTextField GnumChambreArRoom;
    private javax.swing.JTextField GnumChambreMisRoom;
    private javax.swing.JPanel GpRoom;
    private javax.swing.JButton GvaliderArRoom;
    private javax.swing.JButton GvaliderBdRoom;
    private javax.swing.JButton GvaliderMisRoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    private void traitementPacket(PacketCom packetReponse) {
        String type = packetReponse.getType();
        Object contenu = packetReponse.getObjet();

        if(type.equals(RLP.BDROOM_OUI)){
            LinkedList<String> listeReservation = (LinkedList<String>) contenu;
            SwingUtils.emptyList(GlisteBdRoom);
            for(String elm : listeReservation){
                SwingUtils.addToList(GlisteBdRoom, elm);
            }
        }else if(type.equals(RLP.BDROOM_NON)){
            String message = (String) contenu;
            JOptionPane.showMessageDialog(this, message);
        }else if(type.equals(RLP.ARROOM_OUI)){
            JOptionPane.showMessageDialog(this, "ok");
        }else if(type.equals(RLP.ARROOM_NON)){
            String message = (String) contenu;
            JOptionPane.showMessageDialog(this, message);
        }else if(type.equals(RLP.MISROOM_OUI)){
            JOptionPane.showMessageDialog(this, "ok");
        }else if(type.equals(RLP.MISROOM_NON)){
            String message = (String) contenu;
            JOptionPane.showMessageDialog(this, message);
        }else{
            String message = (String) contenu;
            JOptionPane.showMessageDialog(this, message);
        }
    }

}