package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.tools.IndentPrintStream;

import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;

import java.io.PrintStream;
// import org.apache.log4j.Logger;

import org.apache.commons.lang.Validate;

/**
 * Binary expressions.
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    // private static final Logger LOG = Logger.getLogger(AbstractBinaryExpr.class);

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if (getOperatorName() != "=") {
            s.print("(");
        }
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        if (getOperatorName() != "=") {
            s.print(")");
        }
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

    protected DVal op1;
    protected GPRegister op2;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        AbstractExpr leftOperand = getLeftOperand();
        AbstractExpr rightOperand = getRightOperand();

        Data data = compiler.getData();

        if (rightOperand.getDVal() != null) {
            // <codeExp(e1,n)>
            leftOperand.codeGenInst(compiler);
            op2 = data.getLastUsedRegister();
            // <dval(e2)>
            op1 = rightOperand.getDVal();
        } else {
            if (data.hasFreeRegister()) {
                if (data.nbFreeRegsiter() == 1) {
                    // <codeExp(e1,n)>
                    leftOperand.codeGenInst(compiler);
                    op2 = data.getLastUsedRegister();
                    data.incrementFreeStoragePointer();
                    // <codeExp(e2,n+1)>
                    rightOperand.codeGenInst(compiler);
                    op1 = (DVal) data.getLastUsedRegister();
                } else {
                    leftOperand.codeGenInst(compiler);
                    op2 = data.getLastUsedRegister();
                    rightOperand.codeGenInst(compiler);
                    op1 = (DVal) data.getLastUsedRegister();
                }
            } else {
                // <codeExp(e1,n)> - PUSH Rn ; sauvegarde
                leftOperand.codeGenInst(compiler);
                compiler.addInstruction(new PUSH(data.getMaxRegister()), "sauvegarde");
                // <codeExp(e2,n)>
                rightOperand.codeGenInst(compiler);
                op2 = data.getMaxRegister();
                // LOAD Rn, R0
                compiler.addInstruction(new LOAD(op2, GPRegister.R0));
                // POP Rn;
                compiler.addInstruction(new POP((GPRegister) op2), "restauration");
                // data.decrementFreeStoragePointer();
                op1 = (DVal) GPRegister.R0;
            }
        }
    }

}
