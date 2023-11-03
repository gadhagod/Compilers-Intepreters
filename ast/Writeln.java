package ast;

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
}