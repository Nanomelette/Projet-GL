package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.tree.AbstractDeclVar;
import fr.ensimag.deca.tree.DeclVar;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.deca.tree.ListDeclVar;


public class Memory {
    private int maxRegister = 15;
    private int registerPointer = 2;

    private int gB = 1;
    private int lB;

    public Memory() {};

    public void variableInit(ListDeclVar declVariables) {
        for (AbstractDeclVar absDeclVar : declVariables.getList()) {
            DAddr address = new RegisterOffset(gB, Register.GB);
            Identifier var = (Identifier)((DeclVar)absDeclVar).getVarName();
            var.getExpDefinition().setOperand(address);
            gB++;
        } //TODO :  Et si il y a trop de variable ? 
    }

    public int getMaxRegister() {
        return maxRegister;
    }

    public void setMaxRegister(int maxRegister) {
        this.maxRegister = maxRegister;
    }

    public int getRegisterPointer() {
        return registerPointer;
    }

    public void setRegisterPointer(int registerPointer) {
        this.registerPointer = registerPointer;
    }

    /**
     * Increment the register pointer circulairly between 2 and 
     * maxRegister.
     */
    public void incrementRegisterPointer() {
        registerPointer++;
        if (registerPointer == maxRegister) {
            registerPointer = 2;
        }
    }

    /**
     * 
     * @return a boolean true when there is no more free register, false otherwise.
     */
    public boolean isFull() {
        return (maxRegister>=registerPointer);
    }


    public GPRegister getLastRegister() {
        return Register.getR(registerPointer);
    } 

}
