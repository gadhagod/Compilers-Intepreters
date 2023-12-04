package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Represents a numerical value
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Number extends Expression
{
    private int val;

    /**
     * Constructs a number given ints int value
     * @param val   The value of the number
     */
    public Number(int val)
    {
        this.val = val;
    }

    /**
     * Gets the Integer value of the number
     * @param env   The environment this expression is in
     * @return      The int value of the numher
     */
    public Integer eval(Environment env)
    {
        return val;
    }

    /**
     * Gets the String representation of the number
     * @param env   The environment this expression is in
     * @return      The string value of the number
     */
    @Override
    public String toString(Environment env)
    {
        return eval(env).toString();
    }

    /**
     * Gets the String representation of the number
     * @return      The string value of the number
     */
    public String toString()
    {
        return Integer.valueOf(val).toString();
    }

    /**
     * Adds one to this Number
     * @param count  The amount to add to this Number
     */
    public void add(int count)
    {
        val = val + count;
    }

    /**
     * Stores the value of the Number into $v0
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0, " + val);
    } 
}
