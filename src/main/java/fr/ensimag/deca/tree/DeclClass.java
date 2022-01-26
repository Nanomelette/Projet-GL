package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class DeclClass extends AbstractDeclClass {

    private AbstractIdentifier classe;
    private AbstractIdentifier classeSup;
    private ListDeclField listDeclField;
    private ListDeclMethod listDeclMethod;

    /**
     * <p>Constructor for DeclClass.</p>
     *
     * @param tree a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param tree2 a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     * @param listField a {@link fr.ensimag.deca.tree.ListDeclField} object
     * @param listDeclMethod a {@link fr.ensimag.deca.tree.ListDeclMethod} object
     */
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

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        classe.decompile(s);
        if (!classeSup.getName().getName().equals("Object")) {
            s.print(" extends " + classeSup.getName().getName());
        }
        s.println(" {");
        s.indent();
        listDeclField.decompile(s);
        listDeclMethod.decompile(s);
        s.unindent();
        s.print("}");
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        Symbol parent = this.classeSup.getName();
        if (compiler.searchSymbol(parent) == null) {
            throw new ContextualError("undefined super class", this.getLocation());
        }
        if (!compiler.searchSymbol(parent).isClass()) {
            throw new ContextualError("not class extension", this.getLocation());
        }
        ClassType classType = (ClassType) compiler.searchSymbol(parent);
        ClassDefinition classSupDef = classType.getDefinition();
        classeSup.setDefinition(classSupDef);
        ClassType type = new ClassType(this.classe.getName(), this.classe.getLocation(), classSupDef);
        this.classe.setDefinition(type.getDefinition());
        this.classe.setType(type);
        compiler.GetEnvTypes().setEnvironmentType(this.classe.getName(), type, this.classe.getLocation());
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        Symbol symb = this.classe.getName();
        ClassType classType = (ClassType) compiler.GetEnvTypes().getType(symb);
        ClassDefinition classDef = classType.getDefinition();
        EnvironmentExp envExp = classDef.getMembers();
        Symbol symbSup = this.classeSup.getName();
        ClassType classTypeSup = (ClassType) compiler.GetEnvTypes().getType(symbSup);
        ClassDefinition classDefSup = classTypeSup.getDefinition();
        EnvironmentExp envExpSup = classDefSup.getMembers();
        int indexMethods = classDefSup.getIndexMethods();
        classDef.setIndexMethods(indexMethods);
        this.listDeclField.verifyListField(compiler, this.classeSup, this.classe);
        this.listDeclMethod.verifyListMethod(compiler, this.classeSup, this.classe);
        for (Symbol s : envExpSup.getDictionnary().keySet()) {
            try {
                envExp.declare(s, envExpSup.get(s));
            } catch (EnvironmentExp.DoubleDefException e) {}
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        this.listDeclField.verifyListFieldBody(compiler, classeSup, classe);
        this.listDeclMethod.verifyListMethodBody(compiler, classeSup, classe);
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.classe.prettyPrint(s, prefix, false);
        if (this.classeSup != null) {
            this.classeSup.prettyPrint(s, prefix, false);
        }
        this.listDeclField.prettyPrint(s, prefix, false);
        this.listDeclMethod.prettyPrint(s, prefix, true);
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        classe.iter(f);
        classeSup.iter(f);
        listDeclField.iter(f);
        listDeclMethod.iter(f);
    }

    /** {@inheritDoc} */
    @Override
    protected void addToVTable(DecacCompiler compiler) {
        DAddr addressClassSup = classeSup.getClassDefinition().getAddressVTable();
        DAddr adressClass = new RegisterOffset(compiler.getData().getGbOffset(), Register.GB);

        // Initializationd de la VTable
        classe.getClassDefinition().setAddressVTable(adressClass);
        classe.getClassDefinition().initLabelVTable(
                classeSup.getClassDefinition().getLabelVTable());

        compiler.addComment("Code de la table des méthode de " + classe.getName().getName());
        // On indique la classe de la méthode parente
        compiler.addInstruction(new LEA(addressClassSup, Register.R0));
        compiler.addInstruction(new STORE(Register.R0, adressClass));
        compiler.getData().incrementGbOffset();

        // Methode Equals
        LabelOperand equalsOperand = new LabelOperand(new Label("code.Object.equals"));
        compiler.addInstruction(new LOAD(equalsOperand, Register.R0), "Héritage de la méthode equals de Object");
        compiler.addInstruction(new STORE(Register.R0,
                new RegisterOffset(
                        compiler.getData().getGbOffset(), Register.GB)));
        compiler.getData().incrementGbOffset();

        // On initialise d'abord la VTable de la classe
        classe.getClassDefinition().initLabelVTable(
                classeSup.getClassDefinition().getLabelVTable());
        for (AbstractDeclMethod aDeclMethod : listDeclMethod.getList()) {
            Label codeLabel = new Label(
                    "code." + classe.getName().getName() + "." + aDeclMethod.getName().getName().getName());
            classe.getClassDefinition().addLabel(
                    aDeclMethod.getName().getMethodDefinition().getIndex(), codeLabel);
        }
        

        // Ajout des méthodes de la classe
        for (Label codeLabel : classe.getClassDefinition().getLabelVTable().values()) {
            compiler.addInstruction(new LOAD(new LabelOperand(codeLabel), Register.R0));
            compiler.addInstruction(
                    new STORE(Register.R0, new RegisterOffset(compiler.getData().getGbOffset(), Register.GB)));
            compiler.getData().incrementGbOffset();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenDeclClass(DecacCompiler compiler) {
        // Codage de l'initialisation des champs
        if (listDeclField.size() != 0) {
            compiler.addComment("Initialisation des champs de " + classe.getName().getName());
        }

        // Début de bloc
        compiler.newBloc();
        compiler.setToBlocProgram();

        // Register.R1));
        if (!classeSup.getName().getName().equals("Object")) {
            // Initialiser les nouveaux champs à zero
            listDeclField.codeGenListDeclFieldSetZero(compiler);
            // Init les champs parents
            compiler.addComment("Appel de l'initialisation des champs hérités de " + classeSup.getName().getName());
            compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
            compiler.addInstruction(new PUSH(Register.R1), "Empile l'objet à initialiser");
            compiler.addInstruction(new BSR(new Label("init." + classeSup.getName().getName())),
                    "Appel de l’initialisation de la super-classe");
            compiler.addInstruction(new SUBSP(1), "On remet la pile dans son état initial");
        }
        if (listDeclField.size() != 0) {
            compiler.addComment("Initialisation explicite des champs de " + classe.getName().getName());
        }
        listDeclField.codeGenListDeclFieldSet(compiler);
        compiler.addInstruction(new RTS());

        // Restauration des registres
        if (compiler.getData().getNumberOfUsedRegister() > 0) {
            compiler.addComment("Restauration des registres");
        }
        compiler.getData().popUsedRegisters(compiler);

        // Sauvegarde des registres
        compiler.getData().pushUsedRegisters(compiler);
        if (compiler.getData().getNumberOfUsedRegister() > 0) {
            compiler.addCommentAtFirst("Sauvegarde des registres");
        }
        //Fin de bloc
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstructionAtFirst(new BOV(new Label("stack_overflow_error")));
            // On laisse ce +2 -2 pour l'instant pour suivre le calcul
            compiler.addInstructionAtFirst(new TSTO(2 + compiler.getData().getFreeStoragePointer() - 2));
        }
        compiler.addLabelAtFirst(new Label("init." + classe.getName().getName()));
        compiler.appendBlocInstructions();
        compiler.getData().restoreData();
        compiler.setToMainProgram();

        // Codage des méthodes
        listDeclMethod.codeGenListDeclMethod(compiler);

    }
}
