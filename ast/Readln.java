package ast;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
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
    public void exec(Environment env) throws LanguageException
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

    /**
     * Throws an UnsupportedOperationException because READLN is not implemented in the 
     * compiler
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter e) 
    {
        throw new UnsupportedOperationException("READLN not implemented in compiled pascal");
    }
}