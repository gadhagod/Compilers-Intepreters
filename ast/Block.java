package ast;

import java.util.LinkedList;

import environment.Environment;
import exceptions.LanguageException;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;
import jumps.Jump;

/**
 * Represents a Block of statements (starting with BEGIN and 
 * terminating with END)
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class Block extends Statement
{
    private LinkedList<Statement> stmts;

    /**
     * Constructs a Block
     * @param stmts   The list of statements in the Block
     */
    public Block(LinkedList<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Executes the block
     * @param env  The Environment this statement is in
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    public void exec(Environment env) throws LanguageException, Jump
    {
        for (Statement stmt : stmts)
        {
            stmt.exec(env);
        }
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     */
    public String toString(Environment env)
    {
        return stmts.toString();
    }
}
