package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
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


    @Override
    public void decompile(IndentPrintStream s) {
        for(AbstractExpr c : getList()){
            c.decompile(s);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
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
