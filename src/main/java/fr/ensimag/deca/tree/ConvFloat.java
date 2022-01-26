package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ConvFloat extends AbstractUnaryExpr {
    /**
     * <p>Constructor for ConvFloat.</p>
     *
     * @param operand a {@link fr.ensimag.deca.tree.AbstractExpr} object
     */
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        Type type = new FloatType(compiler.getSymbolTable().create("float"));
        this.setType(type);
        return type;
    }


    /** {@inheritDoc} */
    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new FLOAT(op, op));
        compiler.getData().setLastUsedRegister(op);
    }

}
