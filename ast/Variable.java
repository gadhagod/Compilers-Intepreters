package ast;

import emitter.Emitter;
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
        return env.getVariable(varName);
    }

    /**
     * Stores the value of the variable into $t0
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter emitter) 
    {
        if (emitter.hasProcedureContext() && emitter.getProcedureContext().isLocalVariable(varName))
        {
            emitter.emit("lw $v0, " + emitter.getOffset(varName) + "($sp)");
        }
        else
        {
            emitter.emit("lw $v0, " + varName);
        }
    }

    /**
     * Gets the name of the variable
     * @return  The name of the variable
     */
    public String toString()
    {
        return varName;
    }
}