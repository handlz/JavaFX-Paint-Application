package ca.utoronto.utm.assignment2.paint;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A rectangle that can be drawn onto canvas
 */
public class Rectangle extends Shape {

    private Point topLeft;
    private double width, height;
    public Color color;
    public int thickness;
    protected int filled;

    /**
     * Construct a rectangle
     * @param topLeft the top left position
     * @param width the rectangle's width
     * @param height the rectangle's height
     * @param color the rectangle's colour
     * @param thickness the rectangle's thickness
     */
    public Rectangle(Point topLeft, double width, double height, Color color, int thickness) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        this.color = color;
        this.thickness = thickness;
        this.filled = 2;
    }

    /**
     * Construct a rectangle given a reference
     * @param oldRectangle the reference
     */
    public Rectangle(Rectangle oldRectangle) {
        this.topLeft = new Point(oldRectangle.getTopLeft().getX(), oldRectangle.getTopLeft().getY());
        this.width = oldRectangle.getWidth();
        this.height = oldRectangle.getHeight();
        this.color = oldRectangle.color;
        this.thickness = oldRectangle.thickness;
        this.filled = oldRectangle.filled;
    }

    /**
     * Make a copy of the rectangle
     * @param figure the figure being copied
     * @return the copy
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Rectangle((Rectangle) figure);
    }

    /**
     * Get the top left position
     * @return the top left
     */
    public Point getTopLeft() {
        return topLeft;
    }

    /**
     * Get the rectangle's width
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the rectangle's height
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set dimensions for the rectangle
     * @param width width dimension
     * @param height height dimension
     */
    public void setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Draw the rectangle
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        double x = getTopLeft().x;
        double y = getTopLeft().y;
        double width = getWidth();
        double height = getHeight();
        /*
        if (isFill) {
            g2d.setFill(color);
            g2d.fillRect(x, y, width, height);

            g2d.setStroke(Color.BLACK);
        } else {
            g2d.setStroke(color);
        }

         */

        if (this.filled == 1) {
            g2d.setFill(color);
            g2d.fillRect(x, y, width, height);

            g2d.setStroke(Color.BLACK);
        } else if (this.filled == 0) {
            g2d.setStroke(color);
        } else if (isFill) {
            this.filled = 1;
            g2d.setFill(color);
            g2d.fillRect(x, y, width, height);
            g2d.setStroke(Color.BLACK);
        } else if (!isFill) {
            this.filled = 0;
            g2d.setStroke(color);
        }

        g2d.setLineWidth(this.thickness);
        g2d.strokeRect(x, y, width, height);
    }

    @Override
    public void move(double deltaX, double deltaY) {
        topLeft.x += deltaX;
        topLeft.y += deltaY;
    }

    /**
     * Get the bounds of the rectangle
     * @return the bounds
     */
    @Override
    public Bounds getBounds() {
        return new BoundingBox(topLeft.x, topLeft.y, width, height);
    }
}
