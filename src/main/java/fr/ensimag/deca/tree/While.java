package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 *
 * @author gl20
 * @date 01/01/2022
 */
public class While extends AbstractInst {
    private AbstractExpr cond;
    private ListInst inst;

    public AbstractExpr getCondition() {
        return cond;
    }

    public ListInst getInst() {
        return inst;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.cond = condition;
        this.inst = body;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        int n = compiler.getNLabel();
        Label E_Cond = new Label("E_Cond."+n);
        Label E_Start = new Label("E_Start."+n);
        compiler.addInstruction(new BRA(E_Cond));
        compiler.addLabel(E_Start);
        inst.codeGenListInst(compiler);
        compiler.addLabel(E_Cond);
        cond.codeBoolean(true, E_Start, compiler);
        compiler.incrNLabel();
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
                Type type = this.cond.verifyExpr(compiler, localEnv, currentClass);
                this.cond.setType(type);
                this.inst.verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getInst().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        cond.iter(f);
        inst.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        cond.prettyPrint(s, prefix, false);
        inst.prettyPrint(s, prefix, true);
    }
}
