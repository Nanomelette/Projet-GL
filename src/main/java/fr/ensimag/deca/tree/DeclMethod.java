package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclMethod {

    private Identifier method;
    private ListDeclParam listDeclParam;

    public DeclMethod(Identifier method, ListDeclParam listDeclParam){
        Validate.notNull(method);
        Validate.notNull(listDeclParam);
        this.method = method;
        this.listDeclParam = listDeclParam;
    }

    protected void verifyMethod(DecacCompiler compiler, Identifier classeSup)
            throws ContextualError{
            this.verifyMethod(compiler, classeSup);
        }
    
}
