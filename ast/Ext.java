package ast;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
import jumps.Exit;
import jumps.Jump;

/**
 * Represents an EXIT statement
 * @author  Aarav Borthakur
 * @version 10/26/23
 */
public class Ext extends Statement
{
    private Expression returnVal;

    /**
     * Constructs an Exit Statement
     */
    public Ext()
    {
        returnVal = new Number(0);
    }

    /**
     * Constructs an EXIT statement based on containing return value
     * @param val   The return value to exit with
     */
    public Ext(Expression val)
    {
        returnVal = val;
    }

    /**
     * Executes the Exit statement by throwing an Exit
     * @param env   The Environment the statement is in
     */
    @Override
    public void exec(Environment env) throws LanguageException, Jump 
    {
        // evaluate the return value
        Expression _return = returnVal;
        while (_return instanceof Variable || _return instanceof BinOp || _return instanceof ProcedureExpr)
        {
            _return = (Expression) (_return.eval(env));
        }
        throw new Exit(_return);
    }

    /**
     * Gets the String representation of the Exit statement for debugging
     * @param env   The Environment the statement is in
     * @return      The statement's String representation
     */
    @Override
    public String toString(Environment env) throws LanguageException, Jump 
    {
        return "EXIT";
    }

    /**
     * Throws an UnsupportedOperationException because exits 
     * aren't supported in the compiler
     * @param e     The Emitter to use to write out the MIPS instructions
     */
    @Override
    public void compile(Emitter e) 
    {
        throw new UnsupportedOperationException("EXIT not implemented in compiled pascal");
    }
}
