package fr.ensimag.deca.codegen;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.tree.AbstractDeclVar;
import fr.ensimag.deca.tree.DeclVar;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.ListDeclVar;


public class Data {
    private static final Logger LOG = Logger.getLogger(Data.class);

    private int maxRegister = 15;
    // When storagePointer < maxRegister, the storage is in a register, 
    // otherwise it is in the stack.
    private int freeStoragePointer = 2;
    private int gBOffset = 1;
    private int maxStackLength = 0;
    // private Deque<GPRegister> registersStack = new LinkedList<GPRegister>();
    private GPRegister lastUsedRegister = GPRegister.getR(2);

    // Labels :
    private Label pile_pleine = new Label("pile_pleine");
    private Label io_error = new Label("io_error");

    public Data() {};

    public void setMaxRegister(int maxRegister) {
        this.maxRegister = maxRegister;
    }
    
    public boolean hasFreeRegister() {
        return (freeStoragePointer < maxRegister);
    }

    public GPRegister getMaxRegister() {
        return GPRegister.getR(maxRegister - 1);
    }

    public GPRegister getFreeRegister(DecacCompiler compiler) {
        if (hasFreeRegister()) {
            return GPRegister.getR(freeStoragePointer++);
        } else {
            GPRegister lastRegister = GPRegister.getR(maxRegister - 1);
            // registersStack.addFirst(lastRegister);
            // compiler.addInstruction(new PUSH(lastRegister), "sauvegarde");
            // freeStoragePointer++;
            return lastRegister;
        }
    }

    public int nbFreeRegsiter() {
        if (hasFreeRegister()) {
            return maxRegister - freeStoragePointer - 1;
        } else {
            return 0;
        }
    }

    public void decrementFreeStoragePointer() {
        freeStoragePointer--;
    }

    public void incrementFreeStoragePointer() {
        freeStoragePointer++;
        if (freeStoragePointer > maxStackLength) {
            maxStackLength = freeStoragePointer;
        }
    }

    public void restoreData() {
        freeStoragePointer = 2;
    }

    public void setLastUsedRegister(GPRegister lastUsedRegister) {
        this.lastUsedRegister = lastUsedRegister;
    }

    public GPRegister getLastUsedRegister() {
        return lastUsedRegister;
    }

    public void variableInit(ListDeclVar declVariables) {
        for (AbstractDeclVar absDeclVar : declVariables.getList()) {
            DAddr address = new RegisterOffset(gBOffset, Register.GB);
            Identifier var = (Identifier)((DeclVar)absDeclVar).getVarName();
            var.getExpDefinition().setOperand(address);
            gBOffset++;
        }
    }

    public void addHeader(DecacCompiler compiler) {
        compiler.addInstructionAtFirst(new ADDSP(gBOffset-1));
        // TODO : Gérer message d'erreur
        compiler.addInstructionAtFirst(new BOV(pile_pleine));
        compiler.addInstructionAtFirst(new TSTO(gBOffset+maxStackLength-1));
        compiler.addInstructionAtFirst(null, "start main program");
    }

    public void addBottom(DecacCompiler compiler) {
        compiler.addLabel(pile_pleine);
        compiler.addInstruction(new WSTR("Error: full stack."));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
        compiler.addLabel(io_error);
        compiler.addInstruction(new WSTR("Error: io_error."));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
        
    }

    public int getFreeStoragePointer() {
        return freeStoragePointer;
    }
}
