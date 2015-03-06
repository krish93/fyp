/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bswabe;

import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.DefaultCurveParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Harikrish
 */
public class AttributeBasedEncryption {

    /**
     * @param args the command line arguments
     */
    private static String curve_parameter = "type a\n"
			+ "q 87807107996633125224377819847540498158068831994142082"
			+ "1102865339926647563088022295707862517942266222142315585"
			+ "8769582317459277713367317481324925129998224791\n"
			+ "h 12016012264891146079388821366740534204802954401251311"
			+ "822919615131047207289359704531102844802183906537786776\n"
			+ "r 730750818665451621361119245571504901405976559617\n"
			+ "exp2 159\n" + "exp1 107\n" + "sign1 1\n" + "sign0 1\n";
        
    
    public static void setup(Public pub,Mask msk)
    {
        Element alpha, beta_inv;
        CurveParameters params = new DefaultCurveParameters().load(new ByteArrayInputStream(curve_parameter.getBytes()));     
        pub.pairingDesc = curve_parameter;
        pub.p = PairingFactory.getPairing(params);
        Pairing pairing = pub.p;
        pub.g = pairing.getG1().newElement();
        pub.f = pairing.getG1().newElement();
        pub.h = pairing.getG1().newElement();
        pub.gp = pairing.getG2().newElement();
        pub.g_hat_alpha = pairing.getGT().newElement();
        alpha = pairing.getZr().newElement();
        msk.beta = pairing.getZr().newElement();
        msk.g_alpha = pairing.getG2().newElement();
        alpha.setToRandom();
        msk.beta.setToRandom();
        pub.g.setToRandom();
        pub.gp.setToRandom();
        msk.g_alpha = pub.gp.duplicate();
        msk.g_alpha.powZn(alpha);
        beta_inv = msk.beta.duplicate();
        beta_inv.invert();
        pub.f = pub.g.duplicate();
        pub.f.powZn(beta_inv);
        pub.h = pub.g.duplicate();
        pub.h.powZn(msk.beta);
        pub.g_hat_alpha = pairing.pairing(pub.g, msk.g_alpha);
    }
    public static Private keyGeneration(Public pub,Mask msk,String[] attributes)
    {
        try
        {
            int i,len;
            Private prv = new Private();
            Element g_r, r, beta_inv;
            Pairing pairing;
            pairing = pub.p;
            prv.d = pairing.getG2().newElement();
            g_r = pairing.getG2().newElement();
            r = pairing.getZr().newElement();
            beta_inv = pairing.getZr().newElement();
            r.setToRandom();
            g_r = pub.gp.duplicate();
            g_r.powZn(r);
            prv.d = msk.g_alpha.duplicate();
            prv.d.mul(g_r);
            beta_inv = msk.beta.duplicate();
            beta_inv.invert();
            prv.d.powZn(beta_inv);
            len = attributes.length;
            prv.comps = new ArrayList<PrivateComponent>();
            for (i = 0; i < len; i++) 
            {
                PrivateComponent comp = new PrivateComponent();
                Element h_rp;
                Element rp;
                comp.attr = attributes[i];
                comp.d = pairing.getG2().newElement();
                comp.dp = pairing.getG1().newElement();
                h_rp = pairing.getG2().newElement();
                rp = pairing.getZr().newElement();
                elementFromString(h_rp, comp.attr);
                rp.setToRandom();
                h_rp.powZn(rp);
                comp.d = g_r.duplicate();
                comp.d.mul(h_rp);
                comp.dp = pub.g.duplicate();
                comp.dp.powZn(rp);
                prv.comps.add(comp);
            }
            return prv;
        }
        catch(Exception e)
        {
            System.out.println("Private Key Generation Error = " + e);
        }
        return null;
    }
    public static CipherKey encryption(Public pub, String policy)
    {
        try
        {
            CipherKey cipher_key = new CipherKey();
            Cipher cipher = new Cipher();
            Element s, m;
            Pairing pairing = pub.p;
            s = pairing.getZr().newElement();
            m = pairing.getGT().newElement();
            cipher.cs = pairing.getGT().newElement();
            cipher.c = pairing.getG1().newElement();
            cipher.p = parsePolicyPostfix(policy);
            m.setToRandom();
            s.setToRandom();
            cipher.cs = pub.g_hat_alpha.duplicate();
            cipher.cs.powZn(s);
            cipher.cs.mul(m);
            cipher.c = pub.h.duplicate();
            cipher.c.powZn(s);
            fillPolicy(cipher.p, pub, s);
            cipher_key.cph = cipher;
            cipher_key.key = m;
            System.out.println("m = " + m);
            return cipher_key;
        }
        catch(Exception e)
        {
            System.out.println("Encryptoin Part Error = " + e);
        }
        return null;
    }
            
