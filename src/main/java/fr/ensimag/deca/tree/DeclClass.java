package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;

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

        // Ajout des méthodes de la classe
        classe.getClassDefinition().initLabelVTable(
                classeSup.getClassDefinition().getLabelVTable());
        for (AbstractDeclMethod aDeclMethod : listDeclMethod.getList()) {
            Label codeLabel = new Label(
                    "code." + classe.getName().getName() + "." + aDeclMethod.getName().getName().getName());
            classe.getClassDefinition().addLabel(
                    aDeclMethod.getName().getMethodDefinition().getIndex(), codeLabel);
            compiler.addInstruction(new LOAD(new LabelOperand(codeLabel), Register.R0));
            compiler.addInstruction(
                    new STORE(Register.R0, new RegisterOffset(compiler.getData().getGbOffset(), Register.GB)));
            compiler.getData().incrementGbOffset();
        }
    }

    private void codeGenInitClass(DecacCompiler compiler) {
        compiler.addLabel(
                new Label("code.Object.equals"));
        compiler.addInstruction(
                new LOAD(
                        new RegisterOffset(-2, Register.LB),
                        Register.R0));
        compiler.addInstruction(
                new LOAD(
                        new RegisterOffset(-3, Register.LB),
                        Register.R1));
        compiler.addInstruction(
                new CMP(Register.R0, Register.R1));

        compiler.addInstruction(
                new SEQ(Register.R0));
        compiler.getData().setLastUsedRegister(Register.R0);
        compiler.addInstruction(
                new RTS());

    }

    @Override
    protected void codeGenDeclClass(DecacCompiler compiler) {
        // Codage de l'initialisation des champs
        if (listDeclField.size() != 0) {
            compiler.addComment("Initialisation des champs de " + classe.getName().getName());
        }

        // Début de bloc
        compiler.newBloc();
        compiler.setToBlocProgram();

        // compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB),
        // Register.R1));
        if (!classeSup.getName().getName().equals("Object")) {
            // Initialiser les nouveaux champs à zero
            listDeclField.codeGenListDeclFieldSetZero(compiler);
            // Init les champs parents
            compiler.addComment("Appel de l'initialisation des champs hérités de " + classeSup.getName().getName());
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
        if (compiler.getData().getNumberOfUsedRegister() != 0) {
            compiler.addComment("Restauration des registres");
        }
        compiler.getData().popUsedRegisters(compiler);

        // Sauvegarde des registres
        compiler.getData().pushUsedRegisters(compiler);
        if (compiler.getData().getNumberOfUsedRegister() != 0) {
            compiler.addCommentAtFirst("Sauvegarde des registres");
        }

        // Fin de bloc
        /**
         * TODO : TSTO
         * 
         * nombre de registres sauvegardés en début de bloc =
         * 0
         * nombre de variables du bloc =
         * 0 <- Initialisation des champs donc pas de variables
         * nombre maximal de temporaires nécessaires à l’évaluation des expressions =
         * compiler.getData().getFreeStoragePointer() - 2
         * nombre maximal de paramètres des méthodes appelées (chaque instruction BSR
         * effectuant deux empilements) =
         * 2 <- Il faut retenir le PC et l'objet
         * 
         */
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
        // Ajout de l'etiquette de code.Object.equals
        codeGenInitClass(compiler);
        listDeclMethod.codeGenListDeclMethod(compiler);

    }
}
