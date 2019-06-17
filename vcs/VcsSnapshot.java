package vcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import filesystem.FileSystemSnapshot;
import utils.EntityType;
import utils.ErrorCodeManager;
import utils.OutputWriter;

public final class VcsSnapshot {
    private Branch currentBranch;

    private Head head;

    private final OutputWriter outputWriter;

    private Staged staged;

    private Branch master;

    private FileSystemSnapshot fileSystemSnapShot;

    /**
     * File system constructor.
     * @param outputWriter the output writer
     */
    public VcsSnapshot(FileSystemSnapshot fileSystemSnapshot, OutputWriter outputWriter) {
        this.master = new Branch(fileSystemSnapshot);
        currentBranch = master;
        head = new Head();
        head.setIndex(0);
        this.outputWriter = outputWriter;
        this.fileSystemSnapShot = fileSystemSnapshot;
    }

    /**
     * Gets the output writer.
     * @return the output writer
     */
    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    public Branch getCurrentBranch() {
        return currentBranch;
    }

    public FileSystemSnapshot getFileSystemSnapShot() {
        return fileSystemSnapShot;
    }

    /**
     * Sets the current current branch.
     * @param brnachNbranchNameame the branch to be set
     */
    public void setCurrentBranch(Branch branchName) {
        this.currentBranch = branchName;
    }

    /**
     * Adds an entity to this vcs snapshot.
     * @param type   the type of the entity
     * @param branch where to add the entity
     * @param name   the name of the entity
     * @return the return code
     */
    public int addEntity(VcsEntity entity) {
        List<VcsEntity> currentBranchContent = this.currentBranch.getContent();
        if (entity.getType().equals(EntityType.BRANCH)) {
            Branch branch = (Branch) entity;
            if (searchBranch(branch) != null) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
            branch.getContent().add(currentBranch.getContent().get(head.getIndex()));
            currentBranchContent.add(branch);
        } else {
            currentBranchContent.add(entity);
            head.setIndex(currentBranchContent.indexOf(entity));
            Staged.getInstance().clearOperations();
        }
        return ErrorCodeManager.OK;
    }

    public List<Commit> getCommits() {
        List<Commit> commits = new ArrayList<>();
        List<VcsEntity> currentBranchContent = this.currentBranch.getContent();
        for (VcsEntity entity : currentBranchContent) {
            if (entity.getType().equals(EntityType.COMMIT)) {
                commits.add((Commit) entity);
            }
        }
        return commits;
    }

    public int changeCurrentBrench(String name) {
        Branch searchBrench = new Branch(name);
        Branch branch = searchBranch(searchBrench);

        if (branch == null) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }

        this.currentBranch = branch;
        head.setIndex(getIndexOfLastCommit());
        return ErrorCodeManager.OK;
    }

    /**
     * Search a branch into VcsSnapshot.
     * @param branch brench to search
     * @return branch else null
     */
    private Branch searchBranch(Branch branch) {
        List<VcsEntity> flattedList = new ArrayList<>();
        flattedList(Arrays.asList(master), flattedList);
        int indexOfBranch = flattedList.indexOf(branch);
        if (indexOfBranch != -1) {
            return (Branch) flattedList.get(indexOfBranch);
        }
        return null;
    }

    public int revertFileSystem() {
        List<VcsEntity> currentBranchContent = currentBranch.getContent();
        Commit lastCommit = (Commit) currentBranchContent.get(head.getIndex());
        fileSystemSnapShot.setRoot(lastCommit.getSnapshot().getRoot());
        fileSystemSnapShot.setCurrentDir(lastCommit.getSnapshot().getRoot());
        Staged.getInstance().clearOperations();
        return ErrorCodeManager.OK;
    }

    public int resetFileSystemToCommit(int commitId) {
        List<VcsEntity> currentBranchContent = currentBranch.getContent();
        int index = searchCommit(commitId);
        if (index == -1) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }
        Commit commit = (Commit) currentBranchContent.get(index);
        head.setIndex(index);
        removeCommits();
        fileSystemSnapShot.setRoot(commit.getSnapshot().getRoot());
        fileSystemSnapShot.setCurrentDir(commit.getSnapshot().getRoot());
        return ErrorCodeManager.OK;
    }

    private int searchCommit(int commitId) {
        List<VcsEntity> currentBranchContent = currentBranch.getContent();
        Commit commit = new Commit(commitId);
        int index = currentBranchContent.indexOf(commit);
        if (index != -1) {
            return index;
        }
        return -1;
    }

    private void removeCommits() {
        List<VcsEntity> currentBranchContent = currentBranch.getContent();
        List<VcsEntity> commitsToRemove = new ArrayList<>();
        for (int i = head.getIndex() + 1; i < currentBranchContent.size(); i++) {
            if (currentBranchContent.get(i).getType().equals(EntityType.COMMIT)) {
                commitsToRemove.add(currentBranchContent.get(i));
            }
        }
        currentBranchContent.removeAll(commitsToRemove);
    }

    private int getIndexOfLastCommit() {
        List<VcsEntity> currentBranchContent = currentBranch.getContent();
        for (int i = currentBranchContent.size() - 1; i >= 0; i--) {
            VcsEntity entity = currentBranchContent.get(i);
            if (entity instanceof Commit) {
                return i;
            }
        }
        return -1;
    }

    private void flattedList(List<VcsEntity> list, List<VcsEntity> flatList) {
        for (VcsEntity entity : list) {
            if (entity instanceof Branch) {
                flatList.add(entity);
                flattedList(((Branch) entity).getContent(), flatList);
            }
        }
    }

    public Staged getStaged() {
        return staged;
    }

    public Head getHead() {
        return head;
    }
}
