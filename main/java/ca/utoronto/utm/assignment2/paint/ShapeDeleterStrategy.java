package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.geometry.Bounds;

/**
 * Strategy for deleting shapes, implements DrawingStrategy
 * When active, when a figure is clicked it is removed
 * from the paint model
 */
public class ShapeDeleterStrategy implements DrawingStrategy {

    /**
     * Mouse event to delete figure upon clicks
     * @param event the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent event, PaintModel model, PaintPanel panel) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = event.getX();
            double y = event.getY();

            Figure figureToDelete = null;
            // Iterate from topmost figure down to find the clicked figure
            for (int i = model.getFigures().size() - 1; i >= 0; i--) {
                Figure figure = model.getFigures().get(i);
                Bounds bounds = figure.getBounds();
                if (bounds.contains(x, y)) {
                    figureToDelete = figure;
                    break;
                }
            }

            if (figureToDelete != null) {
                model.removeFigure(figureToDelete);
                System.out.println("Deleted Figure: " + figureToDelete);
            }
        }
    }

    /**
     * Not necessary to get figure so returns null
     * @return null
     */
    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
