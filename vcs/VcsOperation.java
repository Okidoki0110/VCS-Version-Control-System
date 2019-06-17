package vcs;

import java.util.ArrayList;

import utils.AbstractOperation;
import utils.OperationType;

public abstract class VcsOperation extends AbstractOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public VcsOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the operation.
     *
     * @param vcs the vcs
     * @return the return code
     */
    public abstract int execute(VcsSnapshot vcsSnapshot);

    /**
     * Accepts the vcs visitor.
     * @param vcs the vcs visitor
     * @return the return code
     */
    @Override
    public final int accept(Vcs vcs) {
        return vcs.visit(this);
    }
}

