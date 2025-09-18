package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * A fill strategy which will allow a user to create a new fill on a canvas
 */
public class FillStrategy implements DrawingStrategy{
    private Fill fill;

    /**
     * Handle a mouse event made by a user creating this new fill
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED){
            fill = new Fill(panel.getCurrentColor());
            model.addFigure(fill);
        }
    }

    /**
     * Return the current fill object
     * @return the current fill object
     */
    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
