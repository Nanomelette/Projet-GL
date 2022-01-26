package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class ClassDefinition extends TypeDefinition {


    /**
     * <p>Setter for the field <code>numberOfFields</code>.</p>
     *
     * @param numberOfFields a int
     */
    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    /**
     * <p>Getter for the field <code>numberOfFields</code>.</p>
     *
     * @return a int
     */
    public int getNumberOfFields() {

        return numberOfFields;
    }

    /**
     * <p>incNumberOfFields.</p>
     */
    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    /**
     * <p>Getter for the field <code>numberOfMethods</code>.</p>
     *
     * @return a int
     */
    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    /**
     * <p>Setter for the field <code>numberOfMethods</code>.</p>
     *
     * @param n a int
     */
    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    /**
     * <p>incNumberOfMethods.</p>
     *
     * @return a int
     */
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    /**
     * <p>Getter for the field <code>indexMethods</code>.</p>
     *
     * @return a int
     */
    public int getIndexMethods() {
        return indexMethods;
    }

    /**
     * <p>Setter for the field <code>indexMethods</code>.</p>
     *
     * @param index a int
     */
    public void setIndexMethods(int index) {
        this.indexMethods = index;
    }

    /**
     * <p>incIndexMethods.</p>
     */
    public void incIndexMethods() {
        indexMethods++;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    private int indexMethods;

    private DAddr addressLabelVTable = new RegisterOffset(1, Register.GB);
    private Map<Integer, Label> labelVTable = new HashMap<Integer, Label>();

    /**
     * <p>initLabelVTable.</p>
     *
     * @param otherLabelVTable a {@link java.util.Map} object
     */
    public void initLabelVTable(Map<Integer, Label> otherLabelVTable) {
        for (Map.Entry<Integer, Label> entry : otherLabelVTable.entrySet()) {
            addLabel(entry.getKey(), entry.getValue());
        }
    }

    /**
     * <p>Getter for the field <code>labelVTable</code>.</p>
     *
     * @return a {@link java.util.Map} object
     */
    public Map<Integer, Label> getLabelVTable() {
        return labelVTable;
    }

    /**
     * <p>hasKey.</p>
     *
     * @param key a int
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean hasKey(int key) {
        return labelVTable.containsKey(key);
    }

    /**
     * <p>getAddressVTable.</p>
     *
     * @return a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public DAddr getAddressVTable() {
        return addressLabelVTable;
    }

    /**
     * <p>setAddressVTable.</p>
     *
     * @param addressVTable a {@link fr.ensimag.ima.pseudocode.DAddr} object
     */
    public void setAddressVTable(DAddr addressVTable) {
        this.addressLabelVTable = addressVTable;
    }
    
    /**
     * <p>addLabel.</p>
     *
     * @param index a int
     * @param label a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public void addLabel(int index, Label label) {
        labelVTable.put(index, label);
    }
    /**
     * <p>getLabel.</p>
     *
     * @param key a int
     * @return a {@link fr.ensimag.ima.pseudocode.Label} object
     */
    public Label getLabel(int key) {
        return labelVTable.get(key);
    }

    /**
     * <p>sizeVTable.</p>
     *
     * @return a int
     */
    public int sizeVTable() {
        return labelVTable.size();
    }

    /**
     * <p>getKeys.</p>
     *
     * @return a {@link java.util.Iterator} object
     */
    public Iterator<Integer> getKeys() {
        return this.labelVTable.keySet().iterator();
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isClass() {
        return true;
    }
    
    /** {@inheritDoc} */
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    /**
     * <p>Getter for the field <code>superClass</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    /**
     * <p>Getter for the field <code>members</code>.</p>
     *
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp getMembers() {
        return members;
    }

    /**
     * <p>Constructor for ClassDefinition.</p>
     *
     * @param type a {@link fr.ensimag.deca.context.ClassType} object
     * @param location a {@link fr.ensimag.deca.tree.Location} object
     * @param superClass a {@link fr.ensimag.deca.context.ClassDefinition} object
     */
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
