package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.AbstractDeclMethod;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractCellEditor;

import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl20
 * @date 01/01/2022
 */
public class ClassDefinition extends TypeDefinition {


    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {

        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;

    private DAddr addressLabelVTable = new RegisterOffset(1, Register.GB);
    private Map<Integer, Label> labelVTable = new HashMap<Integer, Label>();

    public void initLabelVTable(Map<Integer, Label> otherLabelVTable) {
        for (Map.Entry<Integer, Label> entry : otherLabelVTable.entrySet()) {
            addLabel(entry.getKey(), entry.getValue());
        }
    }

    public Map<Integer, Label> getLabelVTable() {
        return labelVTable;
    }

    public Boolean hasKey(int key) {
        return labelVTable.containsKey(key);
    }

    public DAddr getAddressVTable() {
        return addressLabelVTable;
    }

    public void setAddressVTable(DAddr addressVTable) {
        this.addressLabelVTable = addressVTable;
    }
    
    public void addLabel(int index, Label label) {
        labelVTable.put(index, label);
    }
    public Label getLabel(int key) {
        return labelVTable.get(key);
    }

    public int sizeVTable() {
        return labelVTable.size();
    }

    public Iterator<Integer> getKeys() {
        return this.labelVTable.keySet().iterator();
    }
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }
    
}
