/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpabe;

import abe.ElementBoolean;
import abe.Private;
import abe.Cipher;
import abe.Public;
import abe.AttributeBasedEncryption;
import abe.SerializeFile;
import abe.CipherKey;
import abe.Mask;
import cpabe.policy.AttributePolicy;
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
        AttributeBasedEncryption.setup(pub,msk);
        public_byte=SerializeFile.serializePublicKey(pub);
        Common.spitFile(public_file, public_byte);
	msk_byte = SerializeFile.serializeMasterKey(msk);
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
            pub=SerializeFile.unserializePublicKey(public_byte);
            
            msk_byte=Common.suckFile(msk_file);
            msk=SerializeFile.unserializeMasterKey(pub, msk_byte);
            String[] attr_arr = AttributePolicy.parseAttribute(attribute);
            Private prv = AttributeBasedEncryption.keyGeneration(pub, msk, attr_arr);
            private_byte = SerializeFile.serializePrivateKey(prv);
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
            pub=SerializeFile.unserializePublicKey(public_byte);
            
            cipher_key=AttributeBasedEncryption.encryption(pub,policy);
            cipher=cipher_key.cph;
            m=cipher_key.key;
            if (cipher == null) 
            {
                System.out.println("Error happed in enc");
                System.exit(0);
            }
            cipher_buffer = SerializeFile.serializeCipherKey(cipher);
            plt = Common.suckFile(input_file);
            aes_buffer = AESCoder.encrypt(m.toBytes(), plt);
            Common.writeCpabeFile(encrypt_file, cipher_buffer, aes_buffer);
        }
        catch(Exception e)
        {
            System.out.println("Encryption error = " + e);
        }
    }
    public int decryption(String public_file,String private_file,String encrypt_file,String decrypt_file,String prv_file)
    {
        try
        {
            byte[] aes_buffer;
            byte[] cipher_buffer;
            byte[] plt;
            byte[] private_byte,private_byte_1;
            byte[] public_byte;
            byte[][] temp;
            Cipher cipher;
            Private prv,prv1;
            Public pub;
            public_byte = Common.suckFile(public_file);
            pub = SerializeFile.unserializePublicKey(public_byte);
            temp = Common.readCpabeFile(encrypt_file);
            aes_buffer = temp[0];
            cipher_buffer = temp[1];
            cipher = SerializeFile.unserializeCipherKey(pub, cipher_buffer);
            private_byte = Common.suckFile(private_file);
            prv = SerializeFile.unserializePrivateKey(pub, private_byte);
            private_byte_1 = Common.suckFile(prv_file);
            prv1 = SerializeFile.unserializePrivateKey(pub, private_byte_1);
            ElementBoolean beb = AttributeBasedEncryption.decryption(pub, prv, cipher,prv1);
            if (beb.b) 
            {
                plt = AESCoder.decrypt(beb.e.toBytes(), aes_buffer);
                Common.spitFile(decrypt_file, plt);
                return 1;
            } 
            else 
            {
		System.exit(0);
                return 0;
            }
                
        }
        catch(Exception e)
        {
            System.out.println("cpabe decryption error = " + e);
        }
        return 0;
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
