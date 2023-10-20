package ast;

import environment.Environment;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * Represents a READLN statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Readln extends Statement
{
    private static java.util.Scanner scanner;
    private String varName;

    /**
     * Reads input for READLN
     * @returns The input
     */
    private Str readLine()
    {   
        if (scanner == null)
        {
            scanner = new java.util.Scanner(System.in);
        }
        return new Str(scanner.nextLine());
    }
    
    /**
     * Constructs a READLN statement
     * @parma var   The name of the variable to store the value
     *              of the line in
     */
    public Readln(String varName)
    {
        this.varName = varName;
    }

    /**
     /**
     * Executes the READLN
     * @param env  The Environment this statement is in
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    @Override
    public void exec(Environment env) throws TypeMismatch, VariableNotDefined
    {
        env.setVariable(varName, readLine());
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "READLN -> " + varName;
    }
}