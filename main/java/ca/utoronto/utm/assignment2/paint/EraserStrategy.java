package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class EraserStrategy implements DrawingStrategy {
    private EraserFigure eraserFigure;
    private double eraserSize = 20.0; // Eraser size

    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            eraserFigure = new EraserFigure(eraserSize);
            eraserFigure.addPoint(new Point(x, y));
            model.addFigure(eraserFigure); // Add eraser figure to the model

        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (eraserFigure != null) {
                eraserFigure.addPoint(new Point(x, y));
                model.notifyObserversOfChange(eraserFigure); // Notify observers directly
            }
        }
    }

    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
