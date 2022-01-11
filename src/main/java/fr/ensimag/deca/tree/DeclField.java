package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclField {

    private Identifier field;
    private Initialization init;

    public DeclField(Identifier field, Initialization init){
        Validate.notNull(field);
        Validate.notNull(init);
        this.field = field;
        this.init = init;
    }

    protected void verifyField(DecacCompiler compiler, Identifier classeSup, Identifier classe)
            throws ContextualError{
            this.verifyField(compiler, classeSup, classe);
        }
    
}
