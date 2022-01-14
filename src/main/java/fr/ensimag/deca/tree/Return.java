package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class Return extends AbstractInst {

    private AbstractExpr condition;
    
    public AbstractExpr getCondition() {
        return condition;
    }

    public Return(AbstractExpr condition) {
        Validate.notNull(condition);
        this.condition = condition;
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("Return (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, true);
    }

    
}
