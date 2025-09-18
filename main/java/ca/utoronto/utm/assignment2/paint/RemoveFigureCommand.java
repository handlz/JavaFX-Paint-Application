package ca.utoronto.utm.assignment2.paint;

/**
 * Command to remove a figure from the PaintModel.
 */
public class RemoveFigureCommand implements Command {
    private PaintModel model;
    private Figure figure;

    /**
     * Construct the command
     * @param model model used to contain figures
     * @param figure the figure that is reference
     */
    public RemoveFigureCommand(PaintModel model, Figure figure) {
        this.model = model;
        this.figure = figure;
    }

    /**
     * Execute the command
     */
    @Override
    public void execute() {
        model.getFigures().remove(figure);
        model.notifyObserversOfChange(figure);
    }

    /**
     * Undo the figure implementation
     */
    @Override
    public void undo() {
        model.getFigures().add(figure);
        model.notifyObserversOfChange(figure);
    }
}
