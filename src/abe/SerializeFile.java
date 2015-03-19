/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abe;

import it.unisa.dia.gas.jpbc.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.*;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 *
 * @author Harikrish
 */
public class SerializeFile {
    
    public static void shiftElement(ArrayList<Byte> arrlist, Element e) 
    {
         byte[] arr_e = e.toBytes();
         shiftByte(arrlist, arr_e.length);
         AppendByte(arrlist, arr_e);
    }

    public static int unshiftElement(byte[] arr, int offset, Element e) 
    {
        int len;
        int i;
        byte[] e_byte;

        len = unshiftByte(arr, offset);
        e_byte = new byte[(int) len];
        offset += 4;
        for (i = 0; i < len; i++)
        {
           e_byte[i] = arr[offset + i];
        }
        e.setFromBytes(e_byte);
        return (int) (offset + len);
    }

    public static void serializeString(ArrayList<Byte> arrlist, String s) 
    {
	byte[] b = s.getBytes();
	shiftByte(arrlist, b.length);
	AppendByte(arrlist, b);
    }

    public static int unserializeString(byte[] arr, int offset, StringBuffer sb) 
    {
        int i;
        int len;
        byte[] str_byte;

        len = unshiftByte(arr, offset);
        offset += 4;

        str_byte = new byte[len];

        for (i = 0; i < len; i++)
                str_byte[i] = arr[offset + i];

        sb.append(new String(str_byte));
        return offset + len;
    }

    public static byte[] serializePublicKey(Public pub) 
    {
        ArrayList<Byte> arrlist = new ArrayList<Byte>();
        serializeString(arrlist, pub.pairingDesc);
        shiftElement(arrlist, pub.g);
        shiftElement(arrlist, pub.h);
        shiftElement(arrlist, pub.gp);
        shiftElement(arrlist, pub.g_hat_alpha);
        return byteArray(arrlist);
    }

	public static Public unserializePublicKey(byte[] b) 
        {
            Public pub;
            int offset;
            pub = new Public();
            offset = 0;
            StringBuffer sb = new StringBuffer("");
            offset = unserializeString(b, offset, sb);
            pub.pairingDesc = sb.substring(0);
            CurveParameters params = new DefaultCurveParameters().load(new ByteArrayInputStream(pub.pairingDesc.getBytes()));
            pub.p = PairingFactory.getPairing(params);
            Pairing pairing = pub.p;
            pub.g = pairing.getG1().newElement();
            pub.h = pairing.getG1().newElement();
            pub.gp = pairing.getG2().newElement();
            pub.g_hat_alpha = pairing.getGT().newElement();
            offset = unshiftElement(b, offset, pub.g);
            offset = unshiftElement(b, offset, pub.h);
            offset = unshiftElement(b, offset, pub.gp);
            offset = unshiftElement(b, offset, pub.g_hat_alpha);
            return pub;
	}

	public static byte[] serializeMasterKey(Mask msk) 
        {
            ArrayList<Byte> arrlist = new ArrayList<Byte>();
            shiftElement(arrlist, msk.beta);
            shiftElement(arrlist, msk.g_alpha);
            return byteArray(arrlist);
	}
        
	public static Mask unserializeMasterKey(Public pub, byte[] b) 
        {
            int offset = 0;
            Mask msk = new Mask();
            msk.beta = pub.p.getZr().newElement();
            msk.g_alpha = pub.p.getG2().newElement();
            offset = unshiftElement(b, offset, msk.beta);
            offset = unshiftElement(b, offset, msk.g_alpha);
            return msk;
	}

	public static byte[] serializePrivateKey(Private prv) 
        {
            ArrayList<Byte> arrlist;
            int prvCompsLen, i;
            arrlist = new ArrayList<Byte>();
            prvCompsLen = prv.comps.size();
            shiftElement(arrlist, prv.d);
            shiftByte(arrlist, prvCompsLen);
            for (i = 0; i < prvCompsLen; i++) {
                serializeString(arrlist, prv.comps.get(i).attr);
                shiftElement(arrlist, prv.comps.get(i).d);
                shiftElement(arrlist, prv.comps.get(i).dp);
            }
            return byteArray(arrlist);
	}

