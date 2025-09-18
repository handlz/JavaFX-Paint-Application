package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.event.EventType;

/**
 * A triangle strategy that can draw the figure using mouse events
 */
public class TriangleStrategy implements DrawingStrategy {

    private EquilateralTriangle currentTriangle;
    private Point originCentre;

    /**
     * Handle mouse events to draw the triangle
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Triangle");

            // Set the center of the bounding circle to the initial mouse click location
            this.originCentre = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.currentTriangle = new EquilateralTriangle(this.originCentre, 0, panel.getCurrentColor(), panel.getCurrentThickness());
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.currentTriangle != null) {
                // Calculate the radius as the distance from the center to the current mouse position
                double x = mouseEvent.getX() - this.originCentre.x;
                double y = mouseEvent.getY() - this.originCentre.y;
                double radius = Math.sqrt(x * x + y * y);

                // Update the radius of the bounding circle in the current triangle
                this.currentTriangle.setBoundingCircleRadius(radius);

                // This re-adds the triangle to display the updated state while dragging
                panel.clearAndRepaint(currentTriangle);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.currentTriangle != null) {
                model.addFigure(this.currentTriangle);
                System.out.println("Added triangle");
            }
        }
    }

    /**
     * Get the current triangle
     * @return the triangle
     */
    @Override
    public Figure getDrawingFigure() {
        return this.currentTriangle;
    }
}
