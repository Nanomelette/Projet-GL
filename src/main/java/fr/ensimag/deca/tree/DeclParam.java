package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam{

    private AbstractIdentifier type;
    private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier tree, AbstractIdentifier tree2){
        Validate.notNull(tree);
        Validate.notNull(tree2);
        this.type = tree;
        this.name = tree2;
    }

    protected void verifyParam(DecacCompiler compiler)
            throws ContextualError{
            throw new UnsupportedOperationException("not yet implemented");
        }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(type.decompile());
        s.print(" ");
        s.print(name.decompile());
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false); 
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
    }
    
}
