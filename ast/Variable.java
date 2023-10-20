package ast;

import environment.Environment;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * Represents a Variable
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Variable extends Expression
{
    private String varName;

    /**
     * Constructs a Variable
     * @param varName   The name of the variable
     */
    public Variable(String varName)
    {
        this.varName = varName;
    }
    
    /**
     * Gets the value of this Variable
     * @param env   The environment this statement is in
     * @return      The Expression value of the variable
     */
    public Expression eval(Environment env) throws TypeMismatch, VariableNotDefined
    {
        Expression val = env.getVariable(varName);
        if (val == null)
        {
            throw new VariableNotDefined(varName);
        }
        return val;
    }
}