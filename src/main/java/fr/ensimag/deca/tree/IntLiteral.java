package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 * Integer literal
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class IntLiteral extends AbstractExpr {
    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a int
     */
    public int getValue() {
        return value;
    }

    private int value;

    /**
     * <p>Constructor for IntLiteral.</p>
     *
     * @param value a int
     */
    public IntLiteral(int value) {
        this.value = value;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = new IntType(compiler.getSymbolTable().create("int"));
        this.setType(type);
        return type;
    }


    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
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
        Data data = compiler.getData();
        GPRegister freeRegister = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(value, freeRegister));
        data.setLastUsedRegister(freeRegister);
    }

    /** {@inheritDoc} */
    @Override
    protected DVal getDVal() {
        return new ImmediateInteger(value);
    }

}
