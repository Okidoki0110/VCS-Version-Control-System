package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class CheckoutOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
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
        if (!Staged.getInstance().getOperations().isEmpty()) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }
        if (operationArgs.size() == 2 && operationArgs.get(0).equalsIgnoreCase("-c")) {
            return vcsSnapshot.resetFileSystemToCommit(Integer.parseInt(operationArgs.get(1)));
        }
        if (operationArgs.size() == 1) {
            return vcsSnapshot.changeCurrentBrench(operationArgs.get(0));
        }
        return ErrorCodeManager.VCS_BAD_CMD_CODE;
    }

    @Override
    public String description() {
        return "Display current status";
    }
}
