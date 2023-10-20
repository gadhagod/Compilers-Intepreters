package ast;

import environment.Environment;
import exceptions.LanguageException;
import jumps.Jump;

/**
 * Represents a Pascal statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public abstract class Statement 
{
    /**
     * Executes this statement
     * @param env                   The environment this statement is in
     * @throws LanguageException    
     * @throws Jump
     */
    public abstract void exec(Environment env) throws LanguageException, Jump;

    /**
     * Gets the String representation statement
     * @param env                   The environment this statement is in
     * @return                      The String representation statement
     * @throws LanguageException
     * @throws Jump
     */
    public abstract String toString(Environment env) throws LanguageException, Jump;
}
