package ast;

import environment.Environment;
import exceptions.LanguageException;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;
import jumps.Break;
import jumps.Continue;
import jumps.Jump;

/**
 * Represents a while statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class While extends Statement
{
    private Expression condition;
    private Statement _do;

    /**
     * Contructs a While 
     * @param condition The condition to be satisfied
     * @param _do       The body of the while loop
     */
    public While(Expression condition, Statement _do)
    {
        this.condition = condition;
        this._do = _do;
    }

    /**
     * Gets the Boolean representation of condition
     * @param env   The environment this statement is in
     * @return      The Boolean representation of the condition
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    private boolean evalCond(Environment env) throws LanguageException
    {
        Object val = condition;
        while (val instanceof Expression)
        {
            val = ((Expression) val).eval(env);
        }
        return ((Boolean) val);
    }

    /**
     * Executes the while loop until the condition is not met
     * @param env   The environment this statement is in
     * @throws LanguageException
     */
    @Override
    public void exec(Environment env) throws LanguageException
    {
        try 
        {
            while (evalCond(env))
            {
                try 
                {
                    _do.exec(env);
                }
                catch (Continue err)
                {}
                catch (Jump err)
                {
                    throw (Break) (err);
                }
            }
        }
        catch (Break err)
        {}
    }
    
    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "WHILE " + condition;
    }
}
