package fr.ensimag.deca.tree;

/**
 * Function that takes a tree as argument.
 *
 * @see fr.ensimag.deca.tree.Tree#iter(TreeFunction)
 * @author gl20
 * @date 01/01/2022
 * @version $Id: $Id
 */
public interface TreeFunction {
    /**
     * <p>apply.</p>
     *
     * @param t a {@link fr.ensimag.deca.tree.Tree} object
     */
    void apply(Tree t);
}
