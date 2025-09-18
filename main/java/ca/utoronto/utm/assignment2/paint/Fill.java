package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A fill figure used to cover the entire canvas
 */
public class Fill implements Figure {
    private Color color;

    /**
     * Constuct a new fill object of a certain colour
     * @param color the colour of this new fill
     */
    public Fill(Color color) {
        this.color = color;
    }

    /**
     * Draw a fill onto the canvas
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        g2d.setFill(color);
        g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
    }

    /**
     *
     * @param deltaX the change in x
     * @param deltaY the change in y
     */
    @Override
    public void move(double deltaX, double deltaY) {
        // No-op: Fill covers the entire canvas and doesn't need to move
    }

    /**
     * The bounds of this fill that will cover the paint
     * @return
     */
    @Override
    public Bounds getBounds() {
        // Return the bounds of the entire canvas
        return new BoundingBox(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /**
     * Construct a copy of this new fill
     * @param figure the figure being copied
     * @return the new fill object with the same features
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return null;
    }
}
