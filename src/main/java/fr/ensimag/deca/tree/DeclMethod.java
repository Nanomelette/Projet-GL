package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.Label;

import java.io.PrintStream;

public class DeclMethod extends AbstractDeclMethod{

    private AbstractIdentifier type;
    private AbstractIdentifier name;
    private ListDeclParam listDeclParam;
    private AbstractMethodBody methodBody;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, ListDeclParam listDeclParam, AbstractMethodBody methodBody){
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(listDeclParam);
        this.type = type;
        this.name = name;
        this.listDeclParam = listDeclParam;
        this.methodBody = methodBody;
    }

    @Override
    protected void verifyMethod(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError{
        
        Symbol method = this.name.getName();
        Type mType = this.type.verifyType(compiler);
        EnvironmentType env_Types = compiler.GetEnvTypes();
        Symbol symbSup = classeSup.getName();
        ClassType classTypeSup = (ClassType) env_Types.getType(symbSup);
        Signature sig2 = this.listDeclParam.verifyListParam(compiler);
        if (classTypeSup != null) {
            ClassDefinition classDefSup = classTypeSup.getDefinition();
            EnvironmentExp envExpSup = classDefSup.getMembers();
            ExpDefinition ExpDef = envExpSup.get(method);
            if (ExpDef != null) {
                Validate.isTrue(ExpDef.isMethod(), method.getName() + " isn't a method");
                MethodDefinition methodDef = (MethodDefinition) ExpDef;
                Type typeSup = methodDef.getType();
                if (compiler.subType(compiler, mType, typeSup)) {
                    Signature sig = methodDef.getSignature();
                    if (!sig.sameSignature(sig2)) {
                        throw new ContextualError(method.getName()+" must have same signature", this.getLocation());
                    }
                } else {
                    throw new ContextualError(method.getName()+" must have same type", this.getLocation());
                }
            }
            try {
                Symbol symb = classe.getName();
                ClassType classType = (ClassType) env_Types.getType(symb);
                ClassDefinition classDef = classType.getDefinition();
                EnvironmentExp envExp = classDef.getMembers();
                int index = classDefSup.getNumberOfFields() + classDef.getNumberOfFields() + 1;
                MethodDefinition newDef = new MethodDefinition(mType, name.getLocation(), sig2, index);
                name.setDefinition(newDef);
                name.setType(mType);
                envExp.declare(method, newDef);
                classDef.incNumberOfMethods();
                Label label = new Label(symb.getName() +"."+ method.getName());
                newDef.setLabel(label);
            } catch (EnvironmentExp.DoubleDefException e) {
                String message = "can't defined method identifier several times in a class";
                throw new ContextualError(message, name.getLocation());
            }
        } else {
            throw new ContextualError(symbSup.getName()+" can't have null type", classeSup.getLocation());
        }
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, AbstractIdentifier classeSup, AbstractIdentifier classe)
            throws ContextualError{
                ClassDefinition currentClass = classe.getClassDefinition();
                EnvironmentExp EnvExpClass = currentClass.getMembers();
                MethodDefinition nameDef = name.getMethodDefinition();
                EnvironmentExp localEnvInit = nameDef.getMembers();
                this.listDeclParam.verifyListDeclParam(compiler, localEnvInit);
                EnvironmentExp localEnv = localEnvInit.empilement(EnvExpClass);
                this.methodBody.verifyMethodBody(compiler, localEnv, currentClass, this.type.getType());
            }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print("(");
        listDeclParam.decompile(s);
        s.print(")");
        methodBody.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.type.prettyPrint(s, prefix, false);
        this.name.prettyPrint(s, prefix, false);
        this.listDeclParam.prettyPrint(s, prefix, false);
        this.methodBody.prettyPrint(s, prefix, true);
        
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        listDeclParam.iter(f);
        
    }
    
}
