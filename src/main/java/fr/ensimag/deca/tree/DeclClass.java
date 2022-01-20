package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.instructions.LEA;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl20
 * @date 01/01/2022
 */
public class DeclClass extends AbstractDeclClass {

    private AbstractIdentifier classe;
    private AbstractIdentifier classeSup;
    private ListDeclField listDeclField;
    private ListDeclMethod listDeclMethod;

    public DeclClass(AbstractIdentifier tree, AbstractIdentifier tree2, ListDeclField listField,
        ListDeclMethod listDeclMethod) {
        Validate.notNull(tree);
        Validate.notNull(tree2);
        Validate.notNull(listField);
        Validate.notNull(listDeclMethod);
        this.classe = tree;
        this.classeSup = tree2;
        this.listDeclField = listField;
        this.listDeclMethod = listDeclMethod;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        s.print(classe.decompile());
        s.print(" extends ");
        s.print(classeSup.decompile());
        s.print("{");
        s.print(listDeclField.decompile());
        s.print(listDeclMethod.decompile());
        s.print("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        Symbol parent = this.classeSup.getName();
        Validate.isTrue(compiler.searchSymbol(parent).isClass(), "not class extension");
        ClassType classType = (ClassType) compiler.searchSymbol(parent);
        if (classType == null) {
            throw new ContextualError("undefined super class", getLocation());
        }
        ClassDefinition classDefinition = classType.getDefinition();
        ClassType type = new ClassType(this.classe.getName(), getLocation(), classDefinition);
        compiler.GetEnvTypes().setEnvironmentType(this.classe.getName(), type, getLocation());
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        this.verifyClassMembers(compiler);
        this.listDeclField.verifyListField(compiler, this.classeSup, this.classe);
        this.listDeclMethod.verifyListMethod(compiler, this.classeSup);
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.classe.prettyPrint(s, prefix, false);
        if (this.classeSup != null) {
            this.classeSup.prettyPrint(s, prefix, false);
        }
        this.listDeclField.prettyPrint(s, prefix, false);
        this.listDeclMethod.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void addToVTable(DecacCompiler compiler) {
        // compiler.addInstruction(new LEA(classeSup., op2));
    }

}
