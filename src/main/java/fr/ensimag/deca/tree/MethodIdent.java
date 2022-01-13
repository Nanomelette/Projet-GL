package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class MethodIdent {

    AbstractIdentifier ident_method;

    public MethodIdent(AbstractIdentifier ident_method){
        this.ident_method = ident_method;
    }
    
}
