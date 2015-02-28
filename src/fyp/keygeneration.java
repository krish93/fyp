/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fyp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import javax.crypto.NoSuchPaddingException;


/**
 *
 * @author Harikrish
 */
public class keygeneration
{
    public static final String ALGORITHM = "RSA";
    public static final String PRIVATE_KEY_FILE="C://Users//Harikrish//Documents//NetBeansProjects//fyp//keys//RSA-private-key_bluestackpin.key"; 
    public static final String PUBLIC_KEY_FILE="C://Users//Harikrish//Documents//NetBeansProjects//fyp//keys//RSA-public-key_bluestackpin.key"; 
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException,  IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, IOException
    {
      generateKey(PRIVATE_KEY_FILE,PUBLIC_KEY_FILE);
      final String originalText = "Text to be encrypted will need to encryypt in the following way of sa";
      ObjectInputStream inputStream = null;

      try
      {
        inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        final PublicKey publicKey = (PublicKey) inputStream.readObject();
        final byte[] cipherText = encrypt("C://Users//Harikrish//Documents//bluestackpin.txt",PUBLIC_KEY_FILE);
        System.out.println("cipherText = " + cipherText);
        inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
      final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
      final String plainText = decrypt(cipherText, privateKey);
          System.out.println("plainText = " + plainText);
      }
      catch(Exception e)
      {
          System.out.println("Encrypt Error = " + e);
      }
    }
    public static void generateKey(String PRIVATE_KEY_FILE,String PUBLIC_KEY_FILE)
    {
        try
      {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(1024);
        KeyPair kp = kpg.genKeyPair();
        Key publicKey = kp.getPublic();
        Key privateKey = kp.getPrivate();
        System.out.println("publicKey = " + publicKey);
        System.out.println("privateKey = " + privateKey);
        File privateKeyFile = new File(PRIVATE_KEY_FILE);
        File publicKeyFile = new File(PUBLIC_KEY_FILE);

      
        if (privateKeyFile.getParentFile() != null) 
        {
          privateKeyFile.getParentFile().mkdirs();
        }
        privateKeyFile.createNewFile();

        if (publicKeyFile.getParentFile() != null) 
        {
          publicKeyFile.getParentFile().mkdirs();
        }
        publicKeyFile.createNewFile();

      
      ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
      publicKeyOS.writeObject(kp.getPublic());
      publicKeyOS.close();

      
      ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
      privateKeyOS.writeObject(kp.getPrivate());
      privateKeyOS.close();
      }
      catch(Exception e)
      {
          System.out.println("Key Generation = " + e);
      }
    }
    public static byte[] encrypt(String path,String PUBLIC_KEY_FILE) 
    {
        ObjectInputStream inputStreams = null;
        final PublicKey key;

      
      byte[] cipherText = null;
      if(ALGORITHM.equals("RSA"))
      {
        
      try 
      {
          String inputFile=path;
          inputStreams = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
          key = (PublicKey) inputStreams.readObject();
          inputStreams.close();
          FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipherText = cipher.doFinal(inputBytes);
      } 
      catch (Exception e) {
        e.printStackTrace();
      }
      }
    return cipherText;
  }
    public static String decrypt(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      
      final Cipher cipher = Cipher.getInstance(ALGORITHM);
        System.out.println("text = " + text);
      
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return new String(dectyptedText);
  }
}