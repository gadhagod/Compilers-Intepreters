package scanner;

public class OperandToken extends Token
{
    public static enum Operand {
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
        LESS_OR_EQUAL
    }

    private Operand operand;
    private String inpToken;

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
        else
        {
            // unexpected token
            throw new ScanErrorException();
        }
    }

    public Operand getValue()
    {
        return operand;
    }

    public String toString()
    {
        return inpToken;
    }
}
