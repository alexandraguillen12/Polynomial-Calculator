import java.util.Scanner;

/**
 * A class that represents a polynomial calculator. It adds, subtract, multiplies, and divides polynomials.
 *
 * @author Alexandra Guillen
 * @version 1.0
 */
public class PolyCalc
{

    /**
     * Executes when the program is executed through the command line with java PolyCalc. I.e., the 
     * program’s main method is a part of a class called PolyCalc.
     * 
     * Prints a welcome message.
     * 
     * Asks the user for the first polynomial. Polynomials will be specified using space separated pairs of 
     * coefficient and exponents. For example, 2.5x^2 - 1 should be input as 2.5 2 -1 0. If the user doesn’t 
     * provide a valid polynomial, they should be asked again, until they do.
     * 
     * Asks the user for the operation. The possible choices are: +, -, * or /. If the user doesn’t provide 
     * a valid operation, they should be asked again, until they do.
     * 
     * Asks the user for the second polynomial.
     * 
     * Executes the selected operation on the two polynomials and display the result. Print "indivisible" if the 
     * operation is division and the two polynomials are not divisible.
     * 
     * The user can enter quit at any time to exit the program.
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        PolyCalc pc = new PolyCalc();

        System.out.println("\n/*** Welcome to Alexandra Guillen’s polynomial calculator! ***/");
        System.out.println("Instructions: ");
        System.out.println(" - Polynomials are specified using space-separated pairs of coefficient and exponent.");
        System.out.println("   E.g.," + "\"" + "2.5x^2 - 1" + "\"" + " should be input as " + "\"" + "2.5 2 -1 0" + "\"" + ".");
        System.out.println(" - " + "\"" + "quit" + "\"" + " can be used at any time to exit the program.");

        int i = 1;
        while(true){
            System.out.println("\n--- Computation #" + i + " ---");

            Poly polyA = pc.validPoly("a");

            if(polyA == null) break;

            System.out.println("Read a = " + polyA.toString());

            String op;
            while(true){
                System.out.print("Operation (+, -, *, /): ");
                op = scanner.nextLine();
                if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/") && !op.toLowerCase().equals("quit")){
                    System.out.println("Invalid operation. Only listed ones supported.");
                }
                else break;
            }

            if (op.toLowerCase().equals("quit")){
                break;
            }

            Poly polyB = pc.validPoly("b");
            if(polyB == null) break;
            System.out.println("Read b = " + polyB.toString());
            if (op.equals("+"))
                System.out.println("\n("+ polyA.toString() + ") + (" + polyB.toString() + ") = " + polyA.add(polyB).toString());
            else if (op.equals("-"))
                System.out.println("\n("+ polyA.toString() + ") - (" + polyB.toString() + ") = " + polyA.subtract(polyB).toString());
            else if (op.equals("*"))
                System.out.println("\n("+ polyA.toString() + ") * (" + polyB.toString() + ") = " + polyA.multiply(polyB).toString());
            else if (op.equals("/")){
                Poly quotient = polyA.divide(polyB);
                if (quotient == null)
                    System.out.println("\n("+ polyA.toString() + ") / (" + polyB.toString() + ") = indivisible");
                else 
                    System.out.println("\n("+ polyA.toString() + ") / (" + polyB.toString() + ") = " + quotient.toString());
            }

            i++;
        }

        System.out.println("\nThank you, and goodbye.");
        return;
    }

    /**
     * Checks if the input polynomial is valid, asks again if not.
     * @return a valid polynomial
     */
    public Poly validPoly(String p) throws java.util.InputMismatchException{
        Scanner scanner = new Scanner(System.in);
        boolean isValid = false;

        Poly validPoly = new Poly(0,0);
        while(!isValid){
            validPoly = new Poly(0,0);
            System.out.print("Polynomial " + p + ": ");
            String polyLine = scanner.nextLine();

            if (polyLine.toLowerCase().equals("quit"))
                return null;

            Scanner polyScanner = new Scanner(polyLine);  

            while(polyScanner.hasNext()){
                try{
                    double nextCoef = polyScanner.nextDouble();

                    if (!polyScanner.hasNext()){
                        System.out.println("Invalid polynomial input! Please refer to the instructions.");
                        isValid = false;
                        break;
                    }
                    
                    int nextExp = polyScanner.nextInt();
                    if (nextExp < 0) throw new java.util.InputMismatchException();
                    
                    Poly nextTerm = new Poly(nextCoef, nextExp);
                    validPoly = validPoly.add(nextTerm);
                    isValid = true;
                } catch(java.util.InputMismatchException e){
                    System.out.println("Invalid polynomial input! Please refer to the instructions.");
                    isValid = false;
                    break;
                }
            }
        }

        return validPoly;
    }
}
