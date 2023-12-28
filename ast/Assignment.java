package ast;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * Represents a variable assignment statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Assignment extends Statement
{
    private String name;
    private Expression value;
    
    /**
     * Constructs and assignment statement given the variable
     * to assign's name and its value
     * @param name  The name of the variable
     * @param value The value of the variable
     */
    public Assignment(String name, Expression value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Executes the assignment
     * @param env  The Environment this statement is in
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    @Override
    public void exec(Environment env) throws LanguageException
    {
        env.setVariable(name, value);
    }
    
    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     * @throws VariableNotDefined
     * @throws TypeMismatch
     */
    public String toString(Environment env) throws LanguageException
    {
        return name + " := " + value.toString(env);
    }

    /**
     * Compiles the assignment statement and emits the resultant
     * MIPS code into the output file
     * @param emitter   The emitter to use to write to the file
     */
    public void compile(Emitter emitter)
    {
        value.compile(emitter);
        if (emitter.hasProcedureContext() && emitter.getProcedureContext().isLocalVariable(name))
        {
            emitter.emit("sw $v0, " + emitter.getOffset(name) + "($sp)");
        }
        else
        {
            emitter.emit("sw $v0, " + name);
        }
    }
}