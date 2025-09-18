package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Bounds;

/**
 * An equilateral triangle figure that can be used to draw on a canvas
 */
public class EquilateralTriangle extends Shape {

    private Point centre;
    private final Circle boundingCircle;
    private Color color;
    private int thickness;
    private int filled;

    /**
     * Construct a new equilateral triangle
     * @param centre The centre position of the triangle
     * @param radius The radius of the bounding circle
     * @param color The colour of the triangle
     * @param thickness The thickness of the triangle's outline
     */
    public EquilateralTriangle(Point centre, double radius, Color color, int thickness) {
        this.centre = centre;
        this.boundingCircle = new Circle(centre, radius, color, thickness);// Initialize the bounding circle with the given centre and radius
        this.color = color;
        this.thickness = thickness;
        this.filled = 2;
    }


    /**
     * Construct a new equilateral triangle given an old one as a reference
     * @param oldEquilateralTriangle the old equilateral triangle that is the base of the new copy
     */
    public EquilateralTriangle(EquilateralTriangle oldEquilateralTriangle) {
        this.centre = new Point(oldEquilateralTriangle.centre.getX(), oldEquilateralTriangle.centre.getY());
        this.boundingCircle = new Circle(oldEquilateralTriangle.boundingCircle);
        this.color = oldEquilateralTriangle.color;
        this.thickness = oldEquilateralTriangle.thickness;
        this.filled = oldEquilateralTriangle.filled;
    }

    /**
     * Set the bounding equilateral triangle's, circle's radius
     * @param radius the distance from the centre to the outline of the circle
     */
    public void setBoundingCircleRadius(double radius) {
        this.boundingCircle.setRadius(radius); // Update the bounding circle's radius
    }

    // Calculate vertices based on the centre and bounding circle radius
    public Point[] getVertices() {
        Point[] vertices = new Point[3];
        double radius = boundingCircle.getRadius();
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(90 + i * 120);  // 120-degree separation
            double x = centre.x + radius * Math.cos(angle);
            double y = centre.y + radius * Math.sin(angle);
            vertices[i] = new Point(x, y);
        }
        return vertices;
    }

    /**
     * Draw the equilateral triangle onto a graphics context
     * @param g2d the graphics context which is used to help draw
     * @param panel the panel that enables the figures to draw with functionality
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        Point[] vertices = getVertices();
        /*
        if (isFill) {
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] { vertices[0].x, vertices[1].x, vertices[2].x },
                    new double[] { vertices[0].y, vertices[1].y, vertices[2].y },
                    3
            );

            g2d.setStroke(Color.BLACK);
        } else {
            g2d.setStroke(color);
        }

         */
        if (this.filled == 1) {
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] { vertices[0].x, vertices[1].x, vertices[2].x },
                    new double[] { vertices[0].y, vertices[1].y, vertices[2].y },
                    3
            );

            g2d.setStroke(Color.BLACK);
        } else if (this.filled == 0) {
            g2d.setStroke(color);
        } else if (isFill) {
            this.filled = 1;
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] { vertices[0].x, vertices[1].x, vertices[2].x },
                    new double[] { vertices[0].y, vertices[1].y, vertices[2].y },
                    3
            );
            g2d.setStroke(Color.BLACK);
        } else if (!isFill) {
            this.filled = 0;
            g2d.setStroke(color);
        }

        g2d.setLineWidth(this.thickness);
        g2d.strokePolygon(
                new double[] { vertices[0].x, vertices[1].x, vertices[2].x },
                new double[] { vertices[0].y, vertices[1].y, vertices[2].y },
                3
        );
    }

    /**
     * Move the current equilateral triangle to a different position
     * @param deltaX the change in the x direction of the bound circle's centre
     * @param deltaY the change in the y direction of the bound circle's centre
     */
    @Override
    public void move(double deltaX, double deltaY) {
        centre.x += deltaX;
        centre.y += deltaY;
        boundingCircle.setCentre(new Point(centre.x, centre.y));  // Update the bounding circle as well
    }

    /**
     * Get the bounds of the equilateral triangle, which are the positions within
     * @return
     */
    @Override
    public Bounds getBounds() {
        Point[] vertices = getVertices();
        double minX = Math.min(vertices[0].x, Math.min(vertices[1].x, vertices[2].x));
        double minY = Math.min(vertices[0].y, Math.min(vertices[1].y, vertices[2].y));
        double maxX = Math.max(vertices[0].x, Math.max(vertices[1].x, vertices[2].x));
        double maxY = Math.max(vertices[0].y, Math.max(vertices[1].y, vertices[2].y));
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Make a copy of this equilateral triangle
     * @param figure the equilateral triangle used to copy
     * @return a new equilateral triangle of same features
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new EquilateralTriangle((EquilateralTriangle) figure);
    }
}
