package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

/**
 * A shape class that allows for fill and outlines of figures
 */
abstract class Shape implements Figure {

    protected boolean isFill = true;

    /**
     * Set the fill of the figure to true
     */
    public void setFill() {
        isFill = true;
    }

    /**
     * Set the fill of the figure to false
     */
    public void setOutline() {
        isFill = false;
    }

    /**
     * Check the fill
     * @return true or false
     */
    public boolean isFill() {
        return isFill;
    }
}