    public static ElementBoolean decryption(Public pub,Private prv,Cipher cipher)
    {
        try
        {
            Element t,m;
            ElementBoolean beb=new ElementBoolean();
            m=pub.p.getGT().newElement();
            t=pub.p.getGT().newElement();
            checkSatisfy(cipher.p, prv);
            if (!cipher.p.satisfiable)
            {
                System.err.println("cannot decrypt, attributes in key do not satisfy policy");
                beb.e = null;
                beb.b = false;
                return beb;
            }
            pickSatisfyMinLeaves(cipher.p, prv);
            decFlatten(t, cipher.p, prv, pub);
            m = cipher.cs.duplicate();
            m.mul(t);
            t = pub.p.pairing(cipher.c, prv.d);
            t.invert();
            m.mul(t);
            beb.e = m;
            System.out.println("beb.e = " + beb.e);
            beb.b = true;
            
            return beb;
        }
        catch(Exception e)
        {
            System.out.println("Decryption formation error = " + e);
        }
        return null;
    }
    public static void decFlatten(Element r,Policy p,Private prv,Public pub)
    {
        try
        {
        Element one;
        one=pub.p.getZr().newElement();
        one.setToOne();
        r.setToOne();
        decNodeFlatten(r, one, p, prv, pub);
        }
        catch(Exception e)
        {
            System.out.println("dec flatten error = " + e);
        }
    }
    public static void decNodeFlatten(Element r,Element one,Policy p,Private prv,Public pub)
    {
        try
        {
        if(p.children==null || p.children.length==0)
        {
            decLeafFlatten(r,one,p,prv,pub);
        }
        else
        {
            decInternalFlatten(r,one,p,prv,pub);
        }
        }
        catch(Exception e)
        {
            System.out.println("node flatten error = " + e);
        }
    }
    public static void decLeafFlatten(Element r,Element one,Policy p,Private prv,Public pub)
    {
        PrivateComponent c;
        Element s,t;
        try
        {
        c=prv.comps.get(p.attri);
        s=pub.p.getGT().newElement();
        t=pub.p.getGT().newElement();
        
        s=pub.p.pairing(p.c,c.d);
        t=pub.p.pairing(p.cp,c.dp);
        t.invert();
        s.mul(t);
        s.powZn(one);
        r.mul(s);
        }
        catch(Exception e)
        {
            System.out.println("Leaf Flatten Error = " + e);
        }
    }
    public static void decInternalFlatten(Element r,Element one,Policy p,Private prv,Public pub)
    {
        int i;
        Element t,expnew;
        try
        {
        t = pub.p.getZr().newElement();
        expnew = pub.p.getZr().newElement();
        for (i = 0; i < p.satl.size(); i++) 
        {
            lagrangeCoef(t, p.satl, (p.satl.get(i)).intValue());
            expnew = one.duplicate();
            expnew.mul(t);
            decNodeFlatten(r, expnew, p.children[p.satl.get(i) - 1], prv, pub);
        }
        }
        catch(Exception e)
        {
            System.out.println("internal flatten error = " + e);
        }
    }
    public static void lagrangeCoef(Element r, ArrayList<Integer> s, int i) 
    {
        int j, k;
        Element t;
        try
        {
        t = r.duplicate();
        r.setToOne();
        for (k = 0; k < s.size(); k++) 
        {
            j = s.get(k).intValue();
            if (j == i)
                    continue;
            t.set(-j);
            r.mul(t);
            t.set(i - j);
            t.invert();
            r.mul(t);
        }
        }
        catch(Exception e)
        {
            System.out.println("language coeff error = " + e);
        }
    }
    public static void checkSatisfy(Policy p,Private prv)
    {
        int i,l;
        String private_attribute;
        p.satisfiable=false;
        if(p.children==null || p.children.length == 0)
        {
            for(i=0;i<prv.comps.size();i++)
            {
                private_attribute = prv.comps.get(i).attr;
            if(private_attribute.compareTo(p.attr)==0)
            {
                p.satisfiable=true;
                p.attri=i;
                break;
            }
            }
        }
        else
        {
            for(i=0;i<p.children.length;i++)
            {
                checkSatisfy(p.children[i],prv);
            }
            l = 0;
            for(i = 0;i < p.children.length;i++)
            {
                if (p.children[i].satisfiable)
                {
                    l++;
                }
            }
            if (l>=p.k)
            {
                p.satisfiable = true;
            }
        }
    }
    public static void pickSatisfyMinLeaves(Policy p,Private prv)
    {
        int i,k,l,c;
        int length;
        ArrayList<Integer> arr = new ArrayList<Integer>();
        if(p.children==null||p.children.length==0)
        {
            p.min_leaves = 1;
        }
        else
        {
           length=p.children.length;
           for(i=0;i<length;i++)
           {
               if(p.children[i].satisfiable)
               {
                   pickSatisfyMinLeaves(p.children[i], prv);
               }
           }
           for(i=0;i<length;i++)
           {
               arr.add(new Integer(i));
           }
           Collections.sort(arr, new AttributeBasedEncryption.IntegerComparator(p));
           p.satl=new ArrayList<Integer>();
           p.min_leaves=0;
           l=0;
           for(i=0;i<length&&l<p.k;i++)
           {
               c=arr.get(i).intValue();
               if(p.children[c].satisfiable)
               {
                   l++;
                   p.min_leaves+=p.children[c].min_leaves;
                   k=c+1;
                   p.satl.add(new Integer(k));
               }
           }
        }
    }
private static void fillPolicy(Policy p, Public pub, Element e)
    {
        try
        {
            int i;
            Element r, t, h;
            Pairing pairing = pub.p;
            r = pairing.getZr().newElement();
            t = pairing.getZr().newElement();
            h = pairing.getG2().newElement();
            p.q = randPoly(p.k - 1, e);
            if (p.children == null || p.children.length == 0)
            {
                p.c = pairing.getG1().newElement();
                p.cp = pairing.getG2().newElement();
                elementFromString(h, p.attr);
                p.c = pub.g.duplicate();;
                p.c.powZn(p.q.coef[0]);
                p.cp = h.duplicate();
                p.cp.powZn(p.q.coef[0]);
            } 
            else 
            {
                for (i = 0; i < p.children.length; i++) 
                {
                    r.set(i + 1);
                    evalPoly(t, p.q, r);
                    fillPolicy(p.children[i], pub, t);
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Fill Policy error = " + ex);
        }

	}

    private static void evalPoly(Element r, Polynomial q, Element x) 
    {
        int i;
        Element s, t;
        s = r.duplicate();
        t = r.duplicate();
        r.setToZero();
        t.setToOne();
        for (i = 0; i < q.deg + 1; i++) 
        {
            s = q.coef[i].duplicate();
            s.mul(t); 
            r.add(s);
            t.mul(x);
        }
    }
    private static Polynomial randPoly(int degree, Element zero_val) 
   {
        int i;
        Polynomial q = new Polynomial();
        q.deg = degree;
        q.coef = new Element[degree + 1];
        for (i = 0; i < degree + 1; i++)
        {
            q.coef[i] = zero_val.duplicate();
        }
        q.coef[0].set(zero_val);
        for (i = 1; i < degree + 1; i++)
        {
            q.coef[i].setToRandom();
        }
        return q;
    }

private static Policy parsePolicyPostfix(String s)
    {
        try
        {
            String[] toks;
            String tok;
            ArrayList<Policy> stack = new ArrayList<Policy>();
            Policy root;
            toks = s.split(" ");
            int toks_cnt = toks.length;
            for (int index = 0; index < toks_cnt; index++) 
            {
                int i, k, n;
                tok = toks[index];
                if (!tok.contains("of")) 
                {
                    stack.add(baseNode(1, tok));
                } 
                else 
                {
                    Policy node;
                    String[] k_n = tok.split("of");
                    k = Integer.parseInt(k_n[0]);
                    n = Integer.parseInt(k_n[1]);
                    if (k < 1) 
                    {
                            System.out.println("error parsing " + s+ ": trivially satisfied operator " + tok);
                            return null;
                    } else if (k > n) {
                            System.out.println("error parsing " + s+ ": unsatisfiable operator " + tok);
                            return null;
                    } else if (n == 1) {
                            System.out.println("error parsing " + s+ ": indentity operator " + tok);
                            return null;
                    } else if (n > stack.size()) {
                            System.out.println("error parsing " + s+ ": stack underflow at " + tok);
                            return null;
                    }
                    node = baseNode(k, null);
                    node.children = new Policy[n];
                    for (i = n - 1; i >= 0; i--)
                    {
                        node.children[i] = stack.remove(stack.size() - 1);
                    }
                    stack.add(node);
                    }
            }
            if (stack.size() > 1) 
            {
                System.out.println("error parsing " + s+ ": extra node left on the stack");
                return null;
            } 
            else if (stack.size() < 1) 
            {
                System.out.println("error parsing " + s + ": empty policy");
                return null;
            }
            root = stack.get(0);
            return root;
        }
        catch(Exception ex)
        {
            System.out.println("ex = " + ex);
        }
        return null;
    }
    private static Policy baseNode(int k, String s) 
    {
        Policy p = new Policy();
        p.k = k;
        if (!(s == null))
                p.attr = s;
        else
                p.attr = null;
        p.q = null;

        return p;
	}

    private static void elementFromString(Element h, String s)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(s.getBytes());
            h.setFromHash(digest, 0, digest.length);
        }
        catch(Exception e)
        {
            System.out.println("Message Digest Error = " + e);
        }
    }
private static class IntegerComparator implements Comparator<Integer> 
    {
	Policy policy;
        public IntegerComparator(Policy p) 
        {
            this.policy = p;
        }
        @Override
        public int compare(Integer a, Integer b) 
        {
                int k, l;
                k = policy.children[a.intValue()].min_leaves;
                l = policy.children[b.intValue()].min_leaves;
                
                return	k<l?-1:k==l?0:1;
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
