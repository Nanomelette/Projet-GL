package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Deca Identifier
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Identifier extends AbstractIdentifier {

    /** {@inheritDoc} */
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    /** {@inheritDoc} */
    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * {@inheritDoc}
     *
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ExpDefinition.
     *
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    /**
     * <p>getParamDefinition.</p>
     *
     * @return a {@link fr.ensimag.deca.context.ParamDefinition} object
     */
    public ParamDefinition getParamDefinition() {
        try {
            return (ParamDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a parameter identifier, you can't call getParamDefinition on it");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    /** {@inheritDoc} */
    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    /**
     * <p>Constructor for Identifier.</p>
     *
     * @param name a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {

        Symbol symb = (Symbol) compiler.getSymbolTable().create(this.name.getName());
        if (localEnv.get(symb) != null) {
            setDefinition(localEnv.get(symb));
            setType(localEnv.get(symb).getType());
            return localEnv.get(symb).getType();
        } else {
            throw new ContextualError(this.getName() + ": identifier not defined", getLocation());
        }
    }

    /**
     * {@inheritDoc}
     *
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        Type type = compiler.searchSymbol(this.name);
        if (type == null)
            throw new ContextualError("Identifier-type error", this.getLocation());
        else {
            this.setDefinition(compiler.GetEnvTypes().get(this.getName()));
            setType(type);
        }
        return type;
    }

    private Definition definition;

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
    public void decompile(IndentPrintStream s) {
        s.print(name.getName());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected DVal getDVal() {
        return this.getExpDefinition().getOperand();
    }

    /** {@inheritDoc} */
    @Override
    protected void codeBoolean(boolean b, Label E, DecacCompiler compiler) {
        Data data = compiler.getData();
        GPRegister register = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), register));
        compiler.addInstruction(new CMP(0, register));
        compiler.addInstruction(new BEQ(E));
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();
        GPRegister register = data.getFreeRegister(compiler);
        if (getDefinition().isField()) {
            compiler.addInstruction(
                new LOAD(new RegisterOffset(-2, Register.LB), register));
            compiler.addInstruction(
                new LOAD(new RegisterOffset(getFieldDefinition().getIndex(), register), register));
        } else if (getDefinition().isParam()) {
            int offset = getParamDefinition().getIndex() + 2;
            compiler.addInstruction(new LOAD(
                new RegisterOffset(-offset, Register.LB), register));
        } else {
            compiler.addInstruction(new LOAD(getExpDefinition().getOperand(), register));
        }
        data.setLastUsedRegister(register);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        Data data = compiler.getData();
        GPRegister register = data.getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(getVariableDefinition().getOperand(), register));
        data.setLastUsedRegister(register);
    }

    /**
     * {@inheritDoc}
     *
     * Methode used to generate code that assign the result
     * saved in lastUsedRegister to this identifer
     */
    @Override
    protected void codeGenAssign(DecacCompiler compiler, Register register) {
        if (getDefinition().isField()) {
            GPRegister tmpRegister = compiler.getData().getFreeRegister(compiler);
            compiler.addInstruction(
                new LOAD(new RegisterOffset(-2, Register.LB), tmpRegister));
            compiler.addInstruction(new STORE(register, new RegisterOffset(getFieldDefinition().getIndex(), tmpRegister)));
        } else if (getDefinition().isParam()) {
            int offset = getParamDefinition().getIndex() + 2;
            compiler.addInstruction(new STORE(register, new RegisterOffset(-offset, Register.LB)));
        } else {
            DAddr adress = getExpDefinition().getOperand();
            compiler.addInstruction(new STORE(register, adress));
        }
        
    }
}
