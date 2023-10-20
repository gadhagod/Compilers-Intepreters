package ast;

import environment.Environment;
import exceptions.LanguageException;
import jumps.Jump;
import parser.Parser;

/**
 * Represents a if statement
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class If extends Statement
{
    private Expression condition;
    private Statement then;
    private Statement else_;

    /**
     * Constructs an If statement
     * @param condition The condition of the if statement
     * @param then      The body of the if statement
     */
    public If(Expression condition, Statement then)
    {
        this.condition = condition;
        this.then = then;
    }
    
    /**
     * Constructs an If statement
     * @param condition The condition of the if statement
     * @param else      The body of the else statement
     * @param then      The body of the if statement
     */
    public If(Expression condition, Statement then, Statement else_)
    {
        this.condition = condition;
        this.then = then;
        this.else_ = else_;
    }

    /**
     * Executes the if statement
     * @param env  The Environment this statement is in
     */
    public void exec(Environment env) throws LanguageException, Jump
    {
        Object cond = condition;
        while (cond instanceof Expression)
        {  
            cond = ((Expression) cond).eval(env);
        }
        boolean condVal = Parser.expectType(cond, Boolean.class);
        if (condVal)
        {
            then.exec(env);
        }
        else if (else_ != null)
        {
            else_.exec(env);
        }
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return "IF " + condition;
    }
}
