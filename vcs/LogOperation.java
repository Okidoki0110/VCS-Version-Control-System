package vcs;

import java.util.ArrayList;
import java.util.List;

import utils.ErrorCodeManager;
import utils.OperationType;
import utils.OutputWriter;

public final class LogOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
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
        OutputWriter writer = vcsSnapshot.getOutputWriter();
        List<Commit> currentBranchCommits = vcsSnapshot.getCommits();
        StringBuilder message = new StringBuilder();
        for (Commit commit : currentBranchCommits) {
            message.append(String.format("Commit id: %s%n", commit.getId()));
            if (commit.name.equals(currentBranchCommits.
                    get(currentBranchCommits.size() - 1).name)) {
                message.append(String.format("Message: %s%n", commit.getComment()));
            } else {
                message.append(String.format("Message: %s%n%n", commit.getComment()));
            }
        }
        writer.write(message.toString());
        return ErrorCodeManager.OK;
    }

    @Override
    public String description() {
        return "Display current status";
    }
}
