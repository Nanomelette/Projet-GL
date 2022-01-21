package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclMethod.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    void verifyListMethod(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe) throws ContextualError {
        LOG.debug("verify listMethod: start");

        for(AbstractDeclMethod c : this.getList()){
            c.verifyMethod(compiler, classeSup, classe);
        }

        //throw new UnsupportedOperationException("not yet implemented");
        LOG.debug("verify listMethod: end");
    }

}
