package parser;

import java.io.IOException;
import java.util.LinkedList;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Bool;
import ast.Brk;
import ast.Cont;
import ast.Expression;
import ast.For;
import ast.If;
import ast.Number;
import ast.ProcedureDecleration;
import ast.Program;
import ast.Readln;
import ast.Statement;
import ast.Str;
import ast.Variable;
import ast.While;
import ast.Writeln;
import environment.Environment;
import scanner.*;
import exceptions.*;
import jumps.Break;
import jumps.Jump;
import scanner.KeywordToken.Keyword;
import scanner.OperandToken.Operand;
import scanner.SeperatorToken.Seperator;

/**
 * Parses the Pascal program (second phase)
 * @author  Aarav Borthakur
 * @version 10/2/23
 */
public class Parser 
{
    private Scanner scanner;
    private Token currToken;
    private Environment env;

    /**
     * Constrcuts a Parser
     * @param scanner             The Scanner on the language
     * @throws IOException        When there is a problem reading the file       
     * @throws LanguageException  When there is an error in the Pascal program 
     */
    public Parser(Scanner scanner, Environment env) throws IOException, LanguageException
    {
        this.scanner = scanner;
        this.env = env;
        currToken = scanner.nextToken();
    }

    /**
     * Asserts that an object of a class and returns the object casted to 
     * that class
     * @param value         The value to cast
     * @param expectedClass The class to cast it to
     * @return              value casted to expectedClass
     * @throws TypeMismatch When value is not of expectedClass
     */
    public static <T> T expectType(Object value, Class<T> expectedClass) throws TypeMismatch
    {
        if (!expectedClass.isInstance(value))
        {
            throw new TypeMismatch(expectedClass.getName(), value.getClass().getName());
        }
        return expectedClass.cast(value);
    }

    /**
     * Checks that the next token is what's expected and advances
     * the reader
     * @param expectedToken      The expected token
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition            Advances the current token character by one
     */
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

    /**
     * Gets the Number value of the current number
     * @return The value of the number
     * @throws IOException when there is a problem reading the file
     * @throws LanguageException when there is an error in the Pascal program
     * @precondition  The currToken is a digit
     * @postcondition           The number is parsed and currToken is after
     *                           the number
     */
    private Number parseNumber() throws IOException, LanguageException
    {
        int val = ((DigitToken) (currToken)).getValue();
        eat(currToken);
        return new Number(val);
    }
    
    /*
    private Keyword parseKeyword() throws IOException, LanguageException
    {
        Keyword val = ((KeywordToken) (currToken)).getValue();
        eat(currToken);
        return val;
    }
    */
    
    /**
     * Gets the Expression value of the expression
     * @return                   The value of the current expression
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition           The expression is parsed and currToken is after
     *                           the expression
     */
    private Expression parseExpr() throws IOException, LanguageException
    {
        Expression val = parseTerm();
        while (currToken instanceof OperandToken)
        {
            Operand oper = parseOperand();
            Expression secondTerm = parseTerm();
            return new BinOp(val, oper, secondTerm);
        }
        return val;
    }

    /**
     * Parses the current identifier
     * @return                   The String name of the identifier
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition            The identifier is parsed and currToken is after
     *                           the identifier
     */
    private String parseIdentifier() throws IOException, LanguageException
    {
        IdentifierToken token = (IdentifierToken) (currToken);
        eat(token);
        return token.getValue();
    }

    /**
     * Parses the current String
     * @return                   The Expression value of the String
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition           The string is parsed and currToken is after
     *                           the string
     */
    private Str parseString() throws IOException, LanguageException
    {
        StringToken token = (StringToken) (currToken);
        eat(token);
        return new Str(token.getValue());
    }
    
    /**
     * Gets Expression value of the Boolean
     * @return The value of the boolean
     * @throws IOException
     * @throws LanguageException
     */
    private Bool parseBoolean() throws IOException, LanguageException
    {
        BooleanToken token = (BooleanToken) (currToken);
        eat(token);
        return new Bool(token.getValue());
    }

