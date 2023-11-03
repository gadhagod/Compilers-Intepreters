package ast;

import java.util.List;

import environment.Environment;
import exceptions.LanguageException;

/**
 * Represents a Procedure call as a statement, that doesn't
 * capture the return value of the call
 */
public class ProcedureStmt extends Statement
{
    private ProcedureExpr procExpr;

    /**
     * Constructs a ProcedureStmt as a statement without params
     * @param procName  The name of the procedure to execute
     */
    public ProcedureStmt(String procName)
    {
        this.procExpr = new ProcedureExpr(procName);
    }
    
    /**
     * Constructs a ProcedureStmt as a statement without params
     * @param procName  The name of the procedure to execute
     * @param params    The parameter values to pass into the call
     */
    public ProcedureStmt(String procName, List<Expression> params)
    {
        this.procExpr = new ProcedureExpr(procName, params);
    }

    /**
     * Executes the procedure 
     * @param env   The Environment the procedure is in
     * @throws LanguageException
     */
    public void exec(Environment env) throws LanguageException
    {
        procExpr.eval(env);
    }

    /**
     * Gets the String value of the procedure call
     * @return  The String representation of the procedure call,
     *          e.g. "procedureName(1, 2)"
     */
    public String toString(Environment env)
    {
        return procExpr.toString();
    }
}
