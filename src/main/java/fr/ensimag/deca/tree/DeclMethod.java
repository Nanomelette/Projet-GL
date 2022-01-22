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
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
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
                int index = classDefSup.getNumberOfMethods() + classDef.getNumberOfMethods() + 1;
                MethodDefinition newDef = new MethodDefinition(mType, name.getLocation(), sig2, index);
                Label label = new Label(symb.getName() +"."+ method.getName());
                newDef.setLabel(label);
                name.setDefinition(newDef);
                name.setType(mType);
                envExp.declare(method, newDef);
                classDef.incNumberOfMethods();
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
    
    public void codeGenDeclMethod(DecacCompiler compiler) {
        // Début de bloc
        compiler.newBloc(); compiler.setToBlocProgram();
        Label labelReturn = new Label(
            "fin." + name.getMethodDefinition().getLabel().toString()
        );
        compiler.getData().setLabelReturn(labelReturn);

        // Calcul de methodBody
        /**
         * TODO : le nmbre maximal de paramètres des méthodes appelees
         */
        methodBody.codeGenMethodBody(compiler);

        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(
                new WSTR(
                    "Error: method " + labelReturn.toString() + " needs a return"
                )
            );
        }
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());

        // Restauration des registres
        compiler.addLabel(labelReturn);
        compiler.addComment("Restauration des registres");
        compiler.getData().popUsedRegisters(compiler);
        compiler.getData().setLastUsedRegister(Register.R0);
        compiler.addInstruction(new RTS());

        // Sauvegarde des registres
        compiler.getData().pushUsedRegisters(compiler);
        compiler.addCommentAtFirst("Sauvegarde des registres");

        // TSTO
        /**
         * TODO : TSTO
         * 
         * nombre de registres sauvegardés en début de bloc = 
         *          compiler.getData().getNumberOfUsedRegister()
         * nombre de variables du bloc =
         *          ?
         * nombre maximal de temporaires nécessaires à l’évaluation des expressions =
         *          compiler.getData().getMaxStackLength()
         * nombre maximal de paramètres des méthodes appelées (chaque instruction BSR effectuant deux empilements) =
         *          2 <- Il faut retenir le PC et l'objet
         * 
         */
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstructionAtFirst(new BOV(new Label("stack_overflow_error")));
            compiler.addInstructionAtFirst(
                new TSTO(
                    compiler.getData().getNumberOfUsedRegister() +
                    compiler.getData().getMaxStackLength() +
                    ((MethodBody)methodBody).getNbrVarMethodBody()
                )
            );
        }
        compiler.addLabelAtFirst(new Label("code."+name.getMethodDefinition().getLabel().toString()));

        // Fin de bloc 
        compiler.appendBlocInstructions();
        compiler.getData().restoreData();
        compiler.setToMainProgram();

    }   
}
