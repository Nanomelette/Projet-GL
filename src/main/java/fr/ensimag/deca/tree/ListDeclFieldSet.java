package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclFieldSet extends TreeList<AbstractDeclFieldSet>{

    public void verifyListField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe) {
    }

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
