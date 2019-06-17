package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;

public final class CommitOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
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

        if (Staged.getInstance().getOperations().isEmpty() || operationArgs.size() != 2
                || !operationArgs.get(0).toLowerCase().contentEquals("-m")) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        Commit commit = new Commit(vcsSnapshot.getFileSystemSnapShot().cloneFileSystem(),
                operationArgs.get(1));
        return vcsSnapshot.addEntity(commit);

    }

    @Override
    public String description() {
        return "Display current status";
    }

}
