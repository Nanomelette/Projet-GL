package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl20
 * @date 01/01/2022
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        this.setExpression(this.expression.verifyRValue(compiler, localEnv, currentClass, t));
        if(this.expression.getType() == null && t.isClass()){
            this.expression.setType(t);
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {
        if(this.getExpression()!=null){
            s.print(" = ");
            this.getExpression().decompile(s);
        }
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, false);
    }

    @Override
    protected void codeGenInitField(DecacCompiler compiler) {
        expression.codeGenInst(compiler);
        compiler.addInstruction(new LOAD(compiler.getData().getLastUsedRegister(), Register.R0));
    }

    protected void codeGenInitVar(DecacCompiler compiler, DAddr address) {
        expression.codeGenInst(compiler);
        compiler.addInstruction(new STORE(compiler.getData().getLastUsedRegister(), address));
    };
}
