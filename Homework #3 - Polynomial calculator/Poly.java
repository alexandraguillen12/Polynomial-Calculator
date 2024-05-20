import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 * This class is a skeleton. You need to provide implementations
 * for the methods here defined. Feel free however, to add additional
 * methods as you see fit.
 *
 * @author Alexandra Guillen
 * @version 1.0
 */
public class Poly{

    HashMap<Integer,Double> map = new HashMap<>();

    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * If the coefficient is 0, a polynomial is not created.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){

        if (coef != 0) map.put(exp, coef);
        
    }

    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        Poly res = new Poly(0,0);

        Set<Integer> keys = map.keySet();
        for(int i: keys){
            res.map.put(i, map.get(i)); 
        } 

        Set<Integer> pkeys = p.map.keySet();
        for(int i: pkeys){
            if (res.map.get(i) != null){
                double newval = res.map.get(i) + p.map.get(i);
                if (newval != 0)res.map.put(i, newval);
                else res.map.remove(i);
            }
            else res.map.put(i, p.map.get(i));
        }

        return res;
    }

    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        Poly res = new Poly(0,0);

        Set<Integer> keys = map.keySet();
        for(int i: keys){
            res.map.put(i, map.get(i)); 
        } 

        Set<Integer> pkeys = p.map.keySet();
        for(int i: pkeys){
            if (res.map.get(i) != null){
                double newval = res.map.get(i) - p.map.get(i);
                if (newval != 0)res.map.put(i, newval);
                else res.map.remove(i);
            }
            else res.map.put(i, p.map.get(i) * -1);
        }

        return res;
    }

    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        Poly res = new Poly(0,0);

        Set<Integer> keys = map.keySet();
        Set<Integer> pkeys = p.map.keySet();
        
        for(int i: pkeys){
            for(int j: keys){
                double newval = map.get(j) * p.map.get(i);
                int newkey = j + i;
                if (newval != 0){ 
                    Poly mon = new Poly(newval, newkey);
                    res = res.add(mon);
                }
            }
        }

        return res;
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){
        if (p.toString().equals("0.0")) return null;
        else if (this.toString().equals("0.0")) return new Poly(0,0);
        else if (this.equals(p)) return new Poly(1,0);
        else if (p.toString().equals("1.0")) return this;

        Poly res = new Poly(0,0);
        Poly remainder = new Poly(0,0);

        Set<Integer> keys = map.keySet();
        for(int i: keys){
            remainder.map.put(i, map.get(i)); 
        }

        ArrayList<Integer> pkeyArray = new ArrayList<Integer>(p.map.keySet());
        int pkey = pkeyArray.get(pkeyArray.size() - 1);
        double pvalue = p.map.get(pkey);
        
        while (true){ 
            ArrayList<Integer> keyArray = new ArrayList<Integer>(remainder.map.keySet());
            int key = keyArray.get(keyArray.size() - 1);
            double value = remainder.map.get(key);
            
            if (key < pkey) break;
            
            double newval = value / pvalue;
            int newkey = key - pkey;
            Poly mon = new Poly(newval, newkey);
            res = res.add(mon);
            
            remainder = remainder.subtract(p.multiply(mon));
        
            if (remainder.equals(new Poly(0,0))) break;
        }
        
        if (!remainder.equals(new Poly(0,0))) return null;
        
        return res;
    }

    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){
        if (!(o instanceof Poly))
            return false;
        Poly p2 = (Poly) o;

        if (p2.map.size() != this.map.size()) return false;
        Set<Integer> keys = map.keySet();
        for(int i: keys){
            if (p2.map.get(i) == null ||
            !p2.map.get(i).equals(this.map.get(i))) 
                return false;
        }

        return true;
    }

    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    public String toString(){
        String polynomial = "";

        ArrayList<Integer> keys = new ArrayList<Integer>(map.keySet());
        
        for(int i=keys.size() - 1; i >= 0; i--){
            int key = keys.get(i);
            double value = map.get(key);
            double absvalue = Math.abs(value);

            if (!polynomial.equals("")){
                if (value > 0) polynomial += " + ";
                else if (value < 0) polynomial += " - ";
            }
            else{
                if (value < 0) polynomial += "-";
            }

            if (value == 0) continue;
            else if (key == 0) polynomial += absvalue + "";
            else if (key == 1 && absvalue != 1) polynomial += absvalue + "x";
            else if (key == 1 && absvalue == 1) polynomial += "x";
            else if (absvalue == 1) polynomial += "x^" + key;
            else if (key > 1) polynomial += absvalue + "x^" + key;
        }

        if (polynomial.equals("")) polynomial = "0.0";

        return polynomial;
    }
}
