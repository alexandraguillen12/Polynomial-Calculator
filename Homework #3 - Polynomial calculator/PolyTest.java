import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * A class with unit tests for the Poly polynomial class.
 * 
 * We recommend you tackle them (i.e. try to make them pass) in the 
 * order they appear in this file.
 *
 * @author Anna Gommerstadt & Rui Meireles
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PolyTest{

    /**
     * Tests creation and equality testing of single-term polynomials.
     */
    @Test
    public void test1ConsAndEquals(){

        Poly p1 = new Poly(2.5, 2);
        Poly p2 = new Poly(2.5, 2);
        assertEquals("2.5x^2 != 2.5x^2", p1, p2);

        p2 = new Poly(2.5, 1);
        assertNotEquals("2.5x^2 == 2.5x", p1, p2);

        p2 = new Poly(3, 2);
        assertNotEquals("2.5x^2 == 3x^2", p1, p2);

        p2 = new Poly(4, 0);
        assertNotEquals("2.5x^2 == 4", p1, p2);

        p1 = new Poly(0, 4);
        p2 = new Poly(0, 0);
        assertEquals("0x^4 != 0", p1, p2);

        new Poly(2147483647, 2147483647); // designed to break space-inneficient array-based implementations!
    }

    /**
     * Tests polynomial addition and subtraction.
     * Assumes the following methods are correct: constructor, equals().
     */
    @Test
    public void test2AddAndSub(){

        // does add even do anything?
        Poly p1 = new Poly(2, 2); // p1 = 2x^2
        Poly p2 = new Poly(3, 0); // p2 = 3
        Poly p3 = p1.add(p2);     // p3 = 2x^2 + 3
        assertNotEquals("2x^2 + 3 == 2x^2", p3, p1);
   
        // adding negative-coefficient term
        p1 = new Poly(3, 2);          // p1 = 3x^2
        p1 = p1.add(new Poly(4, 1));  // p1 = 3x^2 + 4x
        p1 = p1.add(new Poly(-3, 2)); // p1 = 3x^2 + 4x - 3x^2
        p2 = new Poly(4, 1);          // p2 = 4x
        assertEquals("3x^2 + 4x - 3x^2 != 4x", p2, p1);
        
        // testing monomials with same exponent
        p1 = new Poly(1, 1); // p1 = x
        p2 = new Poly(3, 1); // p2 = 3x
        p3 = p1.add(p2);     // p3 = x + 3x
        Poly p4 = new Poly(4, 1); // p4 = 4x
        assertEquals("3x + x != 4x", p4, p3);

        p4 = p3.subtract(p2); // p4 = 4x - 3x
        assertEquals("4x - 3x != x", p1, p4);
          
        // testing monomials with different exponents #1
        p1 = new Poly(3, 2);         // p1 = 3x^2
        p1 = p1.add(new Poly(4, 1)); // p1 = 3x^2 + 4x
        p2 = new Poly(3, 2);         // p2 = 3x^2
        p1 = p1.subtract(p2);        // p1 = 3x^2 + 4x - 3x^2
        p2 = new Poly(4, 1);
        assertEquals("3x^2 + 4x - 3x^2 != 4x", p2, p1);
        
        // testing monomials with different exponents #2
        p1 = new Poly(2, 1); // p1 = 2x
        p2 = new Poly(3, 0); // p2 = 3
        p3 = p1.add(p2); // p3 = 2x + 3
        p4 = p3.subtract(p2); // p3 = 2x
        assertEquals("2x + 3 - 3 != 2x", p1, p4);
        
        // subtract poly from itself should yield zero result
        p4 = p3.subtract(p3); // p3 = 2x + 3
        p1 = new Poly(0, 0);  // p1 = 0
        assertEquals("2x + 3 - 2x + 3 != 0", p1, p4);
        
        p1 = new Poly(1, 2); // p1 = x^2
        p2 = new Poly(2, 1); // p2 = 2x
        p1 = p1.add(p2);     // p1 = x^2 + 2x
        
        p2 = new Poly(3, 1); // p2 = 3x
        p3 = new Poly(4, 0); // p3 = 4
        p2 = p2.add(p3);     // p2 = 3x + 4
        
        p3 = new Poly(1, 2);         // p3 = x^2
        p3 = p3.add(new Poly(5, 1)); // p3 = x^2 + 5x
        p3 = p3.add(new Poly(4, 0)); // p3 = x^2 + 5x + 4
        
        p4 = p1.add(p2); // p4 = x^2 + 5x + 4
        assertEquals("(x^2 + 2x) + (3x + 4) != x^2 + 5x + 4", p3, p4);
        
        // test addition with zero
        p1 = new Poly(2, 1);
        p2 = new Poly(2, 1);
        p2 = p2.add(new Poly(0, 0));
        p3 = new Poly(4, 1);
        p4 = p1.add(p2);
        assertEquals("(2x) + (2x + 0) != 4x", p3, p4);
        
        // designed to break fixed-size array-based implementations by using a lot of terms
        p1 = new Poly(0, 0);
        p2 = new Poly(0, 0);
        final int nterms = 10000;
        for (int i=0; i <= nterms; i++){
            final int j = nterms - i;
            p1 = p1.add(new Poly(i, i));
            p2 = p2.add(new Poly(j, j));
        }
        assertEquals("1x^1 + 2x^2 + 3x^3 + ... + 10000x^50000 != 10000x^10000 + ... + 3x^3 + 2x^2 + 1x^1", p1, p2);
        
    }

    /**
     * Tests polynomial multiplication.
     * Assumes the following methods are correct: constructor, equals(), add(). 
     */
    @Test
    public void test3Mul() {

        // multiply by zero should yield zero result
        Poly z0 = new Poly(0,0); // z0 = 0
        Poly z1 = new Poly(2, 2); // z1 = 2x^2
        Poly z2 = z1.multiply(z0); // z2 = 0

        // single term by 0
        assertEquals("2x^2 * 0 != 0", z0, z2);
        // zero by single term
        z2 = z0.multiply(z1); // z2 = 0
        assertEquals("0 * 2x^2 != 0", z0, z2);

        // multiple terms by zero
        z1 = z1.add(new Poly(1, 1)); // z1 = 2x^2 + x
        z2 = z1.multiply(z0); // z2 = 0
        assertEquals("2x^2 + x * 0 != 0", z0, z2);
        // zero by multiple terms
        z2 = z0.multiply(z1); // z2 = 0
        assertEquals("0 * 2x^2 + x != 0", z0, z2);

        // multiply multi-term poly by single term poly
        Poly p1 = new Poly(1,2); // p1 = x^2
        Poly p2 = new Poly(5,0); // p2 = 5
        Poly p3 = p1.add(p2); // p3 = x^2 + 5

        Poly p4 = new Poly(2,1); // p4 = 2x
        Poly p5 = p3.multiply(p4); // p5 = 2x^3 + 10x

        Poly p6 = new Poly(2,3); // p6 = 2x^3
        Poly p7 = new Poly(10,1); // p7 = 10x
        Poly res = p6.add(p7); // res = 2x^3 + 10x
        assertEquals("(x^2 + 5) * 2x != 2x^3 + 10x", res, p5); 

        Poly p8 = p1.add(p7);  // p8 = x^2 + 10x 
        assertNotEquals("(x^2 + 5) * 2x) == x^2 + 10x", p8, p5); 

        // multiply multi-term poly by another multi-term poly
        Poly p11 = new Poly(1,2); // p1 = x^2
        Poly p12 = new Poly(5,1); // p2 = 5x
        Poly p13 = new Poly(3,0); // p3 = 3
        Poly p14 = p11.add(p12).add(p13); // p4 = x^2 + 5x + 3
        Poly p15 = new Poly(7,1); // p5 = 7x
        Poly p16 = p15.add(p13); // p6 = 7x + 3
        Poly p17 = new Poly(7,3); // p7 = 7x^3
        Poly p18 = new Poly(38,2); // p8 = 38x^2
        Poly p19 = new Poly(36,1); // p9 = 36x
        Poly p20 = new Poly(9,0); // p10 = 9
        Poly mult = p16.multiply(p14);
        Poly res2 = p17.add(p18).add(p19).add(p20); // res = 7x^3 + 38x^2 + 36x + 9
        assertEquals("(x^2 + 5x + 3) * (7x + 3) != 7x^3 + 38x^2 + 36x + 9", res2, mult);

        // multiply by -1
        Poly s1 = new Poly(3, 2); // s1 = 3x^2
        s1 = s1.add(new Poly(-4, 0)); // s1 = 3x^2 - 4
        Poly s2 = s1.multiply(new Poly(-1, 0)); // s1 = -3x^2 - 4
        s1 = s1.add(s2); // s1 = 0
        assertEquals("(3x^2 - 4 * -1) + 3x^2 - 4 != 0", z0, s1);

        // tests merging of same-exponent terms
        Poly t1 = new Poly(3, 3); // t1 = 3x^3
        t1.add(new Poly(3,1)); // t1 = 3x^3 + 3x
        t1.add(new Poly(3,0)); // t1 = 3x^3 + 3x + 3
        Poly t2 = new Poly(3, 3); // t2 = 3x^3
        t2.add(new Poly(3, 2)); // t2 = 3x^3 + 3x^2
        t2.add(new Poly(1,1)); // t2 = 3x^3 + 3x^2 + x
        Poly t3 = t1.multiply(t2); // t3 = 9x^6 + 9x^5 + 12x^4 + 18x^3 + 12x^2 + 3x

        // build result to test against
        Poly t4 = new Poly(9, 6); // t4 = 9x^6
        t4.add(new Poly(9, 5)); // t4 = 9x^6 + 9x^5
        t4.add(new Poly(12, 4)); // t4 = 9x^6 + 9x^5 + 12x^4
        t4.add(new Poly(18, 3)); // t4 = 9x^6 + 9x^5 + 12x^4 + 18x^3
        t4.add(new Poly(12, 2)); // t4 = 9x^6 + 9x^5 + 12x^4 + 18x^3 + 12x^2
        t4.add(new Poly(3, 1)); // t4 = 9x^6 + 9x^5 + 12x^4 + 18x^3 + 12 x^2 + 3x

        // compare outcome against expected value
        assertEquals("(3x^3 + 3x + 3) * (3x^3 + 3x^2 + x) != 9x^6 + 9x^5 + 12x^4 + 18x^3 + 12x^2 + 3x", t4, t3);
    }

    /**
     * Tests polynomial division.
     * Assumes the following methods are correct: constructor, equals(), add(), multiply().
     */
    @Test
    public void test5MulDiv() {

        // zero dividend divided by anything
        Poly z1 = new Poly(0,0); // z1 = 0
        Poly z2 = z1.divide(new Poly(1,1)); // z2 = 0 / x
        assertEquals("0 / x != 0", z1, z2);

        // divide by zero
        z1 = new Poly(1,2); // z1 = x^2
        z2 = z1.divide(new Poly(0,0)); // z2 = x^2 / 0
        assertEquals("x^2 / 0 != null", null, z2);
        
        // divide zero by zero
        z1 = new Poly(1,2); // z1 = 0
        z2 = z1.divide(new Poly(0,0)); // z2 = 0 / 0
        assertEquals("0 / 0 != null", null, z2);

        // divide by one
        Poly o1 = new Poly(3,2); // o1 = 3x^2
        o1 = o1.add(new Poly(4, 0)); // o1 = 3x^2 + 4
        Poly o2 = o1.divide(new Poly(1,0)); // o2 = (3x^2 + 4) / 1
        assertEquals("(3x^2 + 4) / 1 != 3x^2 + 4", o1, o2);
        
        // simple indivisible test
        o1 = new Poly(3,2); // o1 = 3x^2
        o2 = o1.divide(new Poly(9,5)); // o2 = 9x^5 / 3x^2
        assertEquals("9x^5 / 3x^2 != null", null, o2);

        Poly p1 = new Poly(1,2); // p1 = x^2
        Poly p2 = new Poly(-9,0); // p2 = -9
        Poly p3 = p1.add(p2); // p3 = x^2 - 9
        Poly p4 = new Poly(1,1); // p4 = x
        Poly p5 = new Poly(-3,0); // p5 = -3
        Poly p6 = p4.add(p5); // p6 = x - 3
        Poly q = p3.divide(p6); // q = x + 3
        Poly m = q.multiply(p6); // m = x^2 - 9
        Poly u = p1.divide(q);  // u = null
        assertEquals("(x^2 - 9) / (x - 3) != x + 3", p3, m);
        assertEquals("(x^2 - 9) / x != null", null, u);

        Poly p11 = new Poly(9,0);  // p1 = 9
        Poly p12 = new Poly(6,1);  // p2 = 6x
        Poly p13 = new Poly(1,2);  // p3 = x^2
        Poly p14 = p11.add(p12).add(p13); // p4 = 9 + 6x + x^2
        Poly p15 = new Poly(3,0); // p5 = 3
        Poly p16 = new Poly(1,1); // p6 = x
        Poly p17 = p15.add(p16); // p7 = x + 3
        Poly q2 = p14.divide(p17); // q = x - 3
        Poly m2 = p17.multiply(q2); // m = x^2 + 6x + 9
        assertEquals("(9 + 6x + x^2) / (x + 3) != x - 3", p14, m2);
        Poly u2 = p6.divide(p17); // u = null
        assertEquals("x/3 != null", null, u2);
        

    }

    /**
     * A helper method to check wheter a polynomial's toString() returns a string
     * in the correct format.
     * 
     * @param polyStr the string that should be returned by polynomial toString().
     * @param p the polynomial to be tested.
     * @return an error message.
     */
    private void testToStringHelper(String polyStr, Poly p){
        String emsg = String.format("%s toString() incorrect", polyStr);
        assertEquals(emsg, polyStr, p.toString());
    }

    /**
     * Tests polynomial toString().
     * Assumes the following methods are correct: constructor, add().
     */
    @Test
    public void test5ToString() {

        // printing zero polynomial
        Poly p1 = new Poly(0,0); // p1 = 0
        String s = "0.0";
        this.testToStringHelper(s, p1);
        
        // printing polynomial 1
        p1 = new Poly(1,0); // p1 = 0
        s = "1.0";
        this.testToStringHelper(s, p1);
        
        // printing polynomial -1
        p1 = new Poly(-1,0); // p1 = 0
        s = "-1.0";
        this.testToStringHelper(s, p1);
        
        // printing polynomials with different kinds of terms
        p1 = new Poly(2.0, 4); // p1 = 2.0x^4
        s = "2.0x^4";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(3.4, 3)); // p1 = 2.0x^4 + 3.4x^3
        s += " + 3.4x^3";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(1.0, 2)); // p1 = 2.0x^4 + 3.4x^3 + x^2
        s += " + x^2";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(1.0, 1)); // p1 = 2.0x^4 + 3.4x^3 + x^2 + x
        s += " + x";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(4, 0)); // p1 = 2.0x^4 + 3.4x^3 + x^2 + x + 4.0
        s += " + 4.0";
        this.testToStringHelper(s, p1);

        // adding out of order to test sorting
        p1 = p1.add(new Poly(50, 40)); // p1 = 50.0x^40 + 2.0x^4 + 3.4x^3 + x^2 + x + 4.0
        s = "50.0x^40 + " + s; 
        this.testToStringHelper(s, p1);

        // adding zero coefficient terms shouldn't affect printing
        p1 = p1.add(new Poly(0, 40)); // p1 = 50.0x^40 + 2.0x^4 + 3.4x^3 + x^2 + x + 4
        this.testToStringHelper(s, p1);

        // printing polynomial with negative coefficients 
        p1 = new Poly(-1, 3); // p1 = -x^3
        s = "-x^3";
        this.testToStringHelper(s, p1);
        
        p1 = new Poly(-2, 3); // p1 = -2.0x^3
        s = "-2.0x^3";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(-2.0, 2)); // p1 = -x^3 - 2.0x^2
        s += " - 2.0x^2";
        this.testToStringHelper(s, p1);

        p1 = p1.add(new Poly(1, 1)); // p1 = -x^3 - 2.0x^2 + x
        String olds = s;
        s += " + x";
        this.testToStringHelper(s, p1);

        // killing a term should make it disappear
        p1 = p1.add(new Poly(-1, 1)); // p1 = -x^3 - 2.0x^2 
        this.testToStringHelper(olds, p1);
    }

    /**
     * Makes polynomials, calls methods on them, and evaluates their return values.
     * Assumes the following methods are correct: constructor, equals(), add(), subtract(), multiply(), and divide().
     */
    @Test
    public void test5MyTests() {
        
        // add monomials/polynomials with same and different exponents
        Poly p1 = new Poly(1.2, 3); // p1 = 1.2x^3
        Poly p2 = new Poly(1.3, 3); // p2 = 1.3x^3
        p1 = p1.add(p2); // p1 = 2.5x^3
        Poly p3 = new Poly(4, 2); // p3 = 4x^2
        p1 = p1.add(p3); // p1 = 2.5x^3 + 4x^2
        Poly p4 = new Poly(-0.3, 2); // p4 = -0.3x^2
        p1 = p1.add(p4); // p1 = 2.5x^3 + 3.7x^2
        Poly p5 = new Poly(2.5, 3); // p5 = 2.5x^3
        Poly p6 = new Poly(3.7, 2); // p6 = 3.7x^2
        p5 = p5.add(p6); // p5 = 2.5x^3 + 3.7x^2
        assertEquals("1.2x^3 + 1.3x^3 + 4x^2 + -0.3x^2 != 2.5x^3 + 3.7x^2", p1, p5);
        
        // subtract two monomials and subtract resulting poly from itself
        Poly p7 = new Poly(2.5, 3); // p7 = 2.5x^3
        Poly p8 = new Poly(-3.7, 2); // p8 = -3.7x^2
        p7 = p7.subtract(p8); // p7 = 2.5x^3 + 3.7x^2
        Poly p9 = p7.subtract(p7); // p9 = 0
        Poly p10 = new Poly(0, 0);  // p8 = 0
        assertEquals("2.5x^3 + 3.7x^2 - 2.5x^3 + 3.7x^2 != 0", p9, p10);
        
        // multiply polynomial by another polynomial; then multiply result by zero
        Poly p11 = new Poly(5,2); // p1 = 5x^2
        Poly p12 = new Poly(7,1); // p2 = 7x
        Poly p13 = new Poly(2,0); // p3 = 2
        Poly p14 = p11.add(p12).add(p13); // p4 = 5x^2 + 7x + 2
        Poly p15 = new Poly(-3,1); // p5 = -3x
        Poly p16 = p15.add(p13); // p6 = -3x + 2
        Poly p17 = new Poly(-15,3); // p7 = -15x^3
        Poly p18 = new Poly(-11,2); // p8 = -11x^2
        Poly p19 = new Poly(8,1); // p9 = 8x
        Poly p20 = new Poly(4,0); // p10 = 4
        Poly mult = p16.multiply(p14);
        Poly res = p17.add(p18).add(p19).add(p20); // res = -15x^3 + -11x^2 + 8x + 4
        assertEquals("(5x^2 + 7x + 2) * (-3x + 2) != -15x^3 + -11x^2 + 8x + 4", res, mult);
        res = res.multiply(p10); // p10 = 0
        assertEquals("(-15x^3 + -11x^2 + 8x + 4) * 0 ! = 0", res, p10);
        
        // divide by one, divide poly by poly, divide by zero
        Poly o1 = new Poly(1,2); // o1 = x^2
        o1 = o1.add(new Poly(8, 1)); // o1 = x^2 + 8x
        o1 = o1.add(new Poly(16, 0)); // o1 = x^2 + 8x + 16
        Poly o2 = o1.divide(new Poly(1,0)); // o2 = (x^2 + 8x + 16) / 1
        assertEquals("(x^2 + 8x + 16) / 1 != x^2 + 8x + 16", o1, o2);
        Poly o3 = new Poly(1, 1); // o3 = x
        o3 = o3.add(new Poly(4, 0)); // o3 = x + 4
        o2 = o2.divide(o3); // o2 = (x^2 + 8x + 16) / (x + 4) 
        assertEquals("(x^2 + 8x + 16) / (x + 4) != x + 4", o2, o3);
        o1 = new Poly(2.3,7); // o1 = 2.3x^7
        o2 = o1.divide(new Poly(0,0)); // o2 = 2.3x^7 / 0
        assertEquals("2.3x^7 / 0 != null", null, o2);
    }
}
