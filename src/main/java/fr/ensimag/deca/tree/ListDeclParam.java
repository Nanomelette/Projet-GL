package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {
    
    @Override
    public void decompile(IndentPrintStream s) {
        int enjolivage = 0;
        for (AbstractDeclParam c : getList()) {
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

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    Signature verifyListParam(DecacCompiler compiler) throws ContextualError {
        Signature signature = new Signature();
        for(AbstractDeclParam c : this.getList()){
            signature.add(c.verifyParam(compiler));
        }
        return signature;
    }

    void verifyListDeclParam(DecacCompiler compiler, EnvironmentExp localEnv) throws ContextualError {
        int index = 1;
        for(AbstractDeclParam c : this.getList()){
            c.verifyDeclParam(compiler, localEnv, index);
            index++;
        }
    }

}
