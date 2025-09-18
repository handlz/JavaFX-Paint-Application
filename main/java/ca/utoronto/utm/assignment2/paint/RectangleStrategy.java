package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A rectangle strategy that draws a rectangle based on user mouse events
 */
public class RectangleStrategy implements DrawingStrategy{

    private Rectangle tempRectangle;
    private Point initialCorner;

    /**
     * Handle mouse events to draw
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Rectangle");
            this.initialCorner = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.tempRectangle = new Rectangle(this.initialCorner, 0, 0, panel.getCurrentColor(), panel.getCurrentThickness());
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.tempRectangle != null) {
                double width = Math.abs(mouseEvent.getX() - this.initialCorner.x);
                double height = Math.abs(mouseEvent.getY() - this.initialCorner.y);
                Point topLeft = new Point(Math.min(mouseEvent.getX(), this.initialCorner.x), Math.min(mouseEvent.getY(),
                        this.initialCorner.y));

                Rectangle updatedRectangle = new Rectangle(topLeft, width, height, panel.getCurrentColor(), panel.getCurrentThickness());
                if (!this.tempRectangle.isFill()) {
                    updatedRectangle.setOutline();
                }
                this.tempRectangle = updatedRectangle;

                panel.clearAndRepaint(this.tempRectangle);
            }
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.tempRectangle != null) {
                model.addFigure(this.tempRectangle);
                System.out.println("Added Rectangle");
            }
            this.tempRectangle = null;
        }
    }

    /**
     * Get this rectangle
     * @return the rectangle
     */
    @Override
    public Figure getDrawingFigure() {
        return this.tempRectangle;
    }
}
