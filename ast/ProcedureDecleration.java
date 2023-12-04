package ast;

import java.util.List;

import environment.Environment;
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
     * Gets the body statement of the procedure
     * @return  The body of the procedure
     */
    public Statement getBody()
    {
        return body;
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
     * Executes the Procedure given procedure arguments to pass in
     * @param rootEnv   The root Environment of the program
     * @param paramVals A List of param values to pass into the procedure
     * @return          The return value of the Procedure
     * @throws LanguageException
     * @throws Jump
     */
    public void exec(Environment env) throws LanguageException
    {
        env.setProcedure(getName(), this);
    }

    public List<String> getParamNames()
    {
        return paramNames;
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