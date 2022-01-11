package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;

public class Memory {
    private int maxRegister = 15;
    private int registerPointer = 2;

    public Memory() {};

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
