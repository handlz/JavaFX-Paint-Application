package ca.utoronto.utm.assignment2.paint;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An oval shape that can be used to draw onto a canvas with specific features
 */
public class Oval extends Shape {

    private Point topLeft;
    private final Rectangle boundingBox;
    private Color color;
    private int thickness;
    private int filled;

    /**
     * Constuct a new oval object with specific features
     * @param topLeft the top left position of the rectangle bounding the oval
     * @param width the width of the rectangle bounding the oval
     * @param height the height of the rectangle bounding the oval
     * @param color the colour of the new oval
     * @param thickness the thickness of the oval's outline
     */
    public Oval(Point topLeft, double width, double height, Color color, int thickness) {
        this.topLeft = topLeft;
        // Initialize the bounding box with given width and height
        this.boundingBox = new Rectangle(topLeft, width, height, color, thickness);
        this.color = color;
        this.thickness = thickness;
        this.filled = 2;
    }

    /**
     * Construct a copy of an oval given an old oval as a reference
     * @param oldOval the reference oval
     */
    public Oval(Oval oldOval){
        this.topLeft = new Point(oldOval.getTopLeft().getX(), oldOval.getTopLeft().getY());
        this.boundingBox = new Rectangle(oldOval.boundingBox);
        this.color = oldOval.color;
        this.thickness = oldOval.thickness;
        this.filled = oldOval.filled;
    }

    /**
     * Get the top left position of the rectangle bounding the oval
     * @return the point of the top left position
     */
    public Point getTopLeft() {
        return this.topLeft;
    }

    /**
     * Get the width of the rectangle bounding the oval
     * @return the width of rectangle
     */
    public double getWidth() {
        return this.boundingBox.getWidth();
    }

    /**
     * Get the height of the rectangle bounding the oval
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.boundingBox.getHeight();
    }

    /**
     * Draw the oval onto a canvas
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
            g2d.fillOval(x, y, width, height);

            g2d.setStroke(Color.BLACK);
        } else {
            g2d.setStroke(color);
        }

         */

        if (this.filled == 1) {
            g2d.setFill(color);
            g2d.fillOval(x, y, width, height);

            g2d.setStroke(Color.BLACK);
        } else if (this.filled == 0) {
            g2d.setStroke(color);
        } else if (isFill) {
            this.filled = 1;
            g2d.setFill(color);
            g2d.fillOval(x, y, width, height);
            g2d.setStroke(Color.BLACK);
        } else if (!isFill) {
            this.filled = 0;
            g2d.setStroke(color);
        }

        g2d.setLineWidth(this.thickness);
        g2d.strokeOval(x, y, width, height);
    }

    /**
     * Move the oval in a specific x and y direction
     * @param deltaX the change in x
     * @param deltaY the change in y
     */
    @Override
    public void move(double deltaX, double deltaY) {
        topLeft.x += deltaX;
        topLeft.y += deltaY;
    }

    /**
     * Get the bounds of the oval, which are the positions within
     * @return the bounds of the oval
     */
    @Override
    public Bounds getBounds() {
        return new BoundingBox(topLeft.x, topLeft.y, getWidth(), getHeight());
    }

    /**
     * Make a copy of the oval as a reference
     * @param figure the figure being copied
     * @return a clone of the old oval
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Oval((Oval) figure);
    }
}
