package scanner;
import java.io.*;

/**
 * The Scanner for the compiler/intepreter of the Pascal programming language
 * @author Aarav Borthakur
 *  
 * Usage:
 *  Scanner scanner = new Scanner("x = 1 + 2;")
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    private int line;
    private int col;

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     *  FileInputStream inStream = new FileInputStream(new File(<file name>);
     *  Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream) throws ScanErrorException
    {
        line = 1;
        col = 1;
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
    public Scanner(String inString) throws ScanErrorException
    {
        line = 1;
        col = 1;
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
            //System.out.println(in.readLine());
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
            col++;
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
        return character >= '0' && character <= '9';
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

    /**
     * Checks whether a char represents a seperator
     * @param character  The character to test
     * @return           Whether the @param character represents a seperator
     */
    public static boolean isSeperator(char character)
    {
        return ";{}()".indexOf(character) >= 0;
    }

    /**
     * Checks whether a char represents an operand
     * @param character  The character to test
     * @return           Whether the @param character represents a operand
     */
    public static boolean isOperand(char character)
    {
        return "=+-*/%:<>".indexOf(character) >= 0;
    }
    
    /**
     * Checks whether a char represents a new line
     * @param character  The character to test
     * @return           Whether the @param character represents a new line
     */
    public static boolean isNewLine(char character)
    {
        return character == '\n';
    }

    /**
     * Checks whether a char represents a white space
     * @param character  The character to test
     * @return           Whether the @param character represents a white space
     */
    public static boolean isWhiteSpace(char character) 
    {
        return " \t\r".indexOf(character) >= 0;
    }

    /**
     * Checks whether a char represents a period
     * @param character  The character to test
     * @return           Whether the @param character represents a period
     */
    public static boolean isPeriod(char character)
    {
        return character == '.';
    }

    /**
     * Returns the operand at the current position of the input stream
     * @return  The seperator as a String
     */
    public String scanSeperator() throws ScanErrorException
    {
        String operand = String.valueOf(currentChar);
        eat(currentChar);
        return operand;
    }

    /**
     * Returns the number at the current position of the input stream
     * @return  The number as a String
     */
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

    /**
     * Returns the identifier at the current position of the input stream
     * @return  The identifier as a String
     */
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
    
    /**
     * Returns the operand at the current position of the input stream
     * @return  The operand as a String
     */
    public String scanOperand() throws ScanErrorException
    {
        String operand = String.valueOf(currentChar);
        eat(currentChar);
        return operand;
    }
    
    /**
     * Passes over characters in the input stream until a target
     * character is found
     * @param target    The target character to find
     */
    private void skipUntilFound(char target) throws ScanErrorException
    {
        while (hasNext() && currentChar != target)
        {
            eat(currentChar);
        }
    }
    
    /**
     * Skips over characters in input stream until a target String
     * is found
     * @param target    The target string to find
     */
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
     * Gets the next token in the input stream
     * @throws ScanErrorException
     */
    public Token nextToken() throws ScanErrorException, IOException
    {
        try 
        {
            if (!hasNext() || isPeriod(currentChar))
            {
                if (isPeriod(currentChar))
                {
                    eat(currentChar);
                }
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
                col = 0;
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
            throw new ScanErrorException("Unexpected token: \"" + currentChar + "\"", line, col);
        }
    }    
}
