package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclParam {

    private Identifier type;
    private Identifier name;

    public DeclParam(Identifier type, Identifier name){
        Validate.notNull(type);
        Validate.notNull(name);
        this.type = type;
        this.name = name;
    }

    protected void verifyParam(DecacCompiler compiler)
            throws ContextualError{
            throw new UnsupportedOperationException("not yet implemented");
        }
    
}
