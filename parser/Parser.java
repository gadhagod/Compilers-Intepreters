package parser;

import java.io.IOException;
import java.util.HashMap;

import scanner.*;
import exceptions.*;
import scanner.KeywordToken.Keyword;
import scanner.OperandToken.Operand;

public class Parser 
{
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
    
    private int parseExpr() throws IOException, LanguageException
    {
        int val = parseTerm();
        while (currToken instanceof OperandToken)
        {
            Operand oper = parseOperand();
            int second = parseTerm();
            if (oper.equals(Operand.ADDITION))
            {
                val += second;
            }
            else if (oper.equals(Operand.SUBTRACTION))
            {
                val -= second;
            }
        }
        return val;
    }

    private String parseIdentifier() throws IOException, LanguageException
    {
        IdentifierToken token = (IdentifierToken) (currToken);
        eat(token);
        return token.getValue();
    }

    private int parseFactor() throws IOException, LanguageException
    {
        if (
            currToken instanceof SeperatorToken && 
            ((SeperatorToken) (currToken)).equals(SeperatorToken.Seperator.OPEN_PAREN))
        {
            eat(new SeperatorToken("("));
            int val = parseExpr();
            eat(new SeperatorToken(")"));
            return val;
        }
        else if (
            currToken instanceof OperandToken && 
            ((OperandToken) (currToken)).equals(Operand.SUBTRACTION)
        )
        {
            eat(new OperandToken("-"));
            return -parseFactor();
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
            if (!(value instanceof Integer))
            {
                throw new TypeMismatch(value.getClass().toString(), "integer");
            }
            return (Integer) (value);
        }
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

    private int parseTerm() throws IOException, LanguageException
    {
        int val = parseFactor();
        while (
            (currToken instanceof OperandToken) && 
            (
                ((OperandToken) (currToken)).equals(Operand.MULTIPLICATION) ||
                ((OperandToken) (currToken)).equals(Operand.DIVISION)
            )
        )
        {
            Operand oper = parseOperand();
            int second = parseFactor();
            if (oper.equals(Operand.MULTIPLICATION))
            {
                val *= second;
            }
            else if (oper.equals(Operand.DIVISION))
            {
                val /= second;
            }
        }
        return val;
    }

    /*private*/ public void parseStatement() throws IOException, LanguageException
    {
        Token token = currToken;
        eat(token);
        if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.WRITELN)
        )
        {
            eat(new SeperatorToken("("));
            System.out.println(parseExpr());
            eat(new SeperatorToken(")"));
            eat(new SeperatorToken(";"));
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.BEGIN)
        )
        {
            Token statementOpener = token;
            while (!(((KeywordToken) (statementOpener)).equals(Keyword.END)))
            {
                parseStatement();
                statementOpener = token;
            }
            eat(new KeywordToken(Keyword.END.name()));
            eat(new SeperatorToken(";"));
        }
        else if (token instanceof IdentifierToken)
        {
            eat(new OperandToken(":="));
            vars.put(((IdentifierToken) (token)).getValue(), parseExpr());
            /* TODO: add string variables here */
        }
    }
}
