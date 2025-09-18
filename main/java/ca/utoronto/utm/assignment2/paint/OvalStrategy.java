package ca.utoronto.utm.assignment2.paint;


import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * An oval strategy that will allow for an oval to be drawn on the canvas
 */
public class OvalStrategy implements DrawingStrategy{

    private Oval tempOval;
    private Point initialCorner;

    /**
     * Handle mouse events made by a user trying to make an oval
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            System.out.println("Started Oval");
            this.initialCorner = new Point(mouseEvent.getX(), mouseEvent.getY());
            this.tempOval = new Oval(this.initialCorner, 0, 0, panel.getCurrentColor(), panel.getCurrentThickness());
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            if (this.tempOval != null) {
                double x = mouseEvent.getX() - this.initialCorner.x;
                double y = mouseEvent.getY() - this.initialCorner.y;
                double width = Math.sqrt(x * x);
                double height = Math.sqrt(y * y);

                Point topLeft = new Point(Math.min(mouseEvent.getX(), this.initialCorner.x), Math.min(mouseEvent.getY(),
                        this.initialCorner.y));

                Oval updatedOval = new Oval(topLeft, width, height, panel.getCurrentColor(), panel.getCurrentThickness());
                if (!this.tempOval.isFill()) {
                    updatedOval.setOutline();
                }
                this.tempOval = updatedOval;

                panel.clearAndRepaint(this.tempOval);
            }
        }  else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            if (this.tempOval != null) {
                model.addFigure(this.tempOval);
                System.out.println("Added Oval");
            }
        }
    }

    /**
     * Return this current oval
     * @return the oval
     */
    @Override
    public Figure getDrawingFigure() {
        return this.tempOval;
    }
}
