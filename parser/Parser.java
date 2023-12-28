package parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Bool;
import ast.Brk;
import ast.Cont;
import ast.Expression;
import ast.Ext;
import ast.For;
import ast.If;
import ast.Number;
import ast.ProcedureExpr;
import ast.ProcedureStmt;
import ast.ProcedureDecleration;
import ast.Program;
import ast.Readln;
import ast.Statement;
import ast.Str;
import ast.Variable;
import ast.VariableDeclaration;
import ast.While;
import ast.Writeln;
import environment.Environment;
import scanner.*;
import exceptions.*;
import jumps.Break;
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
     * Converts the current comma seperated set enclosed by parenthesis in the Token stream
     * to a List object. For example, '"hi", 1, 4' in in the token stream would be converted
     * to a List containing [Str("hi"), Number(1), Number(4)]
     * @param terminator     The token that ends the list; when this token is seen,
     *                       this method returns (without eating the terminator)
     * @param expectedClass  The type of the elements in the List; when parsing
     *                       a set of parameter names, use String.class, when 
     *                       parsing a list of parameter values, use Expression.class
     * 
     * @return               The List of values in the comma-seperated set
     */
    public <T> List<T> parseCommaSeperatedSet(Class<T> expectedClass, Seperator terminator) throws IOException, LanguageException
    {
        List<T> items = new LinkedList<T>();
        while (!
            (
                (currToken instanceof SeperatorToken) && ((SeperatorToken) currToken).equals(terminator)
            )
        )
        {
            if (expectedClass.equals(String.class)) // param names
            {
                ((List<String>) items).add(parseIdentifier());
            }
            else // param values
            {
                ((List<Expression>) items).add(parseExpr());

            }
            if ((currToken instanceof SeperatorToken) && ((SeperatorToken) currToken).equals(Seperator.COMMA))
            {
                eat(new SeperatorToken(","));
                // TODO: make sure comma exists before new param value
                // TODO: right now, this may pass: "func(1 2)" without commas
            }
        }
        return items;
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
        Expression firstTerm = parseTerm();
        while (currToken instanceof OperandToken)
        {
            Operand oper = parseOperand();
            Expression secondTerm = parseTerm();
            if (oper.equals(Operand.ADDITION) || oper.equals(Operand.SUBTRACTION) || BinOp.isComparisonOperator(oper))
            {
                firstTerm = new BinOp(firstTerm, oper, secondTerm);
            }
        }
        return firstTerm;
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
            if (currToken.equals(new SeperatorToken("(")))
            {
                eat(new SeperatorToken("("));
                List<Expression> params = parseCommaSeperatedSet(Expression.class, Seperator.CLOSE_PAREN);
                eat(new SeperatorToken(")"));
                if (params == null)
                {
                    return new ProcedureExpr(id);
                }
                return new ProcedureExpr(id, params);
            }
            return new Variable(id);
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
        if (expr instanceof Number || expr instanceof Variable)
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
                Expression second = parseFactor();
                
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
            ((KeywordToken) (token)).equals(Keyword.VAR)
        )
        {
            return parseVariableDec();
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
            List<Statement> blockStatements = new LinkedList<Statement>();
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
        else if (token instanceof KeywordToken && ((KeywordToken) (token)).equals(Keyword.EXIT))
        {
            if (currToken.equals(new SeperatorToken("(")))
            {

                eat(new SeperatorToken("("));
                stmt = new Ext(parseExpr());
                eat(new SeperatorToken(")"));
            }
            else
            {
                stmt = new Ext();
            }
        }
        else if (token instanceof IdentifierToken)
        {
            if (currToken.equals(new OperandToken(":=")))
            {
                eat(new OperandToken(":="));
                stmt = new Assignment(((IdentifierToken) (token)).getValue(), parseExpr());
            }
            else
            {
                eat(new SeperatorToken("("));
                List<Expression> params = parseCommaSeperatedSet(Expression.class, Seperator.CLOSE_PAREN);
                eat(new SeperatorToken(")"));
                if (params == null)
                {
                    stmt = new ProcedureStmt(((IdentifierToken) token).toString());
                }
                else
                {
                    stmt = new ProcedureStmt(((IdentifierToken) token).toString(), params);
                }
            }
        }
        else
        {
            throw new UnexpectedToken(token);
        }
        eat(new SeperatorToken(";"));
        return stmt;
    }

    /**
     * Parses the current procedure in the Token stream
     * @return  The Procedure decleration of the procedure
     * @throws IOException
     * @throws LanguageException
     */
    public ProcedureDecleration parseProcedure() throws IOException, LanguageException
    {
        eat(new KeywordToken(Keyword.PROCEDURE.name()));
        String name = parseIdentifier();
        eat(new SeperatorToken("("));
        List<String> paramNames = parseCommaSeperatedSet(String.class, Seperator.CLOSE_PAREN);
        eat(new SeperatorToken(")"));
        eat(new SeperatorToken(";"));
        if (paramNames == null)
        {
            return new ProcedureDecleration(name, parseStatement());
        }
        return new ProcedureDecleration(name, parseStatement(), paramNames);

    }

    /**
     * Parses the current variable declaration statement in the Token stream. This is important
     * so that the compiler can allocate space to variables.
     * @return The variable declaration Statement 
     * @throws IOException
     * @throws LanguageException
     */
    public VariableDeclaration parseVariableDec() throws IOException, LanguageException
    {
        VariableDeclaration dec = new VariableDeclaration(parseCommaSeperatedSet(String.class, Seperator.SEMICOLON));
        eat(new SeperatorToken(";"));
        return dec;
    }

    /**
     * Parses the entire program by building an abstract syntax tree 
     * representation of the Pascal program
     * @return  The root of the constructed AST
     * @throws IOException
     * @throws LanguageException
     */
    private Program parseProgram() throws IOException, LanguageException
    {
        List<ProcedureDecleration> procs = new LinkedList<ProcedureDecleration>();
        List<VariableDeclaration> varDecs = new LinkedList<VariableDeclaration>();
        while (currToken.equals(new KeywordToken(Keyword.PROCEDURE.name())))
        {
            procs.add(parseProcedure());
        }
        while (currToken.equals(new KeywordToken(Keyword.VAR.name())))
        {
            eat(new KeywordToken(Keyword.VAR.name()));
            varDecs.add(parseVariableDec());
        }
        return new Program(
            varDecs, 
            procs, 
            expectType(parseStatement(), Block.class) // TODO: throw error message
        );
    }

    /**
     * Executes a program given the Scanner 
     * @throws LanguageException
     * @throws IOException
     */
    public void execute() throws LanguageException, IOException
    {
        parseProgram().exec(env);
    }
    
    /**
     * Compiles the program and writes the MIPS output to a file
     * @param fileName  The file to write the MIPS code to
     */
    public void compile(String fileName) throws LanguageException, IOException
    {
        parseProgram().compile(fileName);
    }
}
