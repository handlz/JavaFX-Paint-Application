package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A squiggle strategy that can draw a squiggle through user mouse evnts
 */
public class SquiggleStrategy implements DrawingStrategy{
    private Squiggle squiggle;

    /**
     * Handle mouse events to draw
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.squiggle.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            panel.clearAndRepaint(squiggle);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            this.squiggle = new Squiggle(panel.getCurrentColor(), panel.getCurrentThickness());
            this.squiggle.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
        }else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            model.addFigure(this.squiggle);
        }
    }

    /**
     * Get the squiggle
     * @return current squiggle
     */
    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
