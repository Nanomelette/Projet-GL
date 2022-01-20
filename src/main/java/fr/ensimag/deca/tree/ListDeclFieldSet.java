package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.context.ContextualError;

public class ListDeclFieldSet extends TreeList<AbstractDeclFieldSet>{


    @Override
    public void decompile(IndentPrintStream s) {
        // TODO Auto-generated method stub
        
    }

    protected void codeGenListDeclFieldSet(DecacCompiler compiler) {
        for (AbstractDeclFieldSet declField : getList()) {
            declField.codeGenDeclFieldSet(compiler);
        }
    }

    protected void codeGenListDeclFieldSetZero(DecacCompiler compiler) {
        for (AbstractDeclFieldSet declField : getList()) {
            declField.codeGenDeclFieldSetZero(compiler);
        }
    }

}
