package ca.utoronto.utm.assignment2.paint;

/**
 * The Command interface declares methods for executing and undoing actions.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command.
     */
    void undo();
}
