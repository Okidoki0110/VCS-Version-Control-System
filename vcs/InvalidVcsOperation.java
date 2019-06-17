package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class InvalidVcsOperation extends VcsOperation {
    /**
     * Invalid file system operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public InvalidVcsOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Execute the invalid file system operation operation.
     *
     * @param fileSystemSnapshot the active file system snapshot
     * @return return code
     */
    @Override
    public int execute(final VcsSnapshot vcsSnapshot) {
        return ErrorCodeManager.VCS_BAD_CMD_CODE;
    }

    @Override
    public String description() {
        return "\tInvalid file System";
    }
}
