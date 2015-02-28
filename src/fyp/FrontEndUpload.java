/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FrontEndUpload extends javax.swing.JFrame {

    String file_name="",selected_file_path="",new_file_path="",public_key="",private_key="";
            
    public FrontEndUpload() {
        initComponents();
        file_type.setEditable(false);
        file_size.setEditable(false);
      //  key_generate.setVisible(false);
     
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        choose_file = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        user_file_name = new javax.swing.JTextField();
        file_type = new javax.swing.JTextField();
        file_size = new javax.swing.JTextField();
        file_path = new javax.swing.JTextField();
        upload = new javax.swing.JButton();
        key_generate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        choose_file.setText("Choose File");
        choose_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choose_fileActionPerformed(evt);
            }
        });

        jLabel1.setText("File Name");

        jLabel2.setText("File Type");

        jLabel3.setText("File Size");

        user_file_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                user_file_nameKeyReleased(evt);
            }
        });

        upload.setText("Upload");
        upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadActionPerformed(evt);
            }
        });

        key_generate.setText("Generate Key");
        key_generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                key_generateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(choose_file)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(file_path, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(key_generate))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(file_size, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(file_type, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(user_file_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(upload)
                .addGap(246, 246, 246))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(file_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choose_file)
                    .addComponent(key_generate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(user_file_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(file_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(file_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(upload)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choose_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choose_fileActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          selected_file_path = selectedFile.getAbsolutePath();
          file_name = selectedFile.getName();
          String type[]=file_name.split("\\.");
          user_file_name.setText(type[0]);
          file_path.setText(selected_file_path);
          file_type.setText(type[1].toUpperCase());
          file_size.setText((selectedFile.length()/1024)+" KB");
          new_file_path = selected_file_path.replace("\\","\\\\");
          System.out.println("file_name = " + file_name);
          System.out.println(new_file_path);
        }
    }//GEN-LAST:event_choose_fileActionPerformed

    private void uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadActionPerformed

        upload upload_file=new upload();
        keygeneration file_encrypt=new keygeneration();
        ObjectInputStream inputStream = null;
        String path=file_path.getText();
        String workingDir = System.getProperty("user.dir");
        String new_working_dir=workingDir.replace("\\","//");
        path=path.replace("\\","//");
        System.out.println("path = " + path);
        try
      {
          System.out.println("public_key = " + public_key);
          System.out.println("new_working_dir = " + new_working_dir+"//keys//"+public_key);
        //inputStream = new ObjectInputStream(new FileInputStream("keys//"+public_key));
        //final PublicKey publicKey = (PublicKey) inputStream.readObject();
        //final byte[] cipherText = file_encrypt.encrypt(path,new_working_dir+"//keys//"+public_key);
          //System.out.println("cipherText = " + cipherText);
          File file=new File("keys//temp_file.txt");
          if(!file.exists())
          {
          file.createNewFile();
          }
          FileWriter fw = new FileWriter(file.getAbsoluteFile());
	  BufferedWriter bw = new BufferedWriter(fw);
       //   bw.write(cipherText.toString());
          
          if(upload_file.uploadFile(file_name, file.getAbsolutePath()) == 1)
        {
            JOptionPane.showMessageDialog(this, "Successfully Uploaded");
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Upload Failed");
        }
          bw.close();
      } catch(Exception e)
      {
          System.out.println("Encryption file calling error = " + e);
      }
            
        if(upload_file.uploadFile(file_name, new_file_path) == 1)
        {
            JOptionPane.showMessageDialog(this, "Successfully Uploaded");
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Upload Failed");
        }
    }//GEN-LAST:event_uploadActionPerformed

    private void user_file_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_user_file_nameKeyReleased
        // TODO add your handling code here:
        file_name=user_file_name.getText();
    }//GEN-LAST:event_user_file_nameKeyReleased

    private void key_generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_key_generateActionPerformed
        String name=user_file_name.getText();
        String split_name[]=name.split("\\.");
        
        String algo="AES";
        public_key=algo+"-public-key_"+split_name[0]+".key";
        private_key=algo+"-private-key_"+split_name[0]+".key";
        
        File file_dir=new File("keys");
        keygeneration key_generate=new keygeneration();
        if(!file_dir.exists())
        {
            file_dir.mkdirs();
        }
        File public_file=new File("keys//"+public_key);
        if(!public_file.exists())
        {
            try {
                public_file.createNewFile();
            } catch (Exception ex) {
                System.out.println("Public key File Creation Error = " + ex);
            }
        }
        File private_file=new File("keys//"+private_key);
        if(!private_file.exists())
        {
            try {
                private_file.createNewFile();
            } catch (Exception ex) {
                System.out.println("Private key File Creation Error = " + ex);
            }
        }
        key_generate.generateKey("keys//"+private_key,"keys//"+public_key);
    }//GEN-LAST:event_key_generateActionPerformed

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
            java.util.logging.Logger.getLogger(FrontEndUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontEndUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontEndUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontEndUpload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontEndUpload().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton choose_file;
    private javax.swing.JTextField file_path;
    private javax.swing.JTextField file_size;
    private javax.swing.JTextField file_type;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton key_generate;
    private javax.swing.JButton upload;
    private javax.swing.JTextField user_file_name;
    // End of variables declaration//GEN-END:variables
}
