package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclMethod {

    private AbstractIdentifier type;
    private AbstractIdentifier name;
    private ListDeclParam listDeclParam;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, ListDeclParam listDeclParam){
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(listDeclParam);
        this.type = type;
        this.name = name;
        this.listDeclParam = listDeclParam;
    }

    protected void verifyMethod(DecacCompiler compiler, AbstractIdentifier classeSup)
            throws ContextualError{
            this.verifyMethod(compiler, classeSup);
        }
    
}
