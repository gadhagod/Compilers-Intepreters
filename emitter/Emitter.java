package emitter;
import java.io.*;
import java.util.List;

import ast.ProcedureDecleration;

/**
 * Emits MIPS code into an output file
 * @author Aarav Borthakur, Anu Datar
 * @version 11/30/23
 */
public class Emitter
{
	private static int currId;
	private PrintWriter out;
	private static ProcedureDecleration currProc;
	private int excessStackHeight;

	/**
	 * Creates an emitter for writing to a new file with given name
	 * @param outputFileName	The file to output to 
	 **/
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints one line of code to file (with non-labels indented)
	 * @param code	The content to write to the file
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Emits a stack push into the output file
	 * @param reg	The register whose contents are to be pushed onto the stack
	 */
	public void emitPush(String reg)
	{
		if (hasProcedureContext())
		{
			excessStackHeight += 4;
		}
		emit("# push " + reg);
		emit("subu $sp $sp 4");
		emit("sw " + reg + " ($sp)");
	}

	/**
	 * Emits a stack pop into the output file
	 * @param reg	The register to write the contents of the top of the stack
	 */
	public void emitPop(String reg) 
	{
		if (hasProcedureContext())
		{
			excessStackHeight -= 4;
		}
		emit("# pop " + reg);
		emit("lw " + reg + " ($sp)");
		emit("addu $sp $sp 4");
	}
	
	/**
	 * Emits a stack pop into the output file that does not pop into a register
	 */
	public void emitPop() 
	{
		if (hasProcedureContext())
		{
			excessStackHeight -= 4;
		}
		emit("addu $sp $sp 4");
	}

	/**
	 * Closes the file. Should be called after all calls to emit.
	 */
	public void close()
	{
		out.close();
	}

	/**
	 * Gets the next label ID by incrementing it by 1
	 * @return	The ID of the next label to be used in the MIPS program
	 */
	public static int nextLabelID()
	{
		currId++;
		return currId;
	}

	/**
	 * Gets the current procedure being compiled
	 * @return 	The current procedure being compiled
	 */
	public ProcedureDecleration getProcedureContext()
	{
		return currProc;
	}
	
	/**
	 * Checks whether a procedure is currently being compiled
	 * @return 	Whther a procedure is currently being compiled 
	 */
	public boolean hasProcedureContext()
	{
		return currProc != null;
	}

	/**
	 * Sets the instance field containing the current procedure being compiled
	 * @param proc	The current procedure being compiled
	 */
	public void setProcedureContext(ProcedureDecleration proc) 
	{
		excessStackHeight = 0;
		currProc = proc;
	}

	/**
	 * Removes the stored procedure being compiled
	 */
	public void clearProcedureContext() 
	{
		currProc = null;
	}

	/**
	 * Gets the stack pointer offset to access a variable given its name
	 * @param localVarName	The variable to get the offset of
	 * @return				The offset used to access the value of the variable
	 */
	public int getOffset(String localVarName) 
	{
		List<String> localNames = currProc.getLocalNames();

		// move the offset down skip over excess stack items at top
		int offset = excessStackHeight;

		// traverse localNames backwards
		for (int i = localNames.size() - 1; i >= 0; i--)
		{
			String paramName = localNames.get(i);
			if (paramName.equals(localVarName))
			{
				return offset;
			}
			offset += 4;
		}

		// by now, you've gone through local vars
		// now, go through the return value
		if (localVarName.equals(currProc.getName()))
		{
			return offset;
		}
		offset += 4;

		// by now, you've gone through params and return value
		// the stack pointer now points to the top param
		// now, go through the params
		List<String> paramNames = currProc.getParamNames();
		for (int i = paramNames.size() - 1; i >= 0; i--)
		{
			String paramName = paramNames.get(i);
			if (paramName.equals(localVarName))
			{
				return offset;
			}
			offset += 4;
		}
		// by now, you might as well kys cuz the code shouldnt reach here
		return 0;
	}
}