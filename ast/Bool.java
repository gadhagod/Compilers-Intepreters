package ast;

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
}