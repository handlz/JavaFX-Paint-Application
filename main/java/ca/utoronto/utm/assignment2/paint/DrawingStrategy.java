package ca.utoronto.utm.assignment2.paint;


import javafx.scene.input.MouseEvent;

/**
 * A drawing strategy that implements the strategy design pattern such that different shapes can be drawn
 */
public interface DrawingStrategy {

    /**
     * Handle a mouse event made by a user
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel);

    /**
     * Retrieves the Figure type that the DrawingStrategy relates to.
     *
     * @return the Figure type that the DrawingStrategy relates to, null if no Figure is drawn by the DrawingStrategy
     */
    Figure getDrawingFigure();
}
