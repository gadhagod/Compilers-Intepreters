package parser;

import java.io.IOException;
import java.util.HashMap;

import scanner.*;
import exceptions.*;
import scanner.KeywordToken.Keyword;
import scanner.OperandToken.Operand;

public class Parser 
{
    private java.util.Scanner lineScanner;
    private HashMap<String, Object> vars;
    private Scanner scanner;
    private Token currToken;

    public Parser(Scanner scanner) throws IOException, LanguageException
    {
        this.scanner = scanner;
        vars = new HashMap<String, Object>();
        currToken = scanner.nextToken();
        parseStatement();
    }

    private String readLine()
    {   
        if (lineScanner == null)
        {
            lineScanner = new java.util.Scanner(System.in);
        }
        return lineScanner.nextLine();
    }

    private static <T> T expectType(Object value, Class<T> expectedClass) throws TypeMismatch
    {
        if (!expectedClass.isInstance(value))
        {
            throw new TypeMismatch(expectedClass.getName(), value.getClass().getName());
        }
        return expectedClass.cast(value);
    }

    private void eat(Token expectedToken) throws IOException, LanguageException
    {
        if (currToken.equals(expectedToken))
        {
            currToken = scanner.nextToken();
        }
        else
        {
            throw new UnexpectedToken(currToken, expectedToken);
        }
    }

    private int parseNumber() throws IOException, LanguageException
    {
        int val = ((DigitToken) (currToken)).getValue();
        eat(currToken);
        return val;
    }
    
    /*
    private Keyword parseKeyword() throws IOException, LanguageException
    {
        Keyword val = ((KeywordToken) (currToken)).getValue();
        eat(currToken);
        return val;
    }
    */
    
    private Object parseExpr() throws IOException, LanguageException
    {
        Object val = parseTerm();
        if (val instanceof Integer)
        {
            Integer intVal = (Integer) (val);
            while (currToken instanceof OperandToken)
            {
                Operand oper = parseOperand();
                Integer secondTerm = expectType(
                    parseTerm(), 
                    Integer.class
                );
                if (oper.equals(Operand.ADDITION))
                {
                    intVal += secondTerm;
                }
                else if (oper.equals(Operand.SUBTRACTION))
                {
                    intVal -= secondTerm;
                }
                else
                {
                    throw new TypeMismatch(intVal, secondTerm, oper);
                }
            }
            return intVal;
        }
        else if (val instanceof String)
        {
            String strVal = (String) (val);
            while (currToken instanceof OperandToken)
            {
                Operand oper = parseOperand();
                String secondTerm = expectType(
                    parseTerm(), 
                    String.class
                );
                
                if (oper.equals(Operand.ADDITION))
                {
                    strVal += secondTerm;
                }
                else
                {
                    throw new TypeMismatch("string", "string", oper);
                }
            }
            return strVal;
        }
        return new TypeMismatch("any", val.getClass().getName());
    }

    private String parseIdentifier() throws IOException, LanguageException
    {
        IdentifierToken token = (IdentifierToken) (currToken);
        eat(token);
        return token.getValue();
    }

    private String parseString() throws IOException, LanguageException
    {
        StringToken token = (StringToken) (currToken);
        eat(token);
        return token.getValue();
    }

    private Object parseFactor() throws IOException, LanguageException
    {
        if (
            currToken instanceof SeperatorToken && 
            ((SeperatorToken) (currToken)).equals(SeperatorToken.Seperator.OPEN_PAREN))
        {
            eat(new SeperatorToken("("));
            Object val = parseExpr();
            eat(new SeperatorToken(")"));
            return val;
        }
        else if (
            currToken instanceof OperandToken && 
            ((OperandToken) (currToken)).equals(Operand.SUBTRACTION)
        )
        {
            eat(new OperandToken("-"));
            return -(Integer) (parseFactor());
        }
        else if (currToken instanceof DigitToken)
        {
            return parseNumber();
        }
        else if (currToken instanceof IdentifierToken)
        {
            String id = parseIdentifier();
            Object value = vars.get(id);
            if (value == null)
            {
                throw new VariableNotDefined(id);
            }
            return value;
        }
        else if (currToken instanceof StringToken)
        {
            return parseString();
        }
        /*
        else if (
            currToken instanceof KeywordToken && 
            ((KeywordToken) currToken).isLogicalOperator()
        )
        {
            return parseKeyword();
        }
        */
        else
        {
            throw new UnexpectedToken(currToken.getValue().toString());
        }
    }

    private Operand parseOperand() throws IOException, LanguageException
    {
        OperandToken curr = (OperandToken) (currToken);
        eat(curr);
        return curr.getValue();
    }

    private Object parseTerm() throws IOException, LanguageException
    {
        Object val = parseFactor();
        if (val instanceof Integer)
        {
            int intVal = (Integer) val;
            while (
               ((currToken instanceof OperandToken) && 
                (
                    ((OperandToken) (currToken)).equals(Operand.MULTIPLICATION) ||
                    ((OperandToken) (currToken)).equals(Operand.DIVISION)
                )) ||
                ((currToken instanceof KeywordToken) && (((KeywordToken) (currToken)).equals(Keyword.mod)))
            )
            {
                if (currToken instanceof OperandToken)
                {
                    Operand oper = parseOperand();
                    int second = (Integer) parseFactor();
                    if (oper.equals(Operand.MULTIPLICATION))
                    {
                        intVal *= second;
                    }
                    else if (oper.equals(Operand.DIVISION))
                    {
                        intVal /= second;
                    }
                }
                else  // currToken is a KeywordToken
                {
                    eat(new KeywordToken("mod"));
                    int second = expectType(parseFactor(), Integer.class);
                    intVal %= second;
                }
            }
            return intVal;
        }
        else if (val instanceof String)
        {
            return val;
        }
        else 
        {
            return val;
        }
    }

    /*private*/ public void parseStatement() throws IOException, LanguageException
    {
        Token token = currToken;
        eat(token);
        if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.EOF)
        )
        {
            System.exit(1);
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.WRITELN)
        )
        {
            eat(new SeperatorToken("("));
            System.out.println(parseExpr());
            eat(new SeperatorToken(")"));
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.READLN))
        {
            eat(new SeperatorToken("("));
            String varName = expectType(parseIdentifier(), String.class);
            eat(new SeperatorToken(")"));
            vars.put(varName, readLine());
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.BEGIN)
        )
        {
            Token statementOpener = currToken;
            while (!(
                (statementOpener instanceof KeywordToken) && ((KeywordToken) statementOpener).equals(Keyword.END)
            ))
            {
                parseStatement();
                statementOpener = currToken;
            }
            eat(new KeywordToken(Keyword.END.name()));
        }
        else if (token instanceof IdentifierToken)
        {
            eat(new OperandToken(":="));
            vars.put(((IdentifierToken) (token)).getValue(), parseExpr());
        }
        else
        {
            throw new UnexpectedToken(token);
        }
        eat(new SeperatorToken(";"));
    }
}
