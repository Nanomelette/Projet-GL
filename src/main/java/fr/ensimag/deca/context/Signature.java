package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

import fr.ensimag.deca.DecacCompiler;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    /**
     * <p>add.</p>
     *
     * @param t a {@link fr.ensimag.deca.context.Type} object
     */
    public void add(Type t) {
        args.add(t);
    }
    
    /**
     * <p>paramNumber.</p>
     *
     * @param n a int
     * @return a {@link fr.ensimag.deca.context.Type} object
     */
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    /**
     * <p>size.</p>
     *
     * @return a int
     */
    public int size() {
        return args.size();
    }

    /**
     * <p>sameSignature.</p>
     *
     * @param compiler a {@link fr.ensimag.deca.DecacCompiler} object
     * @param s a {@link fr.ensimag.deca.context.Signature} object
     * @return a boolean
     */
    public boolean sameSignature(DecacCompiler compiler, Signature s) {
        if (s.size() != this.size()) {
            return false;
        }
        int n = this.size();
        for (int i = 0; i < n; i++) {
            if (compiler.assignCompatible(compiler, paramNumber(i), s.paramNumber(i)) == null) {
                return false;
            }
        }
        return true;
    }
}
