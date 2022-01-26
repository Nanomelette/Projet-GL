package fr.ensimag.deca.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Manage unique symbols.
 *
 * A Symbol contains the same information as a String, but the SymbolTable
 * ensures the uniqueness of a Symbol for a given String value. Therefore,
 * Symbol comparison can be done by comparing references, and the hashCode()
 * method of Symbols can be used to define efficient HashMap (no string
 * comparison or hashing required).
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class SymbolTable {
    private Map<String, Symbol> map = new HashMap<String, Symbol>();

    /**
     * Create or reuse a symbol.
     *
     * If a symbol already exists with the same name in this table, then return
     * this Symbol. Otherwise, create a new Symbol and add it to the table.
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link fr.ensimag.deca.tools.SymbolTable.Symbol} object
     */
    public Symbol create(String name) {
        // Symbol value = new Symbol(name);
        // this.map.putIfAbsent(name, value);
        // return value;
        if (map.containsKey(name)) {
            return map.get(name);
        }
        Symbol newsymbol = new Symbol(name);
        map.put(name, newsymbol);
        return newsymbol;
    }

    /**
     * <p>Getter for the field <code>map</code>.</p>
     *
     * @return a {@link java.util.Map} object
     */
    public Map<String, Symbol> getMap(){
        return this.map;
    }

    public static class Symbol {
        // Constructor is private, so that Symbol instances can only be created
        // through SymbolTable.create factory (which thus ensures uniqueness
        // of symbols).
        public Symbol(String name) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        private String name;

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Symbol)) {
                return false;
            }
            Symbol other = (Symbol)obj;
            return other.getName().equals(getName());
        }
    }
}
