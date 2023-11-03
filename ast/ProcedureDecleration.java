package ast;

import java.util.List;

import environment.Environment;
import exceptions.ArugmentException;
import exceptions.LanguageException;
import jumps.Jump;

/**
 * Represents a decleration of a Procedure, a repeatable
 * block of code that can take in inputs and return values
 */
public class ProcedureDecleration
{
    private List<String> paramNames;
    private String name;
    private Statement body;

    /**
     * Constructs a ProcedureDecleration
     * @param name  The name of the procedure
     * @param body  The body code of the procedure
     */
    public ProcedureDecleration(String name, Statement body)
    {
        this.name = name;
        this.body = body;
    }
    
    /**
     * Constructs a ProcedureDecleration 
     * @param name       The name of the procedure
     * @param body       The body code 
     * @param paramNames A List of the names of the parameters to the procedure
     */
    public ProcedureDecleration(String name, Statement body, List<String> paramNames)
    {
        this.name = name;
        this.body = body;
        this.paramNames = paramNames;
    }

    /**
     * Executes the Procedure without procedure arguments
     * @param rootEnv   The root Environment of the program
     * @return          The return value of the Procedure
     * @throws LanguageException
     * @throws Jump
     */
    public Expression exec(/*Environment rootEnv*/) throws LanguageException, Jump 
    {
        Environment env = new Environment(/*rootEnv*/);
        if (paramNames != null)
        {
            throw new ArugmentException(name, paramNames.size(), 0);
        }
        body.exec(env);
        return env.variableDefined(name) ? env.getVariable(name) : new Number(0);
    }
    
    /**
     * Executes the Procedure given procedure arguments to pass in
     * @param rootEnv   The root Environment of the program
     * @param paramVals A List of param values to pass into the procedure
     * @return          The return value of the Procedure
     * @throws LanguageException
     * @throws Jump
     */
    public Expression exec(/*Environment rootEnv, */ List<Expression> paramVals) throws LanguageException, Jump
    {
        int recievedParamsSize = paramVals.size();
        if (recievedParamsSize != paramNames.size())
        {
            throw new ArugmentException(name, paramNames.size(), recievedParamsSize);
        }
        Environment env = new Environment(/*rootEnv*/);
        int i = 0;
        for (Expression paramVal : paramVals)
        {
            env.setVariable(paramNames.get(i), paramVal);
            i++;
        }
        body.exec(env);
        return env.variableDefined(name) ? env.getVariable(name) : new Number(0);
    }

    /**
     * Gets the String representation of the Procedure for debugging
     * @param env   The Environment this decleration is in
     * @return      The name of the procedure
     * @throws LanguageException
     * @throws Jump
     */
    public String toString(Environment env) throws LanguageException, Jump 
    {    
        return "Procedure decleration:" + name;
    }

    /**
     * Retrieves the name of the procedure
     * @return  The name of the procedure
     */
    public String getName()
    {
        return name;
    }
}