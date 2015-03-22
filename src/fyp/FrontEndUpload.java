/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import cpabe.Cpabe;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FrontEndUpload extends javax.swing.JFrame {

    String file_name="",selected_file_path="",new_file_path="",public_key="",private_key="";
    String public_dir="",private_dir="",master_dir="",encrypt_dir="",user_attribute="",user_email="";
            
    public FrontEndUpload(String attr,String email) {
        initComponents();
        user_email=email;
        user_attribute=attr;
        file_type.setEnabled(false);
        file_size.setEnabled(false);
        policy.setVisible(false);
        upload.setEnabled(false);
     
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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        policy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("UPLOAD");

        policy.setText("Generate Policy");
        policy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                policyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(upload)
                .addGap(248, 248, 248))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)))
                                .addGap(38, 38, 38))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(choose_file)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(file_size, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(file_type, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(user_file_name, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(file_path, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addComponent(policy))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(file_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(policy))
                    .addComponent(choose_file, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(user_file_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(file_type, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(file_size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(upload)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choose_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choose_fileActionPerformed
        
        JFileChooser fileChooser = new JFileChooser();
        String dir=System.getProperty("user.dir");
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
          public_dir=dir+"\\file_path\\public_key";
          private_dir=dir+"\\file_path\\private_key";
          master_dir=dir+"\\file_path\\master_key";
          encrypt_dir=dir+"\\file_path\\encrypt";
          File pub=new File(public_dir);
          if(!pub.exists())
          {
              pub.mkdirs();
          }
          File prv=new File(private_dir);
          if(!prv.exists())
          {
              prv.mkdirs();
          }
          File msk=new File(master_dir);
          if(!msk.exists())
          {
              msk.mkdirs();
          }
          File enc=new File(encrypt_dir);
          if(!enc.exists())
          {
              enc.mkdirs();
          }
          System.out.println("file_name = " + file_name);
          System.out.println(new_file_path);
         // fileChooser = null;
          policy.setVisible(true);
        }
    }//GEN-LAST:event_choose_fileActionPerformed

    public String getTime()
    {
        Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
    	return  sdf.format(cal.getTime());
    }
    public void reUpload(String file_name,String policy)
    {
        DBConfig db=new DBConfig();
       Date now=new Date();
       String dir=System.getProperty("user.dir");
       public_dir=dir+"\\file_path\\public_key";
          private_dir=dir+"\\file_path\\private_key";
          master_dir=dir+"\\file_path\\master_key";
          encrypt_dir=dir+"\\file_path\\encrypt";
          File pub=new File(public_dir);
          if(!pub.exists())
          {
              pub.mkdirs();
          }
          File prv=new File(private_dir);
          if(!prv.exists())
          {
              prv.mkdirs();
          }
          File msk=new File(master_dir);
          if(!msk.exists())
          {
              msk.mkdirs();
          }
          File enc=new File(encrypt_dir);
          if(!enc.exists())
          {
              enc.mkdirs();
          }
        System.out.println("policy = " + policy);
        db=null;
        upload upload_file=new upload();
        String start_time=getTime();
        System.out.println("start_time = " + start_time);
        ObjectInputStream inputStream = null;
        
        String workingDir = System.getProperty("user.dir");
        String path=workingDir+"//download//"+file_name;
        String new_working_dir=workingDir.replace("\\","//");
        path=path.replace("\\","//");
        System.out.println("path = " + path);
        String time=now.getTime()+"";
        String filename=time+file_name.split("\\.")[0];
        String private_key=private_dir+"\\private-"+filename+".key";
        String public_key=public_dir+"\\public-"+filename+".key";
        String master_key=master_dir+"\\master-"+filename+".key";
        String encrypt=encrypt_dir+"\\"+time+file_name;
        String modified_user_attribute=policy.replaceAll("[0-9]of[0-9]", "");
        System.out.println("modified_user_attribute = " + modified_user_attribute);
      /*  System.out.println("private_key = " + private_key);
        //System.out.println("public_key = " + public_key);
        //System.out.println("master_key = " + master_key);
        //System.out.println("encrypt = " + encrypt);
        //String policy=user_attribute+" 10of16";
        //String decrypt=workingDir+"\\file_path\\"+file_name;
        System.out.println("user_attribute = " + user_attribute);
        System.out.println("selected_file_path = " + selected_file_path);*/
        Cpabe file_upload=new Cpabe();
        System.out.println("Generating Public and Master key..!!");
        file_upload.setup(public_key, master_key);
        System.out.println("Public key and Master key Generated..!!");
        System.out.println("Generating Private Key..!!");
        file_upload.keyGeneration(public_key, private_key, master_key, modified_user_attribute);
        System.out.println("Private Key Generated..!!");
        System.out.println("Encrypting the file!!");
        file_upload.encryption(public_key, policy,path, encrypt);
        System.out.println("File Encrypted!!!");
        /*System.out.println("decrypting...!!!");
        file_upload.decryption(public_key, private_key, encrypt, decrypt);;
        System.out.println("File Decrypted..!!");*/
        if((upload_file.uploadFile(time+file_name, encrypt) == 1))
        {
            upload_file.uploadKeyFile("private-"+time+file_name+".key", private_key);
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, time+file_name, "upload", "success", start_time, end_time);
            acc=null;
            JOptionPane.showMessageDialog(this, "Successfully Uploaded");
        }
        else
        {
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, filename, "upload", "failure", start_time, end_time);
            acc=null;
            JOptionPane.showMessageDialog(this, "Upload Failed");
        }
    }
    private void uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadActionPerformed
       DBConfig db=new DBConfig();
       Date now=new Date();
       String policy=db.Policy(user_email, file_name);
        System.out.println("policy = " + policy);
        db=null;
        upload upload_file=new upload();
        String start_time=getTime();
        System.out.println("start_time = " + start_time);
        ObjectInputStream inputStream = null;
        String path=file_path.getText();
        String workingDir = System.getProperty("user.dir");
        String new_working_dir=workingDir.replace("\\","//");
        path=path.replace("\\","//");
        System.out.println("path = " + path);
        String time=now.getTime()+"";
        String filename=time+file_name.split("\\.")[0];
        String private_key=private_dir+"\\private-"+filename+".key";
        String public_key=public_dir+"\\public-"+filename+".key";
        String master_key=master_dir+"\\master-"+filename+".key";
        String encrypt=encrypt_dir+"\\"+time+file_name;
        String modified_user_attribute=policy.replaceAll("[0-9]of[0-9]", "");
        System.out.println("modified_user_attribute = " + modified_user_attribute);
      /*  System.out.println("private_key = " + private_key);
        //System.out.println("public_key = " + public_key);
        //System.out.println("master_key = " + master_key);
        //System.out.println("encrypt = " + encrypt);
        //String policy=user_attribute+" 10of16";
        //String decrypt=workingDir+"\\file_path\\"+file_name;
        System.out.println("user_attribute = " + user_attribute);
        System.out.println("selected_file_path = " + selected_file_path);*/
        Cpabe file_upload=new Cpabe();
        System.out.println("Generating Public and Master key..!!");
        file_upload.setup(public_key, master_key);
        System.out.println("Public key and Master key Generated..!!");
        System.out.println("Generating Private Key..!!");
        file_upload.keyGeneration(public_key, private_key, master_key, modified_user_attribute);
        System.out.println("Private Key Generated..!!");
        System.out.println("Encrypting the file!!");
        file_upload.encryption(public_key, policy, selected_file_path, encrypt);
        System.out.println("File Encrypted!!!");
        /*System.out.println("decrypting...!!!");
        file_upload.decryption(public_key, private_key, encrypt, decrypt);;
        System.out.println("File Decrypted..!!");*/
        if((upload_file.uploadFile(time+file_name, encrypt) == 1))
        {
            upload_file.uploadKeyFile("private-"+time+file_name+".key", private_key);
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, time+file_name, "upload", "success", start_time, end_time);
            acc=null;
            JOptionPane.showMessageDialog(this, "Successfully Uploaded");
        }
        else
        {
            String end_time=getTime();
            DBConfig acc=new DBConfig();
            acc.insertAccessPolicy(user_email, filename, "upload", "failure", start_time, end_time);
            acc=null;
            JOptionPane.showMessageDialog(this, "Upload Failed");
        }
    }//GEN-LAST:event_uploadActionPerformed

    private void user_file_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_user_file_nameKeyReleased
        // TODO add your handling code here:
        file_name=user_file_name.getText();
    }//GEN-LAST:event_user_file_nameKeyReleased

    private void policyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_policyActionPerformed
        // TODO add your handling code here:
        String policy="";
        AccessPolicy p=new AccessPolicy(user_email,file_name);
        p.setVisible(true);
        p=null;
        //String policy=p.getPolicy();
       upload.setEnabled(true);
        //System.out.println("policy = " + p.result);
    }//GEN-LAST:event_policyActionPerformed

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
              //  new FrontEndUpload().setVisible(true);
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton policy;
    private javax.swing.JButton upload;
    private javax.swing.JTextField user_file_name;
    // End of variables declaration//GEN-END:variables
}
