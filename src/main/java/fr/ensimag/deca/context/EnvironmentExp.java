package fr.ensimag.deca.context;

import java.util.*;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl20
 * @date 01/01/2022
 */
public class EnvironmentExp {

    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).

    EnvironmentExp parentEnvironment;

    List<EnvironmentExp> ListDictionaries = new LinkedList<EnvironmentExp>();
    HashMap<Symbol, ExpDefinition> DictionnaryMap;

    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;

        if( parentEnvironment == null ){
            /* initialisation de la Hashmap*/
            DictionnaryMap = new HashMap<Symbol,ExpDefinition>();
        }
        
        else {
            
        }

    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        if(!DictionnaryMap.containsKey(key)){
            return null;
        }
        return DictionnaryMap.get(key);
        //throw new UnsupportedOperationException("not yet implemented");

    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        if(DictionnaryMap.containsKey(name)){
            throw new DoubleDefException();
        }
        DictionnaryMap.put(name, def);
        
        //throw new UnsupportedOperationException("not yet implemented");
    }

}
