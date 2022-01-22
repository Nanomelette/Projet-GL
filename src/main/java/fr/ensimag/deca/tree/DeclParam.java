package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ParamDefinition;
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
                throw new ContextualError("type void", type.getLocation());
            }
            return paramType;
        }
    
    protected void verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv)
            throws ContextualError{
            try {
                ParamDefinition newDef = new ParamDefinition(type.getType(), name.getLocation());
                name.setDefinition(newDef);
                name.setType(type.getType());
                localEnv.declare(name.getName(), newDef);
            } catch (EnvironmentExp.DoubleDefException e) {
                String message = "can't defined method identifier several times in a class";
                throw new ContextualError(message, name.getLocation());
            }
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
