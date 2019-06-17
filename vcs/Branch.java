package vcs;

import java.util.LinkedList;
import java.util.List;

import filesystem.FileSystemSnapshot;
import utils.EntityType;
import utils.IDGenerator;
import utils.OutputWriter;

public final class Branch extends VcsEntity {

    private List<VcsEntity> childrenEntities;

    /**
     * First Branch constructor.
     *
     * @param name of the branch
     */
    public Branch(final FileSystemSnapshot fileSystemSnapShot) {
        this.id = IDGenerator.generateFileID();
        this.type = EntityType.BRANCH;
        this.name = "master";
        this.childrenEntities = new LinkedList<>();
        Commit firstCommit = new Commit(fileSystemSnapShot.cloneFileSystem(), "First commit");
        this.childrenEntities.add(firstCommit);
    }

    public Branch(String name) {
        this.id = IDGenerator.generateFileID();
        this.type = EntityType.BRANCH;
        this.name = name;
        this.childrenEntities = new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }
        Branch branch = (Branch) o;
        return branch.name.equals(name);
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
     * Prints the branch content.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public void print(String tabs, OutputWriter outputWriter) {
        outputWriter.write(tabs + this.name + "\n");
        tabs += "\t";
        for (VcsEntity entity : childrenEntities) {
            entity.print(tabs, outputWriter);
        }
    }

    /**
     * Adds a child entity.
     *
     * @param entity the child entity
     *//*
     * protected void addEntity(VcsEntity entity) {
     * this.childrenEntities.add(entity); }
     */

    /**
     * Gets all the children entities of this branch.
     * @return the children entities
     */
    public List<VcsEntity> getContent() {
        return this.childrenEntities;
    }

    /**
     * Checks if branch has commits on it.
     * @param commitName name of commit
     * @return whether the branch holds the entity specified by name
     */
    public boolean hasEntity(String commitName) {
        return childrenEntities.isEmpty();
    }

}
