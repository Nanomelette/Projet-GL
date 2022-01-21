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
        classe.decompile(s);
        s.print(" extends ");
        classeSup.decompile(s);
        s.print("{");
        listDeclField.decompile(s);
        listDeclMethod.decompile(s);
        s.print("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        Symbol parent = this.classeSup.getName();
        Validate.isTrue(compiler.searchSymbol(parent).isClass(), "not class extension");
        ClassType classType = (ClassType) compiler.searchSymbol(parent);
        if (classType == null) {
            throw new ContextualError("undefined super class", this.getLocation());
        }
        ClassDefinition classSupDef = classType.getDefinition();
        classeSup.setDefinition(classSupDef);
        ClassType type = new ClassType(this.classe.getName(), this.classe.getLocation(), classSupDef);
        this.classe.setDefinition(type.getDefinition());
        this.classe.setType(type);
        compiler.GetEnvTypes().setEnvironmentType(this.classe.getName(), type, this.classe.getLocation());
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        this.listDeclField.verifyListField(compiler, this.classeSup, this.classe);
        this.listDeclMethod.verifyListMethod(compiler, this.classeSup, this.classe);
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        this.listDeclMethod.verifyListMethodBody(compiler, classeSup, classe);
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
        classe.iter(f);
        classeSup.iter(f);
        listDeclField.iter(f);
        listDeclMethod.iter(f);
    }

    @Override
    protected void addToVTable(DecacCompiler compiler) {
        // compiler.addInstruction(new LEA(classeSup., op2));
    }

}
