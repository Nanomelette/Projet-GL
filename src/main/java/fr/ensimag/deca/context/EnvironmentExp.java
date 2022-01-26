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
 * @version $Id: $Id
 */
public class EnvironmentExp {

    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).

    EnvironmentExp parentEnvironment;
    HashMap<Symbol, ExpDefinition> DictionnaryMap;

    /**
     * <p>Constructor for EnvironmentExp.</p>
     *
     * @param parentEnvironment a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        DictionnaryMap = new HashMap<Symbol,ExpDefinition>();
    }

    /**
     * <p>getParent.</p>
     *
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp getParent() {
        return this.parentEnvironment;
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     *
     * @param key a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     * @return a {@link fr.ensimag.deca.context.ExpDefinition} object
     */
    public ExpDefinition get(Symbol key) {
        if(DictionnaryMap==null | key == null){
            throw new UnsupportedOperationException ("Empty Dictionnary");
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
     * @throws fr.ensimag.deca.context.EnvironmentExp.DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        if(DictionnaryMap.containsKey(name)){
            throw new DoubleDefException();
        }
        DictionnaryMap.put(name, def);
        //throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * <p>getDictionnary.</p>
     *
     * @return a {@link java.util.HashMap} object
     */
    public HashMap<Symbol, ExpDefinition> getDictionnary(){
        return this.DictionnaryMap;
    }

    /**
     * <p>unionDisjointe.</p>
     *
     * @param env2 a {@link fr.ensimag.deca.context.EnvironmentExp} object
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp unionDisjointe(EnvironmentExp env2){
        EnvironmentExp env3 = new EnvironmentExp(null);
        //assert(env2 != null);
        env3.getDictionnary().putAll(this.getDictionnary());
        env3.getDictionnary().putAll(env2.getDictionnary());
        return env3;
    }

    /**
     * <p>empilement.</p>
     *
     * @param env2 a {@link fr.ensimag.deca.context.EnvironmentExp} object
     * @return a {@link fr.ensimag.deca.context.EnvironmentExp} object
     */
    public EnvironmentExp empilement(EnvironmentExp env2){
        EnvironmentExp env3 = new EnvironmentExp(null);
        env3.getDictionnary().putAll(this.getDictionnary());
        for(Symbol symbol : env2.getDictionnary().keySet()){
            if(!env3.getDictionnary().containsKey(symbol)){
                env3.DictionnaryMap.put(symbol, env2.get(symbol));
            }
        }
        return env3;

    }


}
