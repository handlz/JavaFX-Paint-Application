package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Bounds;

/**
 * An interface which is the base for all shapes and lines that will be drawn
 */
public interface Figure {
    /**
     * Draw the current figure onto the canvas
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    void draw(GraphicsContext g2d, PaintPanel panel);
    // New methods for selection and movement

    /**
     * Move the figure in the desired x and y direction
     * @param deltaX the change in x
     * @param deltaY the change in y
     */
    void move(double deltaX, double deltaY); // Moves the figure

    /**
     * Get the bounds of the figure which are the positions within the figure
     * @return
     */
    Bounds getBounds(); // Returns the bounds for selection checking

    /**
     * Construct a new copy of this figure
     * @param figure the figure being copied
     * @return a new figure with the same features
     */
    Figure makeCopy(Figure figure);
}
