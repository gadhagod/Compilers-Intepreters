package emitter;
import java.io.*;

/**
 * Emits MIPS code into an output file
 * @author Aarav Borthakur, Anu Datar
 * @version 11/30/23
 */
public class Emitter
{
	private static int currId;
	private PrintWriter out;

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
		emit("# pop " + reg);
		emit("lw " + reg + " ($sp)");
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
}