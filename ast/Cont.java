package ast;

import environment.Environment;
import jumps.Continue;

/**
 * Represents a loop continueation statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Cont extends Statement
{
    @Override
    /**
     * Executes the continue by throwing a Continue
     * @param env  The Environment this statement is in
     * @throws Continue
     */
    public void exec(Environment env) throws Continue
    {
        throw new Continue();
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "CONTINUE";
    }
}