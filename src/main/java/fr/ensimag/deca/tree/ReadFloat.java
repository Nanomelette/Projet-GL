package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RFLOAT;

import java.io.PrintStream;

/**
 * Operator "readFloat()"
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ReadFloat extends AbstractReadExpr {

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
        s.print("readFloat()");
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
        compiler.addInstruction(new RFLOAT());
        compiler.addInstruction(new BOV(new Label("io_error")));
        GPRegister lasRegister = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(GPRegister.R1, lasRegister));
        data.setLastUsedRegister(lasRegister);
    }

}
