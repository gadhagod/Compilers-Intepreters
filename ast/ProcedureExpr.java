package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;
import exceptions.ArugmentException;
import exceptions.IllegalBreak;
import exceptions.IllegalContinue;
import exceptions.LanguageException;
import jumps.Break;
import jumps.Exit;
import jumps.Jump;

/**
 * Represents a procedure call as a statement with a return value
 * @author  Aarav Borthakur
 * @version 10/25/23
 */
public class ProcedureExpr extends Expression
{
    private String procName;
    private List<Expression> params;

    /**
     * Constructs a ProcedureExpr given the name of the procedure
     * @param procName  The name of the procedure to execute
     */
    public ProcedureExpr(String procName)
    {
        this.procName = procName;
    }
    
    /**
     * Constructs a ProcedureExpr given the name of the procedure and
     * the parameters to pass into it
     * @param procName  The name of the procedure to execute
     * @param params    A List of parameter values to pass into the call
     */
    public ProcedureExpr(String procName, List<Expression> params)
    {
        this.procName = procName;
        this.params = params;
    }

    /**
     * Executes this procedure call and returns the return value of the 
     * procedure, or Number(0) by default
     * @param env   The environment this procedure is in
     * @throws LanguageException
     */
    public Expression eval(Environment env) throws LanguageException
    {   
        try
        {
            if (params == null)
            {
                return getReturnVal(env);
            }
            return getReturnValWithParams(env);
        }
        catch (Jump err)
        {
            if (err instanceof Break)
            {
                throw new IllegalBreak();
            }
            else if (err instanceof Exit)
            {
                return ((Exit) (err)).getReturnVal();
            }
            else
            {
                throw new IllegalContinue();
            }
        }
    }

        /**
     * Executes the Procedure without procedure arguments
     * @param rootEnv   The root Environment of the program
     * @return          The return value of the Procedure
     * @throws LanguageException
     * @throws Jump
     */
    private Expression getReturnVal(Environment rootEnv) throws LanguageException, Jump 
    {
        ProcedureDecleration proc = rootEnv.getProcedure(procName);
        Environment childEnv = new Environment(/*rootEnv*/);
        if (proc.getParamNames() != null)
        {
            throw new ArugmentException(proc.getName(), proc.getParamNames().size(), 0);
        }
        proc.getBody().exec(childEnv);
        return childEnv.variableDefined(proc.getName()) ? childEnv.getVariable(proc.getName()) : new Number(0);
    }
    

    private Expression getReturnValWithParams(Environment env) throws LanguageException, Jump
    {
        ProcedureDecleration proc = env.getProcedure(procName);

        int recievedParamsSize = params.size();
        if (recievedParamsSize != proc.getParamNames().size())
        {
            throw new ArugmentException(
                proc.getName(), 
                proc.getParamNames().size(), 
                recievedParamsSize
            );
        }
        Environment childEnv = new Environment(/*rootEnv*/);
        int i = 0;
        for (Expression paramVal : params)
        {
            childEnv.setVariable(proc.getParamNames().get(i), paramVal);
            i++;
        }
        proc.getBody().exec(childEnv);
        return childEnv.variableDefined(proc.getName()) ? childEnv.getVariable(proc.getName()) : new Number(0);
    }

    /**
     * Gets the String value of the procedure call
     * @return  The String representation of the procedure call,
     *          e.g. "procedureName(1, 2)"
     */
    public String toString()
    {
        return procName + "(" + params == null ? "" : params + ")";
    }

    /**
     * Compiles a Procedure call and writes the resultant MIPS code into an output file 
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter emitter) 
    {
        // push return address onto stack
        emitter.emit("# push return address onto stack");
        emitter.emitPush("$ra");
        
        // push params onto stack
        emitter.emit("#push params onto stack");
        for (Expression expr : params)
        {
            expr.compile(emitter);
            emitter.emitPush("$v0");
        }

        // jump to procedure
        emitter.emit("jal proc" + procName);

        // pop params from stack
        emitter.emit("#pop params from stack");
        for (int i = 0; i < params.size(); i++)
        {
            emitter.emitPop();
        }

        // pop return address from stack
        emitter.emit("#pop return address from stack");
        emitter.emitPop("$ra");
    }
}
