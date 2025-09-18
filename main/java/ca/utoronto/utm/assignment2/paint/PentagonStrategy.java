package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.event.EventType;

/**
 * A pentagon strategy that will allow for a pentagon to be drawn
 */
public class PentagonStrategy implements DrawingStrategy {

    private Pentagon currentPentagon;
    private Point originCentre;

    /**
     * Handle mouse events made by a user trying to draw a pentagon
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Pentagon");

            // Set the center of the bounding circle to the initial mouse click location
            this.originCentre = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.currentPentagon = new Pentagon(this.originCentre, 0, panel.getCurrentColor(), panel.getCurrentThickness());
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.currentPentagon != null) {
                // Calculate the radius as the distance from the center to the current mouse position
                double x = mouseEvent.getX() - this.originCentre.x;
                double y = mouseEvent.getY() - this.originCentre.y;
                double radius = Math.sqrt(x * x + y * y);

                // Update the radius of the bounding circle in the current pentagon
                this.currentPentagon.setBoundingCircleRadius(radius);

                // This re-adds the pentagon to display the updated state while dragging
                panel.clearAndRepaint(currentPentagon);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.currentPentagon != null) {
                model.addFigure(this.currentPentagon);
                System.out.println("Added Pentagon");
            }
        }
    }

    /**
     * Return this pentagon
     * @return the pentagon object
     */
    @Override
    public Figure getDrawingFigure() {
        return this.currentPentagon;
    }
}
