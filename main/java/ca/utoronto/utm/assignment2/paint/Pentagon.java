package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A pentagon figure that can be drawn on canvas
 */
public class Pentagon extends Shape {

    private Point centre;
    private Circle boundingCircle;
    private Color color;
    private int thickness;
    private int filled;

    /**
     * Construct a new pentagon with specific features
     * @param centre the centre of the bounding circle
     * @param radius the radius of the bounding circle
     * @param color the colour of the pentagon
     * @param thickness the thickness of the pentagon
     */
    public Pentagon(Point centre, int radius, Color color, int thickness) {
        this.centre = centre;
        this.boundingCircle = new Circle(centre, radius, color, thickness); // Initialize bounding circle given centre and radius
        this.color = color;
        this.thickness = thickness;
        this.filled = 2;
    }

    /**
     * Construct a pentagon with features of the old pentagon
     * @param oldPentagon the pentagon that is referenced
     */
    public Pentagon(Pentagon oldPentagon) {
        this.centre = new Point(oldPentagon.centre.getX(), oldPentagon.centre.getY());
        this.boundingCircle = new Circle(oldPentagon.boundingCircle);
        this.color = oldPentagon.color;
        this.thickness = oldPentagon.thickness;
        this.filled = oldPentagon.filled;
    }

    /**
     * Set the bounding circle radius
     * @param radius the radius of the bounding circle
     */
    public void setBoundingCircleRadius(double radius) {
        this.boundingCircle.setRadius(radius); // Update the bounding circle's radius
    }

    // Calculate vertices based on the centre and bounding circle radius
    public Point[] getVertices() {
        Point[] vertices = new Point[5];
        double radius = boundingCircle.getRadius();
        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(90 + i * 72);  // 72-degree separation
            double x = centre.x + radius * Math.cos(angle);
            double y = centre.y + radius * Math.sin(angle);
            vertices[i] = new Point(x, y);
        }
        return vertices;
    }

    /**
     * Draw the pentagon on the canvas
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        Point[] vertices = getVertices();
        /*
        if (isFill) {
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] {vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x, vertices[4].x},
                    new double[] {vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y, vertices[4].y},
                    5
            );

            g2d.setStroke(Color.BLACK);
        } else {
            g2d.setStroke(color);
        }

         */

        if (this.filled == 1) {
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] {vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x, vertices[4].x},
                    new double[] {vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y, vertices[4].y},
                    5
            );

            g2d.setStroke(Color.BLACK);
        } else if (this.filled == 0) {
            g2d.setStroke(color);
        } else if (isFill) {
            this.filled = 1;
            g2d.setFill(color);
            g2d.fillPolygon(
                    new double[] {vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x, vertices[4].x},
                    new double[] {vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y, vertices[4].y},
                    5
            );
            g2d.setStroke(Color.BLACK);
        } else if (!isFill) {
            this.filled = 0;
            g2d.setStroke(color);
        }

        g2d.setLineWidth(this.thickness);
        g2d.strokePolygon(
                new double[] {vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x, vertices[4].x},
                new double[] {vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y, vertices[4].y},
                5);
    }

    /**
     * Get the bounds of the pentagon
     * @return the bounds
     */
    @Override
    public Bounds getBounds() {
        Point[] vertices = getVertices();
        double minX = Math.min(vertices[0].x, Math.min(vertices[1].x, Math.min(vertices[2].x, Math.min(vertices[3].x, vertices[4].x))));
        double minY = Math.min(vertices[0].y, Math.min(vertices[1].y, Math.min(vertices[2].y, Math.min(vertices[3].y, vertices[4].y))));
        double maxX = Math.max(vertices[0].x, Math.max(vertices[1].x, Math.max(vertices[2].x, Math.max(vertices[3].x, vertices[4].x))));
        double maxY = Math.max(vertices[0].y, Math.max(vertices[1].y, Math.max(vertices[2].y, Math.max(vertices[3].y, vertices[4].y))));
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Make a copy pentagon with the current as a reference
     * @param figure the figure being copied
     * @return a pentagon clone
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Pentagon((Pentagon) figure);
    }

    /**
     * Move the pentagon in a desired direction
     * @param deltaX the change in x
     * @param deltaY the change in y
     */
    @Override
    public void move(double deltaX, double deltaY) {
        centre.x += deltaX;
        centre.y += deltaY;
        boundingCircle.setCentre(new Point(centre.x, centre.y));  // Update bounding circle
    }
}
