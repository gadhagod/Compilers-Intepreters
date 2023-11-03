package ast;

import environment.Environment;
import exceptions.LanguageException;

/**
 * Represents an expression
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public abstract class Expression 
{
    /**
     * Evaluates the expression
     * @param env   The environment this expression is in
     * @return      The value of the expression
     */
    public abstract Object eval(Environment env) throws LanguageException;

    /**
     * Gets a string representation of the value of the expression
     * @param env   The environment this expression is in
     */
    public String toString(Environment env) throws LanguageException
    {
        return eval(env).toString();
    };
}
