package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A squiggle that can be drawn on canvas
 */
public class Squiggle implements Figure {
    private ArrayList<Point> points;
    private Color color;
    private int thickness;

    /**
     * Construct a squiggle
     * @param color the colour
     * @param thickness the thickness of the line
     */
    public Squiggle(Color color, int thickness) {

        this.points = new ArrayList<>();
        this.color = color;
        this.thickness = thickness;
    }

    /**
     * Make a squigle using a reference
     * @param oldSquiggle the reference
     */
    public Squiggle(Squiggle oldSquiggle) {
        this.points = new ArrayList<>();
        for (Point point : oldSquiggle.getPoints()) {
            this.points.add(new Point(point.getX(), point.getY()));
        }
        this.color = oldSquiggle.color;
        this.thickness = oldSquiggle.thickness;
    }

    /**
     * Get all points of the squiggle
     * @return list of points
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Add a point
     * @param point the point
     */
    public void addPoint(Point point){
        points.add(point);
    }

    /**
     * Get the thickness of the squiggle
     * @return the thickness
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Draw the squiggle
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    public void draw(GraphicsContext g2d, PaintPanel panel){
        g2d.setStroke(color);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.setLineWidth(thickness);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Move the squiggle in a direction
     * @param deltaX the change in x
     * @param deltaY the change in y
     */
    @Override
    public void move(double deltaX, double deltaY) {
        for (Point point : points) {
            point.x += deltaX;
            point.y += deltaY;
        }
    }

    /**
     * Get the squiggle bounds
     * @return bounds
     */
    @Override
    public Bounds getBounds() {
        double minX = points.stream().mapToDouble(p -> p.x).min().orElse(0);
        double minY = points.stream().mapToDouble(p -> p.y).min().orElse(0);
        double maxX = points.stream().mapToDouble(p -> p.x).max().orElse(0);
        double maxY = points.stream().mapToDouble(p -> p.y).max().orElse(0);
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Make a copy of squiggle
     * @param figure the figure being copied
     * @return the copy
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Squiggle((Squiggle) figure);
    }
}
