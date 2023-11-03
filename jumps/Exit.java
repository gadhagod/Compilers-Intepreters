package jumps;

import ast.Expression;

/**
 * Represents an Exit statement
 * @author  Aarav Borthakur
 * @version 10/26/23
 */
public class Exit extends Jump
{
    private Expression returnVal; 

    /**
     * Constructs and Exit jump given the return value
     * of the procedure
     * @param returnVal  The return value of the procedure
     */
    public Exit(Expression returnVal)
    {
        this.returnVal = returnVal;
    }

    /**
     * Gets the return value of the procedure
     * @return  The return value of the procedure
     */
    public Expression getReturnVal()
    {
        return returnVal;
    }
}
