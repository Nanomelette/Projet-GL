package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl20
 * @date 01/01/2022
 */
public class DeclVar extends AbstractDeclVar {

    private static final Logger LOG = Logger.getLogger(Main.class);

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public AbstractIdentifier getVarName() {
        return varName;
    }

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

                Type varType = type.verifyType(compiler);
                if (varType.isVoid()){
                    throw new ContextualError("type void", getLocation());
                }       
                try {
                    ExpDefinition varDefinition= new VariableDefinition(varType, varName.getLocation());
                    varName.setDefinition(varDefinition);
                    varName.setType(varType);
                    compiler.GetEnvExp().declare(varName.getName(),varDefinition);
                    initialization.verifyInitialization(compiler, varName.getType(), localEnv , currentClass);
                } catch (EnvironmentExp.DoubleDefException e) {
                    // TODO Auto-generated catch block
                    String message = "L'identificateur ne peut etre defini plus qu'une fois";
                    throw new ContextualError(message, getLocation());
                }


    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.println(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    public void codeGenDeclVar(DecacCompiler compiler) {
        DAddr address = new RegisterOffset(compiler.getData().getGbOffset(), Register.GB);
        Identifier var = (Identifier) getVarName();
        var.getExpDefinition().setOperand(address);
        compiler.getData().incrementGbOffset();
        if (initialization instanceof Initialization) {
            Initialization init = (Initialization) initialization;
            init.getExpression().codeGenInst(compiler);
            compiler.addInstruction(new STORE(compiler.getData().getLastUsedRegister(), address));
        }
    }
}
