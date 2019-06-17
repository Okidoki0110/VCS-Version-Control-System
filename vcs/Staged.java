package vcs;

import java.util.ArrayList;
import java.util.List;

import utils.AbstractOperation;

public final class Staged {

    private static Staged instance;

    private List<AbstractOperation> operations = new ArrayList<>();

    public static Staged getInstance() {
        if (instance == null) {
            instance = new Staged();
        }
        return instance;
    }

    public List<AbstractOperation> getOperations() {
        return operations;
    }

    public void addOperations(AbstractOperation operation) {
        operations.add(operation);
    }

    public void clearOperations() {
        operations.clear();
    }

    public String getStagedOperationDescriptions() {
        StringBuilder message = new StringBuilder();
        message.append("Staged changes:\n");
        for (AbstractOperation operation : operations) {
            message.append(operation.description() + "\n");
        }
        return message.toString();
    }

}
