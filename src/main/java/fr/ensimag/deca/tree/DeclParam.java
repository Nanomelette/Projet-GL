package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

public class DeclParam extends AbstractDeclParam{

    private AbstractIdentifier type;
    private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier name){
        Validate.notNull(type);
        Validate.notNull(name);
        this.type = type;
        this.name = name;
    }

    protected Type verifyParam(DecacCompiler compiler)
            throws ContextualError{
            Type paramType = type.verifyType(compiler);
            if (paramType.isVoid()){
                throw new ContextualError("type void", getLocation());
            }
            ExpDefinition newDef= new VariableDefinition(paramType, name.getLocation());
            name.setDefinition(newDef);
            name.setType(paramType);
            return paramType;
        }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile();
        s.print(" ");
        name.decompile();
        
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
