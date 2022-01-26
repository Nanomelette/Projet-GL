package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 * <p>ListDeclMethod class.</p>
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclMethod.class);

    /** {@inheritDoc} */
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
    void verifyListMethod(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError {
        LOG.debug("verify listMethod: start");

        for (AbstractDeclMethod c : this.getList()) {
            c.verifyMethod(compiler, classeSup, classe);
        }
        LOG.debug("verify listMethod: end");
    }

    /**
     * <p>codeGenListDeclMethod.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     */
    public void codeGenListDeclMethod(DecacCompiler compiler) {
        for (AbstractDeclMethod declMethod : getList()) {
            declMethod.codeGenDeclMethod(compiler);
        }
    }

    /**
     * <p>verifyListMethodBody.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param classeSup a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param classe a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @throws fr.ensimag.deca.context.ContextualError if any.
     */
    public void verifyListMethodBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError {

        for (AbstractDeclMethod c : this.getList()) {
            c.verifyMethodBody(compiler, classeSup, classe);
        }
    }

}
