package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A circle strategy that enables a user to build and draw a circle onto a canvas
 */
public class CircleStrategy implements DrawingStrategy{

    private Circle circle;
    private Point originCentre;

    /**
     * Handle the user creating the circle using different mouse events
     * @param mouseEvent the current mouseevent that the user makes
     * @param model the paint model that holds all figures
     * @param panel the panel that operates the drawing of all figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Circle");
            this.originCentre = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.circle = new Circle(this.originCentre, 0, panel.getCurrentColor(), panel.getCurrentThickness());

        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.circle != null) {
                double x = mouseEvent.getX() - this.originCentre.x;
                double y = mouseEvent.getY() - this.originCentre.y;
                double radius = Math.sqrt(x * x + y * y);
                this.circle.setRadius(radius);
                panel.clearAndRepaint(circle);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.circle != null) {
                model.addFigure(this.circle);
                System.out.println("Added Circle");
            }
        }
    }

    /**
     * Get the circle that is currently represented
     * @return the current circle
     */
    @Override
    public Figure getDrawingFigure() {
        return this.circle;
    }
}
