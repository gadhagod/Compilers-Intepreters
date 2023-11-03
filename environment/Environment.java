package environment;

import java.util.HashMap;
import java.util.Map;

import ast.BinOp;
import ast.Expression;
import ast.ProcedureExpr;
import ast.Variable;
import ast.ProcedureDecleration;
import exceptions.LanguageException;
import exceptions.ProcedureNotDefined;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * An environment containing variables and procedures
 * @author  Aarav Borthakur
 * @version 10/19/23
 */
public class Environment 
{ 
    private static Environment root;
    //private Environment parent;
    private Map<String, Expression> vars;
    private Map<String, ProcedureDecleration> procs;

    /**
     * Constructs an Environment by creating HashMaps a symbol tables
     * to store variable and procedures
     */
    public Environment()
    {
        vars = new HashMap<String, Expression>();
        procs = new HashMap<String, ProcedureDecleration>();
        if (root == null)
        {
            root = this;
        }
    }
        
    /*
    /**
     * Constructs an Environment given its parent environment
     * @param parent    The parent environment
     /
    public Environment(Environment parent)
    {
        this.parent = parent;
        vars = new HashMap<String, Expression>();
        procs = new HashMap<String, ProcedureDecleration>();
    }
    */

    /**
     * Gets a procedure in an environment given its name
     * @param name  The name of the procedure
     * @return      The procedure
     * @throws ProcedureNotDefined
     */
    public ProcedureDecleration getProcedure(String name) throws ProcedureNotDefined
    {
        ProcedureDecleration proc;
        if (isRoot())
        {
            proc = procs.get(name);
        }
        else
        {
            proc = root.getProcedure(name);
        }
        if (proc == null)
        {
            throw new ProcedureNotDefined(name);
        }
        return proc;
    }
    
    /**
     * Stores a procedure in the Environment given its name and 
     * decleration
     * @param name  The procedure name
     * @param proc  The procedure body
     */
    public void setProcedure(String name, ProcedureDecleration proc)
    {
        if (isRoot())
        {
            procs.put(name, proc);
        }
        else
        {
            root.setProcedure(name, proc);
        }
    }

    /**
     * Sets the value of a variable given its name
     * @param name  The name of the variable
     * @param value The value to assign to the variable
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    public void setVariable(String name, Expression value) throws LanguageException
    {
        boolean definedInRoot = root.variableDefined(name);
        while (value instanceof Variable || value instanceof BinOp || value instanceof ProcedureExpr)
        {
            if (value instanceof Variable)
            {
                value = ((Variable) value).eval(this);
            }
            else if (value instanceof ProcedureExpr)
            {
                value = ((ProcedureExpr) value).eval(this);
            }
            else // value instanceof BinOp
            {
                value = ((BinOp) value).eval(this);
            }
        }
        if (definedInRoot && !isRoot())
        {
            root.setVariable(name, value);
        }
        else
        {
            vars.put(name, value);
        }
    }

    /**
     * Gets the value of the variable
     * @param name  The name of the variable to retrieve
     * @return      The value of the variable
     */
    public Expression getVariable(String name) throws VariableNotDefined
    {
        Expression val = vars.get(name);
        if (val == null)
        {
            if (isRoot())
            {
                throw new VariableNotDefined(name);
            }
            else
            {
                val = root.getVariable(name);
            }
        }
        return val;
    }
    
    /**
     * Checks whether a variable is in the Environment given its name
     * @param name  The name of the variable to check for
     * @return      Whether the variable is defined
     */
    public boolean variableDefined(String name)
    {
        return vars.containsKey(name);
    }

    /**
     * Checks whether this environment is the root environment
     * @return  Whether this environment is the root env
     */
    public boolean isRoot()
    {
        return this == root;
    }
}
