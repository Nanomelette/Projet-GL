package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Unary expression.
 *
 * @author gl20
 * @date 01/01/2022
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }


    protected abstract String getOperatorName();
  
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        operand.decompile(s);
        s.print(")");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }

    protected GPRegister op;

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();

        if (operand.getDVal() != null) {
            DVal dval = operand.getDVal();
            op = compiler.getData().getFreeRegister(compiler);
            compiler.addInstruction(new LOAD(dval, op));
        } else {
            if (data.hasFreeRegister()) {
                operand.codeGenInst(compiler);
                op = data.getLastUsedRegister();
            } else {
                compiler.addInstruction(new PUSH(data.getMaxRegister()), "sauvegarde");
                operand.codeGenInst(compiler);
                op = data.getLastUsedRegister();
                compiler.addInstruction(new LOAD(op, GPRegister.R0));
                compiler.addInstruction(new POP((GPRegister)op), "restauration");
                data.decrementFreeStoragePointer();
            }
        }
    }
}
