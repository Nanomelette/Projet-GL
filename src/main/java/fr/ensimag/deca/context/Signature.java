package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl20
 * @date 01/01/2022
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }

    public boolean sameSignature(Signature s) {
        if (s.size() != this.size()) {
            return false;
        }
        int n = this.size();
        for (int i = 0; i < n; i++) {
            if (!this.paramNumber(i).sameType(s.paramNumber(i))) {
                return false;
            }
        }
        return true;
    }
}
