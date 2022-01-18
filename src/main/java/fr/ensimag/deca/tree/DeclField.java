package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.io.ObjectInputStream.GetField;

import org.apache.commons.lang.Validate;

public class DeclField extends AbstractDeclField {

    private AbstractIdentifier field;
    private AbstractInitialization init;
    private Visibility visib;
    

    public DeclField( AbstractIdentifier field, AbstractInitialization init){
        Validate.notNull(field);
        Validate.notNull(init);
        this.field = field;
        this.init = init;
    }

    public DeclField(AbstractIdentifier tree, AbstractExpr tree2, AbstractInitialization init2) {
    }

    public AbstractIdentifier getField(){
        return this.field;
    }

    @Override
    public void verifyField(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
    throws ContextualError {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.field.prettyPrint(s, prefix, false);
        this.init.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
