package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.Data;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Selection of a field
 *
 * @author oscarmaggiori
 * @version $Id: $Id
 */
public class Selection extends AbstractLValue{

    private AbstractExpr obj;
    private AbstractIdentifier field;

    /**
     * <p>Constructor for Selection.</p>
     *
     * @param obj a {@link fr.ensimag.deca.tree.AbstractExpr} object
     * @param field a {@link fr.ensimag.deca.tree.AbstractIdentifier} object
     */
    public Selection(AbstractExpr obj, AbstractIdentifier field){
        this.obj = obj;
        this.field = field;
    }

    /** {@inheritDoc} */
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {

        Type type = this.obj.verifyExpr(compiler, localEnv, currentClass);
        if (!type.isClass()) {
            throw new ContextualError(type.getName().getName()+" isn't a class", getLocation());
        }
        ClassType classType = (ClassType) type;
        EnvironmentExp env_exp2 = classType.getDefinition().getMembers();
        if(env_exp2.get(this.field.getName())==null){
            throw new ContextualError(this.field.getName().getName()+" isn't defined in this class", getLocation());
        }
        FieldDefinition fieldDef ;
        if(env_exp2.get(this.field.getName()).isField()){
            fieldDef = (FieldDefinition) env_exp2.get(this.field.getName());
        }
        else{
            throw new ContextualError(this.field.getName().getName()+" isn't a field", getLocation());
        }

        // condition : field_ident = { visibility : PROTECTED }

        if(fieldDef.getVisibility().name().equals("PROTECTED")){
            //on verifie les subTypes
            if (currentClass == null) {
                throw new ContextualError("can't access to protected field "+this.field.getName().getName(), getLocation());
            }
            ClassType currentType = currentClass.getType();
            if (classType.isSubClassOf(currentType) || currentType.isSubClassOf(classType)){
                setType(fieldDef.getType());
                    this.field.setDefinition(fieldDef);
                    this.obj.setType(fieldDef.getType());
                    return fieldDef.getType();
            }
            else {
                throw new ContextualError("can't access to protected field "+this.field.getName().getName(), getLocation());
            }
        }
        
        setType(fieldDef.getType());
        this.field.setDefinition(fieldDef);
        this.obj.setType(fieldDef.getType());
        return fieldDef.getType();


    }

    /** {@inheritDoc} */
    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print(".");
        field.decompile(s);        
    }

    /** {@inheritDoc} */
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        this.obj.prettyPrint(s, prefix, false);
        this.field.prettyPrint(s, prefix, false);
        
    }

    /** {@inheritDoc} */
    @Override
    protected void iterChildren(TreeFunction f) {
        this.obj.iter(f);
        this.field.iter(f);
        
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        Data data = compiler.getData();
        obj.codeGenSelect(compiler);
        // On met dans objRegister l'adresse de l'obj en partie gauche
        GPRegister objRegister = data.getLastUsedRegister();
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new CMP(new NullOperand(), objRegister));
            compiler.addInstruction(new BEQ(new Label("null_dereference")));
        }
        // On récupère l'offset du champ concerné
        compiler.addInstruction(new LOAD(new RegisterOffset(field.getFieldDefinition().getIndex(), objRegister), objRegister));
        
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenSelect(DecacCompiler compiler) {
        codeGenInst(compiler);
    }

    /** {@inheritDoc} */
    @Override
    protected void codeGenAssign(DecacCompiler compiler, Register register) {
        Data data = compiler.getData();
        obj.codeGenSelect(compiler);
        // On met dans objRegister l'adresse de l'obj en partie gauche
        GPRegister objRegister = data.getLastUsedRegister();
        if (!(compiler.getCompilerOptions().getNoCheck())) {
            compiler.addInstruction(new CMP(new NullOperand(), objRegister));
            compiler.addInstruction(new BEQ(new Label("null_dereference")));
        }
        compiler.addInstruction(new STORE(register, new RegisterOffset(field.getFieldDefinition().getIndex(), compiler.getData().getLastUsedRegister())));
    }
    
}
