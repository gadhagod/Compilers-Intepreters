package scanner;
import java.io.*;

/**
 * The Scanner for the compiler/intepreter of the Pascal programming language
 * @author Aarav Borthakur
 *  
 * Usage:
 *  Scanner scanner = new Scanner("x = 1 + 2;")
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    private int line;

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     *  FileInputStream inStream = new FileInputStream(new File(<file name>);
     *  Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream) throws IOException
    {
        line = 1;
        in = new BufferedReader(new InputStreamReader(inStream));
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString) throws IOException
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Sets the next character of the input stream to
     * currentChar
     * @throws IOException
     */
    private void getNextChar()
    {
        try 
        {
            int curr = in.read();
            if (curr == -1)
            {
                eof = true;
            }
            currentChar = (char) (curr);
        } catch (IOException e) 
        {
            System.exit(1);
        }
    }
    /**
     * Sets the next character of the input stream to currentChar
     * given the next character
     * @param expected  The expected next character
     * @throws ScanErrorException if the expected character is not 
     *                            the value of currentChar after
     *                            getNextChar()
     */
    
    private void eat(char expected) throws ScanErrorException
    {
        if (currentChar == expected)
        {
            getNextChar();
        }
        else 
        {
            throw new ScanErrorException("Illegal character: expected <" + expected + "> and found <" + currentChar + ">");
        }
    }
    /**
     * Checks whether there are characters left in the input stream
     * @return  true if the end of the input stream has not been reached,
     *          else false
     */
    public boolean hasNext()
    {
       return !eof;
    }

    /**
     * Checks whether a char represents a digit
     * @param character  The character to test
     * @return           Whether a @param character represents a digit
     */
    public static boolean isDigit(char character) 
    {
        return "0123456789".indexOf(character) >= 0;
    }
    
    /**
     * Checks whether a char represents a letter
     * @param character  The character to test
     * @return           Whether the @param character is represents a letter
     */
    public static boolean isLetter(char character) 
    {
        return "abcdefghijklmnopqrstuvwxyz".indexOf(Character.toLowerCase(character)) >= 0;
    }

    public static boolean isSeperator(char character)
    {
        return ";{}()".indexOf(character) >= 0;
    }

    public static boolean isOperand(char character)
    {
        return "=+-*/%:<>".indexOf(character) >= 0;
    }
    
    public static boolean isNewLine(char character)
    {
        return character == '\n';
    }

    public static boolean isWhiteSpace(char character) 
    {
        return " \t\r".indexOf(character) >= 0;
    }

    public static boolean isCommentOpener(char character)
    {
        return character == '/';
    }

    public String scanSeperator() throws ScanErrorException
    {
        String operand = String.valueOf(currentChar);
        eat(currentChar);
        return operand;
    }

    public String scanNumber() throws ScanErrorException
    {
        String lexeme = "";
        while (isDigit(currentChar))
        {
            lexeme += currentChar;
            eat(currentChar);
        }
        return lexeme;
    }

    public String scanIdentifier() throws ScanErrorException
    {
        String lexeme = "";
        while (isDigit(currentChar) || isLetter(currentChar))
        {
            lexeme += currentChar;
            eat(currentChar);
        }
        return lexeme;
    }
    
    public String scanOperand() throws ScanErrorException
    {
        String operand = String.valueOf(currentChar);
        eat(currentChar);
        return operand;
    }
    
    private void skipUntilFound(char target) throws ScanErrorException
    {
        while (hasNext() && currentChar != target)
        {
            eat(currentChar);
        }
    }
    
    private void skipUntilFound(String target) throws ScanErrorException
    {
        String skipped = "";
        while (hasNext() && !skipped.endsWith(target))
        {
            skipped += currentChar;
            eat(currentChar);
        }
    }

    /**
     * Method: nextToken
     * @return
     */
    public Token nextToken() throws ScanErrorException
    {
        try 
        {
            if (!hasNext())
            {
                return new KeywordToken(KeywordToken.Keyword.END.name());
            }
            if (isSeperator(currentChar))
            {
                return new SeperatorToken(scanSeperator());
            }
            if (isDigit(currentChar))
            {
                return new DigitToken(scanNumber());
            }
            if (isLetter(currentChar))
            {
                return new IdentifierToken(scanIdentifier());
            }
            if (isOperand(currentChar))
            {
                String oper = scanOperand();
                if (oper.equals("/") && currentChar == '/') // if `//`
                {
                    skipUntilFound('\n');
                    return nextToken();
                }
                if (oper.equals("/") && currentChar == '*') // if `/*`
                {
                    skipUntilFound("*/");
                    return nextToken();
                }
                if (oper.equals(":") && currentChar == '=') // if `:=`
                {
                    eat(currentChar);
                    return new OperandToken(":=");
                }
                if (oper.equals(">") && currentChar == '=') // if `:=`
                {
                    eat(currentChar);
                    return new OperandToken(">=");
                }
                if (oper.equals("<") && currentChar == '=') // if `:=`
                {
                    eat(currentChar);
                    return new OperandToken("<=");
                }
    
                return new OperandToken(oper);
            }
            if (isNewLine(currentChar))
            {
                line++;
                eat(currentChar);
                return nextToken();
            }
            if (isWhiteSpace(currentChar))
            {
                eat(currentChar);
                return nextToken();
            }
            else 
            {
                throw new ScanErrorException();
            }
        } 
        catch (ScanErrorException err)
        {
            throw new ScanErrorException("Unexpected token: \"" + currentChar + "\"", line);
        }
    }    
}
