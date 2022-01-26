package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Single precision, floating-point literal
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class FloatLiteral extends AbstractExpr {

    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a float
     */
    public float getValue() {
        return value;
    }

    private float value;

    /**
     * <p>Constructor for FloatLiteral.</p>
     *
     * @param value a float
     */
    public FloatLiteral(float value) {
        Validate.isTrue(!Float.isInfinite(value),
                "literal values cannot be infinite");
        Validate.isTrue(!Float.isNaN(value),
                "literal values cannot be NaN");
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
            Type type = new FloatType(compiler.getSymbolTable().create("float"));
            this.setType(type);
            return type;    
    }


    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(java.lang.Float.toHexString(value));
    }

    @Override
    String prettyPrintNode() {
        return "Float (" + getValue() + ")";
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister lastRegister = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(getDVal(),lastRegister));
        compiler.getData().setLastUsedRegister(lastRegister);
    }

    /** {@inheritDoc} */
    @Override
    protected DVal getDVal() {
        return new ImmediateFloat(value);
    }
}
