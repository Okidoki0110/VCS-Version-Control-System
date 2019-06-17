package vcs;

import java.util.ArrayList;

import utils.OperationType;

public final class RollbackOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the mkdir operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(VcsSnapshot vcsSnapshot) {
        return vcsSnapshot.revertFileSystem();
    }

    @Override
    public String description() {
        return "Display current status";
    }
}

