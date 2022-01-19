package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

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

    protected void verifyMethod(DecacCompiler compiler, AbstractIdentifier classeSup)
            throws ContextualError{
                throw new UnsupportedOperationException("not yet implemented");
        }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(type.decompile());
        s.print(" ");
        s.print(name.decompile());
        s.print("(");
        s.print(listDeclParam.decompile());
        s.print(")");
        s.print(methodBody.decompile());
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
        // TODO Auto-generated method stub
        
    }
    @Override
    public AbstractIdentifier getName() {
        return name;
    }

    @Override
    protected void addToVTable(DecacCompiler compiler, String debName) {
        Label labelName = new Label(debName + name.getName().getName());
        compiler.addInstruction(
            new LOAD(new LabelOperand(labelName), Register.R0)
            );
        compiler.addInstruction(new STORE(Register.R0, 
                                    new RegisterOffset(
                                        compiler.getData().getGbOffset(), Register.GB)
                                    )
                                );
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof DeclMethod) {
            DeclMethod other = (DeclMethod)obj;
            return other.getName().getName().getName().equals(this.getName().getName().getName());
        }
        return false;
    }
    
}
