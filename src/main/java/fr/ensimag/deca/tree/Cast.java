package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

public class Cast extends AbstractExpr {
    private AbstractIdentifier type;
    private AbstractExpr e;

    public Cast (AbstractIdentifier type, AbstractExpr e){
        this.type = type;
        this.e = e;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        if(type.getName().getName().equals("Null")){
            throw new ContextualError("cannot cast null type", getLocation());
        }
        if(type.getName().getName().equals("void")){
            throw new ContextualError("cannot cast void type", getLocation());
        }
        Type type = this.type.verifyType(compiler);
        Type expressionType = this.e.verifyExpr(compiler, localEnv, currentClass);
        if(type.isVoid()){
            throw new ContextualError("cannot cast void type", getLocation());
        }
        if(type.isNull()){
            throw new ContextualError("cannot cast null type", getLocation());
        }
        if(type == expressionType){
            return type;
        }
        if(compiler.assignCompatible(compiler, type, expressionType)!= null){
            return compiler.assignCompatible(compiler, type, expressionType);
        }
        if(compiler.assignCompatible(compiler, expressionType, type)!= null){
            return compiler.assignCompatible(compiler, expressionType, type);
        }
        throw new ContextualError("impossible cast", getLocation());
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        type.decompile(s);
        s.print(") (");
        e.decompile(s);
        s.print(")");        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        e.prettyPrint(s, prefix, false);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        e.iter(f);
    }

    private void isInstanceOf(DecacCompiler compiler, Label doCast) {
        ClassType cType = (ClassType)e.getType();
        ClassDefinition typeCible = cType.getDefinition();
        ClassType bla = (ClassType)type.getType();
        ClassDefinition alzn = bla.getDefinition();
        GPRegister reg = compiler.getData().getFreeRegister(compiler);
        compiler.addInstruction(new LOAD(alzn.getAddressVTable(), reg));
        compiler.addInstruction(new CMP(typeCible.getAddressVTable(), reg));
        compiler.addInstruction(new BEQ(doCast));
        while (typeCible.getSuperClass() != null) {
            typeCible = typeCible.getSuperClass();
            compiler.addInstruction(new CMP(typeCible.getAddressVTable(), reg));
            compiler.addInstruction(new BEQ(doCast));
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        e.codeGenInst(compiler);
        GPRegister lastUsed = compiler.getData().getLastUsedRegister();
        if ((type.getType().isInt()) && (e.getType().isFloat())) {
            compiler.addInstruction(new INT(lastUsed, lastUsed));
        } else if ((type.getType().isFloat()) && (e.getType().isInt())) {
            compiler.addInstruction(new FLOAT(lastUsed, lastUsed));
        } else if (type.getType().isClass()) {
            if (e.getType().isNull()) {
                // TODO : a est la valeur null
            } else {
                Label doCast = new Label("do_cast."+compiler.getNLabel());
                compiler.incrNLabel();
                // Tester en assembleur si e est une instance de type
                isInstanceOf(compiler, doCast);
                // On cast
                if (!(compiler.getCompilerOptions().getNoCheck())) {
                    compiler.addInstruction(new BRA(new Label("cast_error")));
                }
                compiler.addLabel(doCast);
            }
        } else {
            if (!(compiler.getCompilerOptions().getNoCheck())) { 
                compiler.addInstruction(new BRA(new Label("cast_error")));
            }
        }
    }
}
