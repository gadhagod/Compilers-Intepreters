package environment;

import java.util.HashMap;
import java.util.HashSet;

import ast.BinOp;
import ast.Expression;
import ast.Variable;
import ast.ProcedureDecleration;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * An environment containing variable
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Environment 
{ 
    private HashMap<String, Expression> vars;
    private HashMap<String, ProcedureDecleration> procs;

    /**
     * Constructs an Environment by creating a HashMap
     */
    public Environment()
    {
        vars = new HashMap<String, Expression>();
        procs = new HashMap<String, ProcedureDecleration>();
    }
        

    public ProcedureDecleration getProcedure(String name)
    {
        return procs.get(name);
    }
    
    public void setProcedure(String name, ProcedureDecleration proc)
    {
        procs.put(name, proc);
    }

    /**
     * Sets the value of a variable given its name
     * @param name  The name of the variable
     * @param value The value to assign to the variable
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    public void setVariable(String name, Expression value) throws TypeMismatch, VariableNotDefined
    {
        while (value instanceof Variable || value instanceof BinOp)
        {
            if (value instanceof Variable)
            {
                value = ((Variable) value).eval(this);
            }
            else // value instanceof BinOp
            {
                value = ((BinOp) value).eval(this);
            }
        }
        vars.put(name, value);
    }

    /**
     * Gets the value of the variable
     * @param name  The name of the variable to retrieve
     * @return      The value of the variable
     */
    public Expression getVariable(String name)
    {
        return vars.get(name);
    }
}