    /**
     * Parses current factor
     * @return                   The Expression value of the factor
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition            The factor is parsed and currToken is after
     *                           the factor
     */
    private Expression parseFactor() throws IOException, LanguageException
    {
        if (
            currToken instanceof SeperatorToken && 
            ((SeperatorToken) (currToken)).equals(SeperatorToken.Seperator.OPEN_PAREN))
        {
            eat(new SeperatorToken("("));
            Expression val = parseExpr();
            eat(new SeperatorToken(")"));
            return val;
        }
        else if (
            currToken instanceof OperandToken && 
            ((OperandToken) (currToken)).equals(Operand.SUBTRACTION)
        )
        {
            eat(new OperandToken("-"));
            return new BinOp(
                new Number(-1), 
                Operand.MULTIPLICATION, 
                parseFactor()
            );
        }
        else if (currToken instanceof DigitToken)
        {
            return parseNumber();
        }
        else if (currToken instanceof IdentifierToken)
        {
            String id = parseIdentifier();
            Expression value = new Variable(id);

            return value;
        }
        else if (currToken instanceof StringToken)
        {
            return parseString();
        }
        else if (currToken instanceof BooleanToken)
        {
            return parseBoolean();
        }
        else
        {
            throw new UnexpectedToken(currToken.getValue().toString());
        }
    }

    /**
     * Parses the current operand
     * @return                   The Operand value
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition            The operand is parsed and currToken is after
     *                           the operand
     */
    private Operand parseOperand() throws IOException, LanguageException
    {
        OperandToken curr = (OperandToken) (currToken);
        eat(curr);
        return curr.getValue();
    }

    /**
     * Parses the current term
     * @return                   The Expression value of the term
     * @throws IOException       When there is a problem reading the file 
     * @throws LanguageException When there is an error in the Pascal program
     * @postcondition            The term is parsed and currToken is after
     *                           the term
     */
    private Expression parseTerm() throws IOException, LanguageException
    {
        Expression expr = parseFactor();
        if (expr instanceof Number)
        {
            while ((currToken instanceof OperandToken) && (
                ((OperandToken) (currToken)).equals(Operand.MULTIPLICATION) ||
                ((OperandToken) (currToken)).equals(Operand.DIVISION) ||
                ((OperandToken) (currToken)).equals(Operand.MODULO) ||
                ((OperandToken) (currToken)).equals(Operand.EQUALS) ||
                ((OperandToken) (currToken)).equals(Operand.NOT_EQUAL) ||
                ((OperandToken) (currToken)).equals(Operand.GREATER) ||
                ((OperandToken) (currToken)).equals(Operand.LESS) ||
                ((OperandToken) (currToken)).equals(Operand.GREATER_OR_EQUAL) ||
                ((OperandToken) (currToken)).equals(Operand.LESS_OR_EQUAL)

            ))
            {
                Operand oper = parseOperand();
                Expression second = expectType(parseFactor(), Number.class);
                
                /*
                if (
                    ((OperandToken) (currToken)).equals(Operand.MULTIPLICATION) ||
                    ((OperandToken) (currToken)).equals(Operand.DIVISION) ||
                    ((OperandToken) (currToken)).equals(Operand.MODULO) ||
                    ((OperandToken) (currToken)).equals(Operand.EQUALS) ||
                    ((OperandToken) (currToken)).equals(Operand.NOT_EQUAL) ||
                    ((OperandToken) (currToken)).equals(Operand.GREATER) ||
                    ((OperandToken) (currToken)).equals(Operand.LESS) ||
                    ((OperandToken) (currToken)).equals(Operand.GREATER_OR_EQUAL) ||
                    ((OperandToken) (currToken)).equals(Operand.LESS_OR_EQUAL)
                )
                {
                }
                */
                expr = new BinOp(expr, oper, second);
            }
            return expr;
        }
        else if (expr instanceof Str)
        {
            return expr;
        }
        else
        {
            return expr;
        }
    }

