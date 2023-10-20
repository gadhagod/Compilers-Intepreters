package ast;

import java.util.LinkedList;
import java.util.Map;

import environment.Environment;
import exceptions.IllegalBreak;
import exceptions.IllegalContinue;
import exceptions.LanguageException;
import jumps.Break;
import jumps.Continue;
import jumps.Jump;

public class Program 
{
    private LinkedList<ProcedureDecleration> procs;
    private Statement script;
    private Environment env;

    public Program(LinkedList<ProcedureDecleration> procs, Statement script, Environment env)
    {
        this.procs = procs;
        this.script = script;
        this.env = env;
    }
    
    public Program(Statement script, Environment env)
    {
        this.script = script;
        this.env = env;
    }

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
            else
            {
                throw new IllegalContinue();
            }
        }
    }
}
