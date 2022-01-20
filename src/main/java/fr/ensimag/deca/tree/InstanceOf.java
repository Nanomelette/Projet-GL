package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import net.bytebuddy.utility.dispatcher.JavaDispatcher.Instance;

public class InstanceOf extends AbstractExpr{


    private AbstractExpr e;
    private AbstractIdentifier type;


    public InstanceOf(AbstractExpr e, AbstractIdentifier type){
        super();
        Validate.notNull(e);
        Validate.notNull(type);
        this.type = type;
        this.e = e;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type1 = this.e.verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.type.verifyType(compiler);
        if((type1 == null || type1.isClass()) && type2.isClass()){
            return compiler.searchSymbol(compiler.getSymbolTable().create("boolean"));
        }
        throw new ContextualError("Incorrect types", getLocation());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        e.decompile(s);
        s.print(" instanceOf ");
        type.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // TODO Auto-generated method stub
        
    }
    
}
