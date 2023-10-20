package ast;

import environment.Environment;

/**
 * Represents a String
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Str extends Expression
{
    private String val;

    /**
     * Constructs a Str with its String value
     * @param val   The String value of the string
     */
    public Str(String val)
    {
        this.val = val;
    }

    /**
     * Gets the String value of the Str
     * @param env   The environment this statement is in
     * @return      The String value of the Str        
     */
    public String eval(Environment env)
    {
        return val;
    }
}