	public static Private unserializePrivateKey(Public pub, byte[] b) {
            Private prv;
            int i, offset, len;
            prv = new Private();
            offset = 0;
            prv.d = pub.p.getG2().newElement();
            offset = unshiftElement(b, offset, prv.d);
            prv.comps = new ArrayList<PrivateComponent>();
            len = unshiftByte(b, offset);
            offset += 4;
            for (i = 0; i < len; i++) {
                PrivateComponent c = new PrivateComponent();
                StringBuffer sb = new StringBuffer("");
                offset = unserializeString(b, offset, sb);
                c.attr = sb.substring(0);
                c.d = pub.p.getG2().newElement();
                c.dp = pub.p.getG2().newElement();
                offset = unshiftElement(b, offset, c.d);
                offset = unshiftElement(b, offset, c.dp);
                prv.comps.add(c);
            }
            return prv;
	}

	public static byte[] serializeCipherKey(Cipher cph) {
            ArrayList<Byte> arrlist = new ArrayList<Byte>();
            SerializeFile.shiftElement(arrlist, cph.cs);
            SerializeFile.shiftElement(arrlist, cph.c);
            SerializeFile.serializePolicy(arrlist, cph.p);

            return byteArray(arrlist);
	}

	public static Cipher unserializeCipherKey(Public pub, byte[] cphBuf) {
            Cipher cph = new Cipher();
            int offset = 0;
            int[] offset_arr = new int[1];
            cph.cs = pub.p.getGT().newElement();
            cph.c = pub.p.getG1().newElement();
            offset = SerializeFile.unshiftElement(cphBuf, offset, cph.cs);
            offset = SerializeFile.unshiftElement(cphBuf, offset, cph.c);
            offset_arr[0] = offset;
            cph.p = SerializeFile.unserializePolicy(pub, cphBuf, offset_arr);
            offset = offset_arr[0];
            return cph;
	}
        
	private static void shiftByte(ArrayList<Byte> arrlist, int k) {
            int i;
            byte b;

            for (i = 3; i >= 0; i--) {
                b = (byte) ((k & (0x000000ff << (i * 8))) >> (i * 8));
                arrlist.add(Byte.valueOf(b));
            }
	}

	private static int unshiftByte(byte[] arr, int offset) {
            int i;
            int r = 0;

            for (i = 3; i >= 0; i--)
            {
               r |= (byte2int(arr[offset++])) << (i * 8);
            }

            return r;
	}

	private static void serializePolicy(ArrayList<Byte> arrlist,Policy p) {
            shiftByte(arrlist, p.k);

            if (p.children == null || p.children.length == 0) {
                    shiftByte(arrlist, 0);
                    serializeString(arrlist, p.attr);
                    shiftElement(arrlist, p.c);
                    shiftElement(arrlist, p.cp);
            } else {
                    shiftByte(arrlist, p.children.length);
                    for (int i = 0; i < p.children.length; i++)
                            serializePolicy(arrlist, p.children[i]);
            }
	}

	private static Policy unserializePolicy(Public pub, byte[] arr,int[] offset) {
            int i;
            int n;
            Policy p = new Policy();
            p.k = unshiftByte(arr, offset[0]);
            offset[0] += 4;
            p.attr = null;

            n = unshiftByte(arr, offset[0]);
            offset[0] += 4;
            if (n == 0) {
                p.children = null;

                StringBuffer sb = new StringBuffer("");
                offset[0] = unserializeString(arr, offset[0], sb);
                p.attr = sb.substring(0);

                p.c = pub.p.getG1().newElement();
                p.cp = pub.p.getG1().newElement();

                offset[0] = unshiftElement(arr, offset[0], p.c);
                offset[0] = unshiftElement(arr, offset[0], p.cp);
            } else {
                p.children = new Policy[n];
                for (i = 0; i < n; i++)
                        p.children[i] = unserializePolicy(pub, arr, offset);
            }

            return p;
	}

	private static int byte2int(byte b) {
		if (b >= 0)
			return b;
		return (256 + b);
	}

	private static void AppendByte(ArrayList<Byte> arrlist, byte[] b) {
		int len = b.length;
		for (int i = 0; i < len; i++)
			arrlist.add(Byte.valueOf(b[i]));
	}

	private static byte[] byteArray(ArrayList<Byte> B) {
		int len = B.size();
		byte[] b = new byte[len];
	
		for (int i = 0; i < len; i++)
			b[i] = B.get(i).byteValue();
	
		return b;
	} 
}
