package vcs;

import java.util.ArrayList;

import utils.ErrorCodeManager;
import utils.OperationType;
import utils.OutputWriter;

public final class StatusOperation extends VcsOperation {
    /**
     * status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
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
        String currentBrench = vcsSnapshot.getCurrentBranch().getName();
        String stagesDescription = Staged.getInstance().getStagedOperationDescriptions();
        String outMessage = String.format("On branch: %s%n%s", currentBrench, stagesDescription);
        writer.write(outMessage);

        return ErrorCodeManager.OK;
    }

    @Override
    public String description() {
        return "Display current status";
    }

}