    /**
     * Parses the current statement
     * @throws IOException       When there is a problem reading the file
     * @throws LanguageException When there is an error in the Pascal program
     * @throws Break
     * @return                  The statement
     */
    private Statement parseStatement() throws IOException, LanguageException
    {
        Statement stmt;
        Token token = currToken;
        eat(token);
        if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.EOF)
        )
        {
            System.exit(0);
            throw new IOException(); // dummy throw to pass IO exception
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.WRITELN)
        )
        {
            eat(new SeperatorToken("("));
            stmt = new Writeln(parseExpr());
            eat(new SeperatorToken(")"));
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.READLN))
        {
            eat(new SeperatorToken("("));
            String varName = expectType(parseIdentifier(), String.class);
            eat(new SeperatorToken(")"));
            stmt = new Readln(varName);
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.BEGIN)
        )
        {
            Token statementOpener = currToken;
            LinkedList<Statement> blockStatements = new LinkedList<Statement>();
            while (!(
                (statementOpener instanceof KeywordToken) && ((KeywordToken) statementOpener).equals(Keyword.END)
            ))
            {
                blockStatements.add(parseStatement());
                statementOpener = currToken;
            }
            eat(new KeywordToken(Keyword.END.name()));
            stmt = new Block(blockStatements);
        }
        else if (
            token instanceof KeywordToken && 
            ((KeywordToken) (token)).equals(Keyword.FOR)
        )
        {
            String startName = parseIdentifier();
            eat(new OperandToken(":="));
            Expression startVal = parseExpr();
            env.setVariable(startName, startVal);
            eat(new KeywordToken(Keyword.TO.name()));
            Expression endVal = parseExpr();
            eat(new KeywordToken(Keyword.DO.name()));
            Statement _do = parseStatement();
            return new For(startName, endVal, _do);
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.BREAK))
        {
            stmt = new Brk();
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.CONTINUE))
        {
            stmt = new Cont();
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.IF))
        {
            Expression cond = parseExpr();
            eat(new KeywordToken(Keyword.THEN.name()));
            Statement then = parseStatement();
            if ((currToken instanceof KeywordToken) && (((KeywordToken) currToken).equals(Keyword.ELSE)))
            {
                eat(currToken);
                Statement _else = parseStatement();
                return new If(cond, then, _else);
            }
            return new If(cond, then);
            // return here because we don't eat a semicolon after and if statement
        }
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.WHILE))
        {
            Expression cond = parseExpr();
            eat(new KeywordToken(Keyword.DO.name()));
            Statement _do = parseStatement();
            return new While(cond, _do);
        }
        else if (token instanceof IdentifierToken)
        {
            eat(new OperandToken(":="));
            stmt = new Assignment(((IdentifierToken) (token)).getValue(), parseExpr());
        }
        else
        {
            throw new UnexpectedToken(token);
        }
        eat(new SeperatorToken(";"));
        return stmt;
    }

    public ProcedureDecleration parseProcedure() throws IOException, LanguageException
    {
        eat(new KeywordToken(Keyword.PROCEDURE.name()));
        String name = parseIdentifier();
        eat(new SeperatorToken("("));
        eat(new SeperatorToken(")"));
        eat(new SeperatorToken(";"));
        return new ProcedureDecleration(name, parseStatement());
    }

    private Program parseProgram(Environment env) throws IOException, LanguageException
    {
        LinkedList<ProcedureDecleration> procs = new LinkedList<ProcedureDecleration>();
        while (currToken.equals(new KeywordToken(Keyword.PROCEDURE.name())))
        {
            procs.add(parseProcedure());
        }
        return new Program(procs, parseStatement(), env);
    }

    /**
     * Executes a program given the Scanner 
     * @throws LanguageException
     * @throws IOException
     */
    public void execute() throws LanguageException, IOException
    {
        while (scanner.hasNext())
        {
            try
            {
                parseStatement().exec(env);
            }
            catch (Jump err)
            {
                if (err instanceof Break)
                {
                    throw new IllegalBreak();
                }
                else
                {
                    throw new IllegalContinue();
                }
            }
        }
    }
}
