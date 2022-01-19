package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class MethodCall extends AbstractExpr {

    private AbstractExpr obj;
    private AbstractIdentifier meth;
    private ListExpr param;

    public MethodCall(AbstractExpr expr, AbstractIdentifier method, ListExpr listExpr){
        this.obj = expr;
        this.meth = method;
        this.param = listExpr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        System.out.println(this.obj);
        Identifier a = (Identifier) this.obj;
        System.out.println(a.getName());
        System.out.println(compiler.GetEnvExp().getDictionnary().keySet().toString());
        int i=0;
        while(i < compiler.GetEnvExp().getDictionnary().keySet().size()){
            if(a.getName().equals(((Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i]).getName())){
                break;
            }
            i++;
        }
        if ((i == compiler.GetEnvExp().getDictionnary().keySet().size())) {
            throw new ContextualError("identifier not defined", getLocation());
        }
        Symbol symb = (Symbol)compiler.GetEnvExp().getDictionnary().keySet().toArray()[i];
        if (compiler.GetEnvExp().get(symb) != null) {
            setType(compiler.GetEnvExp().get(symb).getType());
            return compiler.GetEnvExp().get(symb).getType();
        }
        throw new UnsupportedOperationException("Invalid expression");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        if( this.obj != null){
            s.print(this.obj.decompile());
            s.print(".");
        }
        s.print(this.meth.decompile());
        s.print("(");
        s.print(this.param.decompile());
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
