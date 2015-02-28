/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpabe;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Harikrish
 */
public class Common {
    public static byte[] suckFile(String input_file)
    {
        try
        {
            InputStream is = new FileInputStream(input_file);
            int size = is.available();
            byte[] content = new byte[size];
            is.read(content);
            is.close();
            return content;
        }
        catch(Exception e)
        {
            System.out.println("File Reading Error = " + e);
        }
        return null;
    }
    public static void spitFile(String output_file,byte[] b)
    {
        try
        {
        OutputStream os = new FileOutputStream(output_file);
        os.write(b);
        os.close();
        }
        catch(Exception e)
        {
            System.out.println("File Writing error = " + e);
        }
    }
    public static void writeCpabeFile(String encrypt_file,byte[] cipher_buffer, byte[] aes_buffer){
		
        int i;
        try
        {
            OutputStream os = new FileOutputStream(encrypt_file);
            for (i = 3; i >= 0; i--)
            {
                os.write(((aes_buffer.length & (0xff << 8 * i)) >> 8 * i));
            }
            os.write(aes_buffer);
            for (i = 3; i >= 0; i--)
            {
                os.write(((cipher_buffer.length & (0xff << 8 * i)) >> 8 * i));
            }
            os.write(cipher_buffer);
            os.close();
        }
        catch(Exception e)
        {
            System.out.println("Writing Error = " + e);
        }
    }
    public static byte[][] readCpabeFile(String encrypt_file) 
    {
	int i, len;
        try
        {
            InputStream is = new FileInputStream(encrypt_file);
            byte[][] res = new byte[2][];
            byte[] aesBuf, cphBuf;

            len = 0;
            for (i = 3; i >= 0; i--)
            {
                len |= is.read() << (i * 8);
            }
            aesBuf = new byte[len];
            is.read(aesBuf);
            len = 0;
            for (i = 3; i >= 0; i--)
            {
                len |= is.read() << (i * 8);
            }
            cphBuf = new byte[len];
            is.read(cphBuf);
            is.close();
            res[0] = aesBuf;
            res[1] = cphBuf;
            return res;
	}
        catch(Exception e)
        {
            System.out.println("Read Error = " + e);
        }
        return null;
    }

    public static ByteArrayOutputStream writeCpabeData(byte[] mBuf,byte[] cph_buffer, byte[] aes_buffer)
    {
            int i;
            try
            {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            for (i = 3; i >= 0; i--)
            {
                os.write(((mBuf.length & (0xff << 8 * i)) >> 8 * i));
            }
            os.write(mBuf);
            for (i = 3; i >= 0; i--)
            {
                os.write(((aes_buffer.length & (0xff << 8 * i)) >> 8 * i));
            }
            os.write(aes_buffer);
            for (i = 3; i >= 0; i--)
            {
                os.write(((cph_buffer.length & (0xff << 8 * i)) >> 8 * i));
            }
            os.write(cph_buffer);
            os.close();
            return os;
            }
            catch(Exception e)
            {
                System.out.println("Write Data Error = " + e);
            }
            return null;
    }
    public static byte[][] readCpabeData(InputStream is) {
        try
        {
            int i, len;

            byte[][] res = new byte[3][];
            byte[] mBuf, aes_buffer, cipher_buffer;
            len = 0;
            for (i = 3; i >= 0; i--)
            {
                len |= is.read() << (i * 8);
            }
            mBuf = new byte[len];
            is.read(mBuf);
            len = 0;
            for (i = 3; i >= 0; i--)
            {
                len |= is.read() << (i * 8);
            }
            aes_buffer = new byte[len];
            is.read(aes_buffer);
            len = 0;
            for (i = 3; i >= 0; i--)
            {
                len |= is.read() << (i * 8);
            }
            cipher_buffer = new byte[len];
            is.read(cipher_buffer);
            is.close();
            res[0] = aes_buffer;
            res[1] = cipher_buffer;
            res[2] = mBuf;
            return res;
        }
        catch(Exception e)
        {
            System.out.println("e = " + e);
        }
        return null;
    }
}
