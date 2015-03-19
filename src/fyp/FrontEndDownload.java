/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import abe.Cipher;
import abe.Private;
import abe.Public;
import abe.SerializeFile;
import abe.AttributeBasedEncryption;
import cpabe.Common;
import cpabe.Cpabe;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Harikrish
 */
public class FrontEndDownload extends javax.swing.JFrame {

    /**
     * Creates new form FrontEndDownload
     */
    String user_login_ret,user_email;
    public FrontEndDownload(String login_ret,String email) {
        initComponents();
        user_login_ret=login_ret;
        user_email=email;
        upload download_file=new upload();
        List files=download_file.ListFile();
        System.out.println("files = " + files);
        Iterator iter=files.iterator();
        DefaultListModel model=new DefaultListModel();
        while(iter.hasNext())
        {
            String file=(String)iter.next();
            System.out.println("file = " + file);
            model.addElement(file);
        }
        jList1.setModel(model);
        jList1.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        download = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane2.setViewportView(jList1);

        jLabel1.setFont(new java.awt.Font("Traditional Arabic", 1, 18)); // NOI18N
        jLabel1.setText("List of Files");

        download.setText("Download");
        download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(144, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(download)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(download)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public int policySatisfy(String user_email,String user_login,String Name)
    {
        String dir=System.getProperty("user.dir");
            String pub=dir+"\\sample1";
            String msk=dir+"\\sample2";
            String prv=dir+"\\sample3";
            DBConfig db=new DBConfig();
            String policy=db.Policy(user_email, Name);
            byte[] private_byte;
            byte[] public_byte;
            byte[] aes_buffer;
            byte[] cipher_buffer;
            byte[][] temp;
            String file_path_enc=dir+"\\download-encrypt\\"+Name;
            Cipher cipher;
            Private prv1;
            Public pub1;
            Cpabe dec=new Cpabe();
            dec.setup(pub,msk);
            dec.keyGeneration(pub, prv, msk, user_login_ret);
            dec=null;
            public_byte = Common.suckFile(pub);
            pub1 = SerializeFile.unserializePublicKey(public_byte);
            temp = Common.readCpabeFile(file_path_enc);
            aes_buffer = temp[0];
            cipher_buffer = temp[1];
            cipher = SerializeFile.unserializeCipherKey(pub1, cipher_buffer);
            private_byte = Common.suckFile(prv);
            prv1 = SerializeFile.unserializePrivateKey(pub1, private_byte);
            AttributeBasedEncryption check_policy=new AttributeBasedEncryption();
            check_policy.checkSatisfy(cipher.p, prv1);
            if (cipher.p.satisfiable)
            {
             return 1;
            }
           return 0;
    }
    public String getTime()
    {
        Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    	return  sdf.format(cal.getTime());
    }
    private void downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadActionPerformed
        // TODO add your handling code here:
        String start_time=getTime();
        List files=jList1.getSelectedValuesList();
        upload download=new upload();
        download.downloadFile(files);
        String name=jList1.getSelectedValue().toString();
        int policy=policySatisfy(user_email,user_login_ret,name);
        if(policy==1)
        {
            System.out.println("in");
            download.downloadKeyFile("private-"+name+".key");
            int ret=download.decryptFile(files,"private-"+name+".key");
            if(ret==1)
            {
            
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, name, "download", "success", start_time, end_time);
            acc.modifyHistoryYable(user_email, name, "success");
            acc=null;
            JOptionPane.showMessageDialog(this, "File Downloaded");
            }
            else
            {
                
                String end_time=getTime();
                DBConfig acc=new DBConfig();
                acc.insertAccessPolicy(user_email, name, "download", "failure", start_time, end_time);
                acc.modifyHistoryYable(user_email, name, "failure");
                acc=null;
                JOptionPane.showMessageDialog(this, "Error in Downloading the File");
            }
        }
        else
        {
            
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, name, "download", "failure", start_time, end_time);
            acc.modifyHistoryYable(user_email, name, "failure");
            acc=null;
            JOptionPane.showMessageDialog(this, "Policy is not satisfied to download");
        }
    }//GEN-LAST:event_downloadActionPerformed

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
            java.util.logging.Logger.getLogger(FrontEndDownload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontEndDownload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontEndDownload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontEndDownload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontEndDownload().setVisible(true);
            }
        });*/
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton download;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
