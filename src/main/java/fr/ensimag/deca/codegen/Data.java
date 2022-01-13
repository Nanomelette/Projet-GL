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
import fr.ensimag.ima.pseudocode.instructions.PUSH;
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
    // private Deque<GPRegister> registersStack = new LinkedList<GPRegister>();
    private GPRegister lastUsedRegister = GPRegister.getR(2);

    // Labels :
    private Label pile_pleine = new Label("pile_pleine");

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
            LOG.debug("On va PUSH  : " + lastRegister);
            // compiler.addInstruction(new PUSH(lastRegister), "sauvegarde");
            // freeStoragePointer++;
            return lastRegister;
        }
    }

    // /**
    //  * Renvoie le dernier registre dans lequel nous avons écrit.
    //  * @return
    //  */
    // public GPRegister getLastRegister() {
    //     if (hasFreeRegister()) {
    //         return GPRegister.getR(freeStoragePointer - 1);
    //     } else {
    //         return GPRegister.getR(maxRegister - 1);
    //     }
    // }

    public void decrementFreeStoragePointer() {
        freeStoragePointer--;
    }

    public void incrementFreeStoragePointer() {
        freeStoragePointer++;
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
    
    // public void storeValue(GPRegister register) {
    //     registersStack.addFirst(register);
    //     storagePointer++;
    // }

    // public GPRegister downloadValue() {
    //     storagePointer--;
    //     return registersStack.removeFirst();
    // }

    // public void save(DecacCompiler compiler, DVal op1) {
    //     GPRegister op2;
    //     if (storagePointer < maxRegister) {
    //         op2 = GPRegister.getR(storagePointer);
    //         compiler.addInstruction(new LOAD(op1, op2));
    
    //     } else {
    //         op2 = GPRegister.getR(maxRegister-1);
    //         compiler.addInstruction(new PUSH(registersStack.peekFirst()));
    //         compiler.addInstruction(new LOAD(op1, op2));
    //     }
    //     storeValue(op2);
    // }

    // public GPRegister download(DecacCompiler compiler) {
    //     GPRegister op1 = downloadValue();
    //     if (storagePointer < maxRegister) {
    //         return op1;
    //     } else {
    //         compiler.addInstruction(new LOAD(op1, GPRegister.R1));
    //         compiler.addInstruction(new POP(op1));
    //         return GPRegister.R1;
    //     }
    // }

    public void addHeader(DecacCompiler compiler) {
        // Ecriture de l'header
        compiler.addInstructionAtFirst(new ADDSP(16));
        compiler.addInstructionAtFirst(new BOV(pile_pleine));
        // TODO : gérer l'argument de TSTO : surement un truc du genre 'debut de la pile + taille de la pile'
        compiler.addInstructionAtFirst(new TSTO(6));
        compiler.addInstructionAtFirst(null, "start main program");
    }

    public void addBottom(DecacCompiler compiler) {
        compiler.addLabel(pile_pleine);
        compiler.addInstruction(new WSTR("Error: full stack."));
        compiler.addInstruction(new WNL());
        compiler.addInstruction(new ERROR());
    }

    public int getFreeStoragePointer() {
        return freeStoragePointer;
    }
}
