package ast;

import emitter.Emitter;
import environment.Environment;
import jumps.Break;

/**
 * Represents a loop breakage statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Brk extends Statement
{
    /**
     * Constructs a Break statement
     */
    public Brk() {}

    /**
     * Executes the break by throwing a Break
     * @param env  The Environment this statement is in
     * @throws Break
     */
    @Override
    public void exec(Environment env) throws Break
    {
        throw new Break();
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "BREAK";
    }

    /**
     * Throws an UnsupportedOperationException because breaks 
     * aren't supported in the compiler
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter e) 
    {
        throw new UnsupportedOperationException("BREAK not implemented in compiled pascal");
    }
}
