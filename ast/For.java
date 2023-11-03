
package ast;

import environment.Environment;
import exceptions.LanguageException;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;
import jumps.Break;
import jumps.Continue;
import jumps.Jump;

/**
 * Represents a for loop
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class For extends Statement
{
    private String currName;
    private Expression max;
    private Statement _do;
    private boolean done;

    /**
     * Constucts a For loop statement
     * @param startName   The name of the iterative variable
     * @param max         The value of the startName to stop
     *                    iteration at
     * @param _do         The body statement of the for loop
     */
    public For(String startName, Expression max, Statement _do)
    {
        currName = startName;
        this.max = max;
        this._do = _do;
    }

    /**
     * Adds 1 to the value of the iterative variable
     * @param env  The Environment this statement is in  
     * @throws TypeMismatch
     * @throws VariableNotDefined
     * @throws Break
     */
    private void addCount(Environment env) throws LanguageException
    {
        Expression currExpr = env.getVariable(currName);
        Expression maxExpr = max;
        while (!(currExpr instanceof Number))
        {
            currExpr = (Expression) currExpr.eval(env);
        }
        Number currNum = (Number) currExpr;
        while (!(max instanceof Number))
        {
            maxExpr = (Expression) max.eval(env);
        }
        Number maxNum = (Number) maxExpr;
        currNum.add(1);
        done = currNum.eval(env) > maxNum.eval(env);
    }

    /**
     * Executes the for loop
     * @param env           The Environment this statement is in
     * @throws TypeMismatch
     * @throws VariableNotDefined
     * @precondition        The variable with name startName is declared
     */
    public void exec(Environment env) throws LanguageException, Jump
    {
        try 
        {
            while (!done)
            {
                try 
                {
                    _do.exec(env);
                }
                catch (Continue err)
                {}
                catch (Break err)
                {}
                addCount(env);
            }
        }
        catch (Break err)
        {}
    }

    /**
     * Gets a String representation of the for loop
     * @param env   The Environmnet this statement is in
     * @return      The Strign representation of teh loop
     * @throws VariableNotDefined
     * @throws TypeMismatch
     */
    public String toString(Environment env) throws LanguageException
    {
        return "FOR " + currName + " < " + max.toString(env);
    }
}
