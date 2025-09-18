package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A polyline figure that can be drawn on canvas
 */
public class Polyline implements Figure {
    private ArrayList<Point> points;
    private Color color;
    private int thickness;

    /**
     * Construct a new polyline
     * @param color the colour of the polyline
     * @param thickness the thickness of the polyline
     */
    public Polyline(Color color, int thickness) {
        this.points = new ArrayList<>();
        this.color = color;
        this.thickness = thickness;
    }

    /**
     * Make a new polyline given a reference polyline
     * @param polyline Make a new polyline given a reference polyline
     */
    public Polyline(Polyline polyline) {
        this.points = new ArrayList<>(polyline.points);
        this.color = polyline.color;
    }

    /**
     * Make a new polyline given a reference polyline
     * @param oldPolyline the reference
     * @param dummy the dummy integer
     */
    public Polyline(Polyline oldPolyline, int dummy) {
        this.points = new ArrayList<>();
        for (Point point : oldPolyline.points) {
            this.points.add(new Point(point.getX(), point.getY()));
        }
        this.color = oldPolyline.color;
        this.thickness = oldPolyline.thickness;
    }

    /**
     * Get the thickness of the polyline
     * @return the thickness from 1-10
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Add a point in the collection of points
     * @param point a point
     */
    public void addPoint(Point point){
        points.add(point);
    }

    /**
     * Draw the polyline
     * @param g2d the graphics context that will help in drawing the figure
     * @param panel the panel that allows for the figure to be drawn
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        g2d.setStroke(color);
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.setLineWidth(thickness);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * Get the bounds of the polyline
     * @return the bounds
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
     * Make a copy of this figure
     * @param figure the figure being copied
     * @return the polyline copy
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return new Polyline((Polyline) figure, 0);
    }

    /**
     * Move this polyline
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
}
