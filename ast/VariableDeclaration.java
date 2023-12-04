package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
import jumps.Jump;

/**
 * Represents a global variable declaration statement.
 * This class is important for the compiler to allocate
 * space in the .data section of the MIPS program for 
 * each variable. When using the compiler, each variable
 * needs to be globally declared with this class.
 * @author  Aarav Borthakur
 * @version 11/23/23
 */
public class VariableDeclaration
{
    private List<String> varNames;

    /**
     * Constructs a VariableDeclaration given a List of the names
     * of the variables to declare
     */
    public VariableDeclaration(List<String> varNames)
    {
        this.varNames = varNames;
    }

    /**
     * Writes the .data line of the MIPS program allocating space for this variable
     * @param emitter     The Emitter to use to write out the MIPS instructions
     */
    public void compile(Emitter emitter)
    {
        for (String name : varNames)
        {
            emitter.emit(name + ": .word 0");
        }
    }
}
