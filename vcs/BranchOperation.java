package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class BranchOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the mkdir operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(final VcsSnapshot vcsSnapshot) {
        if (operationArgs.size() != 2 || operationArgs.get(1).isEmpty()) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        Branch branch = new Branch(operationArgs.get(1));
        return vcsSnapshot.addEntity(branch);
    }

    @Override
    public String description() {
        return "Display current status";
    }
}
