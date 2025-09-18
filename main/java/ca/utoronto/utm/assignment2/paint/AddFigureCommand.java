package ca.utoronto.utm.assignment2.paint;

/**
 * Command to add a figure to the PaintModel.
 */
public class AddFigureCommand implements Command {
    private PaintModel model;
    private Figure figure;

    /**
     * Construct a new AddFigureCommand with a model and figure
     * @param model Model that holds information about figures and executes commands
     * @param figure Reference of a particular figure in the panel
     */
    public AddFigureCommand(PaintModel model, Figure figure) {
        this.model = model;
        this.figure = figure;
    }

    /**
     * Executes the AddFigureCommand using the model and notifying observers of the change
     */
    @Override
    public void execute() {
        model.getFigures().add(figure);
        model.notifyObserversOfChange(figure);
    }

    /**
     * Remove the figure associated with the command from model and notifies the observers
     */
    @Override
    public void undo() {
        model.getFigures().remove(figure);
        model.notifyObserversOfChange(figure);
    }
}
