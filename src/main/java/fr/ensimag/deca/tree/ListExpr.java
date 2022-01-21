package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ListExpr extends TreeList<AbstractExpr> {

    public Signature verifyListExp(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
        throws ContextualError {
        Signature sig = new Signature();
            for (AbstractExpr e : getList()){
            Type type = e.verifyExpr(compiler, localEnv, currentClass);
            sig.add(type);
        }
        return sig;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        for(AbstractExpr c : getList()){
            c.decompile(s);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
