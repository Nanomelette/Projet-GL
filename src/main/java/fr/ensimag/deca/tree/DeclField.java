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

    private Identifier field;
    private Initialization init;
    

    public DeclField(Identifier field, Initialization init){
        Validate.notNull(field);
        Validate.notNull(init);
        this.field = field;
        this.init = init;
    }

    public Identifier getField(){
        return this.field;
    }

    public Initialization getInit(){
        return this.init;
    }

    @Override
    public void verifyField(DecacCompiler compiler, Identifier classeSup, Identifier classe)
    throws ContextualError {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(getField().decompile());
        s.print(getInit().decompile());
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
