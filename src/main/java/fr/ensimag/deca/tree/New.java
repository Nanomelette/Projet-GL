package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class New extends AbstractExpr {

    private AbstractIdentifier class_;

    public New(AbstractIdentifier class_){
        this.class_ = class_;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type = compiler.searchSymbol(this.class_.getName());
        if(type == null){
            throw new ContextualError("New class cannot be null", getLocation());
        }
        if(!type.isClass()){
            throw new ContextualError("the type of the class must be a class", getLocation());
        }
        ClassType classType = (ClassType) type;
        class_.setType(classType);
        class_.setDefinition(classType.getDefinition());
        setType(classType);
        return classType;
    }

    @Override
    public void decompile(IndentPrintStream s) {     
        s.print("new");
        s.print(class_.decompile());
        s.print("()");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        class_.prettyPrint(s, prefix, false);        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        class_.iter(f);
        
    }
    
}
