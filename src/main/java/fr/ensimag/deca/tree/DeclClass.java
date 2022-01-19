package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.LEA;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl20
 * @date 01/01/2022
 */
public class DeclClass extends AbstractDeclClass {

    private Identifier classe;
    private Identifier classeSup;
    private ListDeclField listDeclField;
    private ListDeclMethod listDeclMethod;

    public DeclClass(Identifier classe, Identifier classeSup, ListDeclField listDeclField,
        ListDeclMethod listDeclMethod) {
        Validate.notNull(classe);
        Validate.notNull(classeSup);
        Validate.notNull(listDeclField);
        Validate.notNull(listDeclMethod);
        this.classe = classe;
        this.classe = classeSup;
        this.listDeclField = listDeclField;
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
        this.verifyClass(compiler);
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
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void addToVTable(DecacCompiler compiler) {
        // compiler.addInstruction(new LEA(classeSup., op2));

        for (AbstractDeclMethod aDeclMethod : listDeclMethod.getList()) {
            aDeclMethod.addToVTable(compiler);
        }
    }

}
