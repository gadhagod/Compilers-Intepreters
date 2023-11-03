package ast;

import java.util.List;

import environment.Environment;
import exceptions.IllegalBreak;
import exceptions.IllegalContinue;
import exceptions.IllegalExit;
import exceptions.LanguageException;
import jumps.Break;
import jumps.Continue;
import jumps.Exit;
import jumps.Jump;

/**
 * Represents an entire Pascal program. This is the root 
 * of the abstract syntax tree
 * @author  Aarav Borthakur
 * @version 10/25/23
 */
public class Program 
{
    private List<ProcedureDecleration> procs;
    private Statement script;

    /**
     * Constructs a Program given the list of procedures of the program
     * and the body of it
     * @param procs     A List of procedures of the program
     * @param script    The body of the program (usually a Block Statement)
     */
    public Program(List<ProcedureDecleration> procs, Statement script)
    {
        this.procs = procs;
        this.script = script;
    }
    
    /**
     * Constructs a Program without Procedures with its body
     * @param script    The body of the program (usually a Block Statement)
     */
    public Program(Statement script)
    {
        this.script = script;
    }

    /**
     * Executes the entire Program 
     * @param env   The Environment this Program is in
     * @throws LanguageException
     */
    public void exec(Environment env) throws LanguageException
    {
        for (ProcedureDecleration proc : procs)
        {
            env.setProcedure(proc.getName(), proc);
        }
        try 
        {
            script.exec(env);
        }
        catch (Jump err)
        {
            if (err instanceof Break)
            {
                throw new IllegalBreak();
            }
            else if (err instanceof Continue)
            {
                throw new IllegalContinue();
            }
            else if (err instanceof Exit)
            {
                throw new IllegalExit();
            }
        }
    }
}
