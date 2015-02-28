/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpabe;

import bswabe.*;
import cpabe.policy.LangPolicy;
import it.unisa.dia.gas.jpbc.Element;

/**
 *
 * @author Harikrish
 */
public class Cpabe {

    /**
     * @param args the command line arguments
     */
    public void setup(String public_file,String msk_file)
    {
        byte[] public_byte,msk_byte;
        Public pub = new Public();
        Mask msk = new Mask();
        bswabe.setup(pub,msk);
        public_byte=SerializeUtils.serializeBswabePub(pub);
        Common.spitFile(public_file, public_byte);
	msk_byte = SerializeUtils.serializeBswabeMsk(msk);
	Common.spitFile(msk_file, msk_byte);
    }
    public void keyGeneration(String public_file,String private_file,String msk_file,String attribute)
    {
        try
        {
            Public pub;
            Mask msk;
            byte[] public_byte,msk_byte,private_byte;
            
            public_byte=Common.suckFile(public_file);
            pub=SerializeUtils.unserializeBswabePub(public_byte);
            
            msk_byte=Common.suckFile(msk_file);
            msk=SerializeUtils.unserializeBswabeMsk(pub, msk_byte);
            String[] attr_arr = LangPolicy.parseAttribute(attribute);
            Private prv = bswabe.keyGeneration(pub, msk, attr_arr);
            private_byte = SerializeUtils.serializeBswabePrv(prv);
            Common.spitFile(private_file, private_byte);
        }
        catch(Exception e)
        {
            System.out.println("Key Generation Error = " + e);
        }
    }
    public void encryption(String public_file,String policy,String input_file,String encrypt_file)
    {
        try
        {
            Public pub;
            Cipher cipher;
            CipherKey cipher_key;
            byte[] plt;
            byte[] cipher_buffer;
            byte[] aes_buffer;
            byte[] public_byte;
            Element m;
            
            public_byte=Common.suckFile(public_file);
            pub=SerializeUtils.unserializeBswabePub(public_byte);
            
            cipher_key=bswabe.encryption(pub,policy);
            cipher=cipher_key.cph;
            m=cipher_key.key;
            if (cipher == null) 
            {
                System.out.println("Error happed in enc");
                System.exit(0);
            }
            cipher_buffer = SerializeUtils.bswabeCphSerialize(cipher);
            plt = Common.suckFile(input_file);
            aes_buffer = AESCoder.encrypt(m.toBytes(), plt);
            Common.writeCpabeFile(encrypt_file, cipher_buffer, aes_buffer);
        }
        catch(Exception e)
        {
            System.out.println("Encryption error = " + e);
        }
    }
    public void decryption(String public_file,String private_file,String encrypt_file,String decrypt_file)
    {
        try
        {
            byte[] aes_buffer;
            byte[] cipher_buffer;
            byte[] plt;
            byte[] private_byte;
            byte[] public_byte;
            byte[][] temp;
            Cipher cipher;
            Private prv;
            Public pub;
            public_byte = Common.suckFile(public_file);
            pub = SerializeUtils.unserializeBswabePub(public_byte);
            temp = Common.readCpabeFile(encrypt_file);
            aes_buffer = temp[0];
            cipher_buffer = temp[1];
            cipher = SerializeUtils.bswabeCphUnserialize(pub, cipher_buffer);
            private_byte = Common.suckFile(private_file);
            prv = SerializeUtils.unserializeBswabePrv(pub, private_byte);
            ElementBoolean beb = bswabe.decryption(pub, prv, cipher);
            if (beb.b) 
            {
                plt = AESCoder.decrypt(beb.e.toBytes(), aes_buffer);
                Common.spitFile(decrypt_file, plt);
            } 
            else 
            {
		System.exit(0);
            }
                
        }
        catch(Exception e)
        {
            System.out.println("cpabe decryption error = " + e);
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
