package ast;

import emitter.Emitter;
import environment.Environment;
import exceptions.LanguageException;
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
    private static int currLabelID;
    private Expression left;
    private Operand operand;
    private Expression right;

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
    public Expression eval(Environment env) throws LanguageException
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
    public String toString(Environment env) throws LanguageException
    { 
        return left.toString(env) + " " + operand + " " + right.toString(env);
    }    

    /**
     * Checks whether an operator is a comparison operator
     * @param oper  The operator to test
     * @return      Whether the operator is a comparison
     */
    public static boolean isComparisonOperator(Operand oper)
    {
        return (
            oper.equals(Operand.GREATER) || 
            oper.equals(Operand.GREATER_OR_EQUAL) || 
            oper.equals(Operand.LESS) ||
            oper.equals(Operand.LESS_OR_EQUAL) ||
            oper.equals(Operand.EQUALS) ||
            oper.equals(Operand.NOT_EQUAL)
        );
    }

    /**
     * Gets the MIPS instruction corresponding to an Operand
     * @param oper  The operand to convert
     * @return      The mips instruction corresponding to oper
     * @throws IllegalArgumentException
     */
    private static String getOperandInstruction(Operand oper) throws IllegalArgumentException
    {
        if (oper.equals(Operand.ADDITION))
        {
            return "addu";
        }
        if (oper.equals(Operand.SUBTRACTION))
        {
            return "subu";
        }
        if (oper.equals(Operand.MULTIPLICATION))
        {
            return "mul";
        }
        throw new IllegalArgumentException("Operator " + oper + " not allowed in compileOperand");
    }
    
    /**
     * Gets the opposite of a MIPS instruction corresponding to an Operand
     * @param oper  The operand whose inverse is to be retrieved
     * @return      The mips instruction corresponding to the inverse of oper
     * @throws IllegalArgumentException
     */
    private static String getOperandInverseInstruction(Operand oper) throws IllegalArgumentException
    {
        if (oper.equals(Operand.EQUALS))
        {
            return "bne";
        }
        if (oper.equals(Operand.NOT_EQUAL))
        {
            return "beq";
        }
        if (oper.equals(Operand.LESS))
        {
            return "bge";
        }
        if (oper.equals(Operand.LESS_OR_EQUAL))
        {
            return "bgt";
        }
        if (oper.equals(Operand.GREATER))
        {
            return "ble";
        }
        if (oper.equals(Operand.GREATER_OR_EQUAL))
        {
            return "blt";
        }
        throw new IllegalArgumentException("Operator " + oper + " not allowed in compileOperand");
    }
    
    /**
     * Inverses the operand of the BinOp
     * @poscondition    Instance field operand is inversed
     */
    public void inverse() throws IllegalArgumentException
    {
        if (operand.equals(Operand.EQUALS))
        {
            operand = Operand.NOT_EQUAL;
        }
        else if (operand.equals(Operand.NOT_EQUAL))
        {
            operand = Operand.EQUALS;
        }
        else if (operand.equals(Operand.LESS))
        {
            operand = Operand.GREATER_OR_EQUAL;
        }
        else if (operand.equals(Operand.LESS_OR_EQUAL))
        {
            operand = Operand.GREATER;
        }
        else if (operand.equals(Operand.GREATER))
        {
            operand = Operand.LESS_OR_EQUAL;
        }
        else if (operand.equals(Operand.GREATER_OR_EQUAL))
        {
            operand = Operand.LESS;
        }
        else
        {
            throw new IllegalArgumentException("Operator " + operand + " not allowed in compileOperand");
        }
    }

    /**
     * Writes the MIPS instructions to execute this BinOp
     * @param emitter   The emitter to use to output MIPS instructions
     */
    @Override
    public void compile(Emitter emitter)
    {
        left.compile(emitter); // v0 now has the left
        if (isComparisonOperator(operand))
        {
            emitter.emitPush("$v0"); // left is now on stack
            right.compile(emitter); // v0 now has the right
            emitter.emit("move $t0, $v0"); // t0 now has the right
            emitter.emitPop("$t1"); // t1 now has the left
            emitter.emit(getOperandInverseInstruction(operand) + " $t1, $t0, " + getCurrLabel()); // jump statement
        }
        else if (
            operand.equals(Operand.ADDITION) || 
            operand.equals(Operand.SUBTRACTION) || 
            operand.equals(Operand.MULTIPLICATION)
        )
        {
            emitter.emitPush("$v0"); // left is now on stack
            right.compile(emitter); // v0 now has the right
            emitter.emit("move $t0, $v0"); // t0 now has the right
            emitter.emitPop("$t1"); // t1 now has the left
            emitter.emit(getOperandInstruction(operand) + " $v0, $t1, $t0"); // v0 now has the sum
        }
        else if (operand.equals(Operand.DIVISION))
        {
            emitter.emitPush("$v0"); // left is now on stack
            right.compile(emitter); // v0 now has the right
            emitter.emit("move $t0, $v0"); // t0 now has the right
            emitter.emitPop("$t1"); // t1 now has the left
            emitter.emit("div $t1, $t0"); // v0 now has the sum
            emitter.emit("mflo $v0");
        }
        else
        {
            throw new UnsupportedOperationException(operand + " not implemented in compiled pascal");
        }
    }

    /**
     * Sets the label ID for the next conditional to be written
     * @param currLabelID   The ID of the label
     */
    public static void setCurrLabelID(int currLabelID)
    {
        BinOp.currLabelID = currLabelID;
    }

    /**
     * Gets the label of the conditional being outputted
     * @return  The label of the conditional being outputted
     */
    public static String getCurrLabel()
    {
        return "endif" + currLabelID;
    }
}
