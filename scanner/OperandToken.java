package scanner;

/**
 * Represents a Token that is an operand
 * @author  Aarav Borthakur
 * @version 8/31/23
 */
public class OperandToken extends Token
{
    /**
     * Types of operands
     */
    public static enum Operand 
    {
        ASSIGNMENT,
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        POWER,
        DIVISION,
        MODULO,
        GREATER,
        GREATER_OR_EQUAL,
        LESS,
        LESS_OR_EQUAL,
        EQUALS
    }

    private Operand operand;
    private String inpToken;

    /**
     * Constructs an OperandToken
     * @param inpToken    The input token
     * @throws ScanErrorException
     */
    public OperandToken(String inpToken) throws ScanErrorException
    {
        this.inpToken = inpToken;
        if (inpToken.equals(":="))
        {
            operand = Operand.ASSIGNMENT;
        }
        else if (inpToken.equals("+"))
        {
            operand = Operand.ADDITION;
        }
        else if (inpToken.equals("-"))
        {
            operand = Operand.SUBTRACTION;
        }
        else if (inpToken.equals(("*")))
        {
            operand = Operand.MULTIPLICATION;
        }
        else if (inpToken.equals("/"))
        {
            operand = Operand.DIVISION;
        }
        else if (inpToken.equals("%"))
        {
            operand = Operand.MODULO;
        }
        else if (inpToken.equals(">"))
        {
            operand = Operand.DIVISION;
        }
        else if (inpToken.equals(">="))
        {
            operand = Operand.GREATER_OR_EQUAL;
        }
        else if (inpToken.equals(("<")))
        {
            operand = Operand.LESS;
        }
        else if (inpToken.equals("<="))
        {
            operand = Operand.LESS_OR_EQUAL;
        }
        else if (inpToken.equals("="))
        {
            operand = Operand.EQUALS;
        }
        else
        {
            throw new ScanErrorException(); // unexpected token
        }
    }

    /**
     * Gets the value of the operand
     * @param inpToken    The input token
     * @throws ScanErrorException
     */
    public Operand getValue()
    {
        return operand;
    }

    /**
     * Gets the string representation of the token
     * @return  The String representation of the token
     */
    public String toString()
    {
        return inpToken;
    }
}
