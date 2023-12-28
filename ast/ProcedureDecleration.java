package ast;

import java.util.LinkedList;
import java.util.List;

import emitter.Emitter;
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
    private List<String> localNames;
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
        populateLocalVars();
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
        populateLocalVars();
    }

    public void populateLocalVars()
    {
        if (
            body instanceof Block && 
            ((Block) (body)).getStatements().get(0) instanceof VariableDeclaration
        )
        {
            List<Statement> stmts = ((Block) (body)).getStatements();
            localNames = ((VariableDeclaration) stmts.get(0)).getVarNames();
            stmts.remove(0); // remove the variable decleration from the body
        }
        else
        {
            localNames = new LinkedList<String>();
        }
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
    
    public List<String> getLocalNames()
    {
        return localNames;
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

    /**
     * Checks whether a variable is local to a procedure
     * @param varName   The name of the variable to test
     * @return          Whether the variable is local to the procedure
     */
    public boolean isLocalVariable(String varName)
    {
        return paramNames.contains(varName) || localNames.contains(varName) || name.equals(varName);
    }

    /**
     * Compiles the procedure decleration into MIPS and outputes the 
     * assembly code into the output file
     * @param emitter   The Emitter to use to emit the MIPS code to
     */
    public void compile(Emitter emitter)
    {

        // precondition: params are on the stack
        emitter.emit("proc" + name + ":");

        // add return value to stack
        emitter.emit("# add return value to stack");
        emitter.emit("li $v0, 0");
        emitter.emitPush("$v0");

        // add local vars to stack
        emitter.emit("# add local vars to stack");
        for (int i = 0; i < localNames.size(); i++)
        {
            emitter.emit("li $v0, 0"); // placeholder value
            emitter.emitPush("$v0");
        }

        emitter.setProcedureContext(this);

        body.compile(emitter);

        // remove local vars on the stack
        emitter.emit("# remove local vars on the stack");
        for (int i = 0; i < localNames.size(); i++)
        {
            emitter.emitPop();
        }

        // remove return val from stack and store in $v0 
        emitter.emit("# return val from stack and store in $v0 ");
        emitter.emitPop("$v0");

        emitter.emit("jr $ra");
        emitter.clearProcedureContext();
    }
}