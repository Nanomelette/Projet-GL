package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclParam {

    private Identifier param;

    public DeclParam(Identifier param){
        Validate.notNull(param);
        this.param = param;
    }

    protected void verifyParam(DecacCompiler compiler)
            throws ContextualError{
            this.verifyParam(compiler);
        }
    
}
