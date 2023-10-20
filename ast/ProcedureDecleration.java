package ast;

import environment.Environment;
import exceptions.LanguageException;
import jumps.Jump;

public class ProcedureDecleration extends Statement
{
    private String name;
    private Statement body;

    public ProcedureDecleration(String name, Statement body)
    {
        this.name = name;
        this.body = body;
    }

    @Override
    public void exec(Environment env) throws LanguageException, Jump 
    {
        body.exec(env);
    }

    @Override
    public String toString(Environment env) throws LanguageException, Jump 
    {    
        return "";
    }

    public String getName()
    {
        return name;
    }
}