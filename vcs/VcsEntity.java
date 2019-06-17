package vcs;

import utils.AbstractEntity;
import utils.OutputWriter;

public abstract class VcsEntity extends AbstractEntity {
    protected String name;

    /**
     * @return the name of the entity
     */
    public String getName() {
        return this.name;
    }

    /**
     * Prints the entity.
     *
     * @param tabs         indentation
     * @param outputWriter the output writer
     */
    public abstract void print(String tabs, OutputWriter outputWriter);
}
