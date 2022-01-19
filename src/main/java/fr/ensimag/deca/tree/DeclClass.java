package fr.ensimag.deca.tree;

import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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
    private ListDeclFieldSet listDeclField;
    private ListDeclMethod listDeclMethod;

    public DeclClass(AbstractIdentifier tree, AbstractIdentifier tree2, ListDeclFieldSet listField,
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
        DAddr addressClassSup = classeSup.getClassDefinition().getAddressVTable();
        DAddr adressClass = new RegisterOffset(compiler.getData().getGbOffset(), Register.GB);

        // Initializationd de la VTable
        classe.getClassDefinition().setAddressVTable(adressClass);
        classe.getClassDefinition().initLabelVTable(
            classeSup.getClassDefinition().getLabelVTable()
            );

        compiler.addComment("Code de la table des méthode de " + classe.getName().getName());
        // On indique la classe de la méthode parente
        compiler.addInstruction(new LEA(addressClassSup, Register.R0));
        compiler.addInstruction(new STORE(Register.R0, adressClass));
        compiler.getData().incrementGbOffset();
        
        // Methode Equals
        LabelOperand equalsOperand = new LabelOperand(compiler.getData().getLabels().equals);
        // TODO : faire un get qui combine
        compiler.getData().getLabels().addLabel(compiler.getData().getLabels().equals);
        compiler.addInstruction(new LOAD(equalsOperand, Register.R0), "Héritage de la méthode equals de Object");
        compiler.addInstruction(new STORE(Register.R0, 
                                    new RegisterOffset(
                                        compiler.getData().getGbOffset(), Register.GB)
                                    )
                                );
        compiler.getData().incrementGbOffset();

        // Ajout des méthodes de la classe
        classe.getClassDefinition().initLabelVTable(
            classeSup.getClassDefinition().getLabelVTable()
        );
        for (AbstractDeclMethod aDeclMethod : listDeclMethod.getList()) {
            classe.getClassDefinition().addLabel(
                aDeclMethod.getName().getMethodDefinition().getIndex(),
                new Label(
                    "code." + classe.getName().getName() + "." + aDeclMethod.getName().getName().getName()
                    )
                );
            compiler.getData().incrementGbOffset();
        }

        // On ajoute les méthodes de la classe mère, donc en faisant l'hypothèse que l'on réécrit moins de fonction
        // Iterator<Integer> it = classeSup.getClassDefinition().getKeys();
        // while (it.hasNext()) {
        //     int index = it.next();
        //     AbstractDeclMethod declmethod = classeSup.getClassDefinition().getLabel(index);
        //     Map<Integer, AbstractDeclMethod> classeVTable = classe.getClassDefinition().getLabelVTable();
        //     String debName;
        //     if (classeVTable.containsValue(declmethod)) {
        //         debName = "code." + classeSup.getName().getName();
        //         classe.getClassDefinition().addLabel(, index);
        //     } else {
        //         debName = "code." + classe.getName().getName();
        //         classe.getClassDefinition().addLabel(declmethod, index);
        //     }
        //     declmethod.addToVTable(compiler, debName);

        // }

        // Version fonctionnel avec HashMap<Integer, AbstractDeclMethod>
        // for (int key = 0; key < classeSup.getClassDefinition().sizeVTable(); key++) {
        //     AbstractDeclMethod declMethod = classeSup.getClassDefinition().getLabel(key);
        //     Map<Integer, AbstractDeclMethod> classeVTable = classe.getClassDefinition().getLabelVTable();
        //     String debName;
        //     if (classeVTable.containsValue(declMethod)) {
        //         debName = "code." + classeSup.getName().getName();
        //         classe.getClassDefinition().addLabel(declMethod, key);
        //     } else {
        //         debName = "code." + classeSup.getName().getName();
        //         classe.getClassDefinition().addLabel(declMethod, key);
        //     }
        //     declMethod.addToVTable(compiler, debName);
        // }

        


        

        // for (AbstractDeclMethod aDeclMethod : listDeclMethod.getList()) {
        //     // Table des étiquettes
        //     classe.getClassDefinition().addLabel(aDeclMethod);
        //     // Gen du code
        //     aDeclMethod.addToVTable(compiler, "code." + classe.getName().getName());
        //     if (!classeSup.getName().getName().equals("Object")) {
        //         int key = 2;
        //         while (classeSup.getClassDefinition().hasKey(key)) {
        //             if (!classe.getClassDefinition().hasKey(key)) {
        //                 classe.getClassDefinition().addLabel(
        //                     classeSup.getClassDefinition().getLabel(key)
        //                     );
        //                 }
        //                 key++;
        //             }
        //         }
        }
}
