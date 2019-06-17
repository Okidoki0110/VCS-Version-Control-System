package vcs;

import filesystem.FileSystemSnapshot;
import utils.EntityType;
import utils.IDGenerator;
import utils.OutputWriter;

public final class Commit extends VcsEntity {

    private FileSystemSnapshot snapshot;
    private String comment;

    /**
     * Commit constructor.
     *
     * @param current file system snapshot
     */
    public Commit(final FileSystemSnapshot snapshot, final String comment) {
        this.id = IDGenerator.generateCommitID();
        this.type = EntityType.COMMIT;
        this.snapshot = snapshot;
        this.comment = comment;
        this.name = String.format("commit %d", id);
    }

    public Commit(int commitId) {
        this.id = commitId;
        this.type = EntityType.COMMIT;
        this.name = String.format("commit %d", id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Commit)) {
            return false;
        }
        Commit commit = (Commit) o;
        return commit.name.equals(name);
    }

    @Override
    public int hashCode() {
        final int r = 17;
        int result = r;
        final int c = 31;
        result = c * result + name.hashCode();
        return result;
    }

    /**
     * Prints the commit name.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public void print(String tabs, OutputWriter outputWriter) {
        outputWriter.write(tabs + this.name);
    }

    public FileSystemSnapshot getSnapshot() {
        return snapshot;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean hasEntity(String entityPath) {
        return false;
    }
}
