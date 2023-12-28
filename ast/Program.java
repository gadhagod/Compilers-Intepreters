package ast;

import java.util.List;

import emitter.Emitter;
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
    private List<VariableDeclaration> varDeclarations;
    private List<ProcedureDecleration> procs;
    private Block script;


    public Program(List<VariableDeclaration> varDeclarations, List<ProcedureDecleration> procs, Block script)
    {
        this.varDeclarations = varDeclarations;
        this.procs = procs;
        this.script = script;
    }
    
    /*
    /**
     * Constructs a Program without Procedures with its body
     * @param script    The body of the program (usually a Block Statement)
     /
    public Program(Block script)
    {
        this.script = script;
    }
    */

    /**
     * Executes the entire Program 
     * @param env   The Environment this Program is in
     * @throws LanguageException
     */
    public void exec(Environment env) throws LanguageException
    {
        if (procs != null)
        {
            for (ProcedureDecleration proc : procs)
            {
                proc.exec(env); // put the procedure into the environment
            }
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

    /**
     * Writes out the entire MIPS file given the Pascal program's
     * variable declarations, procedures, and body
     * @param fileName  The file to write the mips instructions to (usually ending in ".asm")
     * @postcondition   The compiled mips code is outputed to fileName
     */
    public void compile(String fileName)
    {
        Emitter emitter = new Emitter(fileName);
        emitter.emit(".text");
        emitter.emit(".globl main");
        emitter.emit("main:");

        for (Statement s : script.getStatements())
        {
            s.compile(emitter);
        }
        emitter.emit("li $v0 10");
        emitter.emit("syscall # halt");
        for (ProcedureDecleration proc : procs)
        {
            proc.compile(emitter);
        }
        emitter.emit(".data");
        emitter.emit("newLine: .asciiz \"\\n\"");
        for (VariableDeclaration var : varDeclarations)
        {
            var.compile(emitter);
        }
        emitter.close();
    }
}
