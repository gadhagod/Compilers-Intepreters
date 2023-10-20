package ast;

import environment.Environment;
import exceptions.TypeMismatch;
import exceptions.VariableNotDefined;
import parser.Parser;
import scanner.OperandToken.Operand;

/**
 * Represents a binary operation or comparison
 * @author  Aarav Borthakur
 * @version 10/17/23
 */
public class BinOp extends Expression 
{
    Expression left;
    Operand operand;
    Expression right;

    /**
     * Constructs a BinOp given the operands and the operator
     * @param left      The left operand
     * @param operand   The operator
     * @param right     The right operand
     * @throws TypeMismatch
     */
    public BinOp(Expression left, Operand operand, Expression right) throws TypeMismatch
    {
        this.left = left;
        this.operand = operand;
        this.right = right;
    }

    /**
     * Evaulates the operation
     * @param env  The Environment this statement is in
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    @Override
    public Expression eval(Environment env) throws TypeMismatch, VariableNotDefined
    {
        Object leftVal = left;
        while (leftVal instanceof Expression)
        {
            leftVal = ((Expression) leftVal).eval(env);
        }
        Object rightVal = right;
        while (rightVal instanceof Expression)
        {
            rightVal = ((Expression) rightVal).eval(env);
        }

        Parser.expectType(rightVal, leftVal.getClass());
        if (leftVal instanceof String)
        {
            String leftStr = (String) leftVal;
            String rightStr = (String) rightVal;
            if (operand.equals(Operand.ADDITION))
            {
                return new Str(leftStr + rightStr);
            }
            else if (operand.equals(Operand.EQUALS))
            {
                return new Bool(leftStr.equals(rightStr));
            }
            else if (operand.equals(Operand.NOT_EQUAL))
            {
                return new Bool(!leftStr.equals(rightStr));
            }
            else
            {
                throw new TypeMismatch(leftStr, rightStr, operand);
            }
        }
        else if (leftVal instanceof Integer)
        {
            Integer leftNum = (Integer) leftVal;
            Integer rightNum = (Integer) rightVal;
            if (operand.equals(Operand.ADDITION))
            {
                return new Number(leftNum + rightNum);
            }
            else if (operand.equals(Operand.SUBTRACTION))
            {
                return new Number(leftNum - rightNum);
            }
            else if (operand.equals(Operand.MULTIPLICATION))
            {
                return new Number(leftNum * rightNum);
            }
            else if (operand.equals(Operand.DIVISION))
            {
                return new Number(leftNum / rightNum);
            }
            else if (operand.equals(Operand.EQUALS))
            {
                return new Bool(leftNum.equals(rightNum));
            }
            else if (operand.equals(Operand.GREATER))
            {
                return new Bool(leftNum > rightNum);
            }
            else if (operand.equals(Operand.LESS))
            {
                return new Bool(leftNum < rightNum);
            }
            else if (operand.equals(Operand.GREATER_OR_EQUAL))
            {
                return new Bool(leftNum >= rightNum);
            }
            else if (operand.equals(Operand.LESS_OR_EQUAL))
            {
                return new Bool(leftNum <= rightNum);
            }
            else if (operand.equals(Operand.NOT_EQUAL))
            {
                return new Bool(leftNum != rightNum);
            }
            else
            {
                throw new TypeMismatch(leftNum, rightNum, operand);
            }
        }
        throw new TypeMismatch(leftVal.getClass().toString());
    }

    /**
     * Gets the String representation of the statement
     * @param env  The Environment this statement is in
     * @return     The String representation of the statement
     * @throws TypeMismatch
     * @throws VariableNotDefined
     */
    public String toString(Environment env) throws TypeMismatch, VariableNotDefined
    {
        return left.toString(env) + " " + operand + " " + right.toString(env);
    }    
}
