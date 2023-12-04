package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a Bool 
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Bool extends Expression
{
    private boolean val;

    /**
     * Constructs a Bool given its Boolean value
     * @param val   The boolean value
     */
    public Bool(boolean val)
    {
        this.val = val;
    }

    /**
     * Gets the value of the boolean
     * @param env   The environment this boolean is in
     */
    public Boolean eval(Environment env)
    {
        return val;
    }

    /**
     * Throws an UnsupportedOperationException because Booleans 
     * aren't supported in the compiler
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter e) 
    {
        throw new UnsupportedOperationException("Booleans not implemented in compiled pascal");
    }
}