package ast;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;

/**
 * Represents a WRITELN statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Writeln extends Statement
{
    private Expression exp;
    
    /**
     * Constructs a Writeln statement
     * @param exp       The expression to print
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Prints the expression
     * @param env   The environment this statement is in
     * @throws      TypeMismatch
     * @throws      VariableNotDefined
     */
    @Override
    public void exec(Environment env) throws LanguageException
    {
        Object result = exp.eval(env);
        while (result instanceof Expression) {
            result = ((Expression) result).eval(env);
        }
        System.out.println(result);
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "WRITELN " + exp;
    }

    /**
     * Writes out the MIPS instructions representing the WRITE
     * statement. Prints the contents of the $v0 register
     * @param e The Emitter to use to write out the mips instructions to
     */
    public void compile(Emitter emitter)
    {
        exp.compile(emitter);
        
        emitter.emit("move $a0, $v0");
        emitter.emit("li $v0, " + 1);
        emitter.emit("syscall");

        // print new line
        emitter.emit("li $v0, 4");
        emitter.emit("la $a0, newLine");
        emitter.emit("syscall");
    } 
}