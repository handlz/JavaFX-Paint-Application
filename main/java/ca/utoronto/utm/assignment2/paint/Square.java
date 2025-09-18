package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A square that can be drawn
 */
public class Square extends Rectangle {

    /**
     * Construct a square
     * @param topLeft position
     * @param sideLength length of side
     * @param color colour of square
     * @param thickness thickess of outline
     */
    public Square(Point topLeft, double sideLength, Color color, int thickness) {

        super(topLeft, sideLength, sideLength, color, thickness);  // In a square, width = height
        this.filled = 2;

    }

    /**
     * Make a square using a reference
     * @param oldSquare the reference
     */
    public Square(Square oldSquare) {
        super(oldSquare);
        this.filled = oldSquare.filled;
    }

    /**
     * Get the side length
     * @return the side length
     */
    public double getSideLength() {
        return this.getWidth();  // Since width == height, returning one of them suffices
    }


    /**
     * Draw the square
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        double x = getTopLeft().x;
        double y = getTopLeft().y;
        double sideLength = getSideLength();
        /*
        if (isFill) {
            g2d.setFill(color);
            g2d.fillRect(x, y, sideLength, sideLength);

            g2d.setStroke(Color.BLACK);
        } else {
            g2d.setStroke(color);
        }

         */

        if (this.filled == 1) {
            g2d.setFill(color);
            g2d.fillRect(x, y, sideLength, sideLength);

            g2d.setStroke(Color.BLACK);
        } else if (this.filled == 0) {
            g2d.setStroke(color);
        } else if (isFill) {
            this.filled = 1;
            g2d.setFill(color);
            g2d.fillRect(x, y, sideLength, sideLength);
            g2d.setStroke(Color.BLACK);
        } else if (!isFill) {
            this.filled = 0;
            g2d.setStroke(color);
        }

        g2d.setLineWidth(this.thickness);
        g2d.strokeRect(x, y, sideLength, sideLength);
    }

    /**
     * Make a copy of this square
     * @param figure the figure being copied
     * @return the copy
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Square((Square) figure);
    }
}
