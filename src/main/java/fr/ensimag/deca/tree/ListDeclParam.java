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
public class ListDeclParam extends TreeList<AbstractDeclParam> {
    private static final Logger LOG = Logger.getLogger(ListDeclParam.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclParam c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    void verifyListParam(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listParam: start");

        for(AbstractDeclParam c : this.getList()){
            c.verifyParam(compiler);
        }

        //throw new UnsupportedOperationException("not yet implemented");
        LOG.debug("verify listParam: end");
    }

}
