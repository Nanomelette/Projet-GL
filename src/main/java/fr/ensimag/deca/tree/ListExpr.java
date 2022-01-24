package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ListExpr extends TreeList<AbstractExpr> {

    public void verifyListExp(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Signature sig)
        throws ContextualError {
        int i = 0;
        if (getList().size() > sig.size()) {
            throw new ContextualError("MethodCall: too many args", getLocation());
        }
        if (getList().size() < sig.size()) {
            throw new ContextualError("MethodCall: too few args", getLocation());
        }
        for (AbstractExpr e : getList()){
            e = e.verifyRValue(compiler, localEnv, currentClass, sig.paramNumber(i));
            Type type = e.verifyExpr(compiler, localEnv, currentClass);
            e.setType(type);
            this.set(i, e);
            i++;
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        int enjolivage = 0;
        for(AbstractExpr c : getList()){
            if (enjolivage > 0) {
                s.print(" ");
            }
            c.decompile(s);
            if (enjolivage < size() - 1) {
                s.print(",");
            }
            enjolivage++;
        }
    }

    public void codeGenListExpr(DecacCompiler compiler) {
        int i = -1;
        for (AbstractExpr expr : getList()) {
            GPRegister restoreRegister = compiler.getData().getLastUsedRegister();
            expr.codeGenInst(compiler);
            GPRegister register = compiler.getData().getLastUsedRegister();
            compiler.addInstruction(new STORE(register, new RegisterOffset(i, Register.SP)));
            compiler.getData().restoreDataTo(restoreRegister.getNumber());
            i--;
        }
    }
}
