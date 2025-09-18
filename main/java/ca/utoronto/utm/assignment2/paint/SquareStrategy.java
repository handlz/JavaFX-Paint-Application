package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A square strategy that uses mouse events to draw a square
 */
public class SquareStrategy implements DrawingStrategy {

    private Point initialCorner;
    private Square tempSquare;

    /**
     * Handle mouse events to draw a square
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Square");
            this.initialCorner = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.tempSquare = new Square(this.initialCorner, 0, panel.getCurrentColor(), panel.getCurrentThickness());
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.tempSquare != null) {
                double sideLength = Math.max(
                        Math.abs(mouseEvent.getX() - this.initialCorner.x),
                        Math.abs(mouseEvent.getY() - this.initialCorner.y)
                );
                Point topLeft = new Point(
                        Math.min(mouseEvent.getX(), this.initialCorner.x),
                        Math.min(mouseEvent.getY(), this.initialCorner.y)
                );

                Square updatedSquare = new Square(topLeft, sideLength, panel.getCurrentColor(), panel.getCurrentThickness());
                if (!this.tempSquare.isFill()) {
                    updatedSquare.setOutline();
                }
                this.tempSquare = updatedSquare;

                panel.clearAndRepaint(this.tempSquare);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.tempSquare != null) {
                model.addFigure(this.tempSquare);
                System.out.println("Added Square");
            }
            this.tempSquare = null;
        }
    }

    /**
     * Get the current square
     * @return the square
     */
    @Override
    public Figure getDrawingFigure() {
        return this.tempSquare;
    }
}
