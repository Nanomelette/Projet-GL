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
public class ListDeclField extends TreeList<AbstractDeclField> {
    private static final Logger LOG = Logger.getLogger(ListDeclField.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    void verifyListField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe) throws ContextualError {
        LOG.debug("verify listField: start");

        for(AbstractDeclField c : this.getList()){
            c.verifyField(compiler, classeSup, classe);
        }
        LOG.debug("verify listField: end");
    }

    protected void codeGenListDeclFieldSet(DecacCompiler compiler) {
        for (AbstractDeclField declField : getList()) {
            declField.codeGenDeclField(compiler);
        }
    }

    protected void codeGenListDeclFieldSetZero(DecacCompiler compiler) {
        for (AbstractDeclField declField : getList()) {
            declField.codeGenDeclFieldZero(compiler);
        }
    }
    
    void verifyListFieldBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe) throws ContextualError {

        for(AbstractDeclField c : this.getList()){
            c.verifyFieldBody(compiler, classeSup, classe);
        }
    }

}
