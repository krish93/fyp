/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bswabe;

import it.unisa.dia.gas.jpbc.Element;
import java.util.ArrayList;

/**
 *
 * @author Harikrish
 */
public class Policy {
    int k;
    String attr;
    Element c;
    Element cp;
    Policy[] children;
    Polynomial q;
    public boolean satisfiable;
    int min_leaves;
    int attri;
    ArrayList<Integer> satl = new ArrayList<Integer>();
}
