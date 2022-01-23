package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;

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
        if(type.sameType(expressionType)){
            this.setType(type);
            return type;
        }
        if(compiler.assignCompatible(compiler, type, expressionType)!= null){
            this.setType(type);
            return type;
        }
        if(compiler.assignCompatible(compiler, expressionType, type)!= null){
            this.setType(type);
            return type;
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
        // register contient l'adresse de l'objet à tester dans le tas
        GPRegister register = compiler.getData().getLastUsedRegister();
        Type verType = type.getType();
        Validate.isTrue(verType.isClass(), type.getName()+" isn't a class");
        ClassType classType = (ClassType) verType;
        ClassDefinition typeCible = classType.getDefinition();
        compiler.addInstruction(new CMP(typeCible.getAddressVTable(), register));
        compiler.addInstruction(new BEQ(doCast));
        while (typeCible.getSuperClass() != null) {
            typeCible = typeCible.getSuperClass();
            compiler.addInstruction(new CMP(typeCible.getAddressVTable(), register));
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
                compiler.addLabel(doCast);
                // On cast
                if (!(compiler.getCompilerOptions().getNoCheck())) {
                    compiler.addInstruction(new BRA(new Label("cast_error")));
                }
            }
        } else {
            if (!(compiler.getCompilerOptions().getNoCheck())) { 
                compiler.addInstruction(new BRA(new Label("cast_error")));
            }
        }
    }
}
