package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
/**
* Eraser tool that clears part of the canvas
*/
public class EraserFigure implements Figure {

    private ArrayList<Point> points;
    private double eraserSize;

    /**
     * EraserFigure constructor, creates new eraser
     * with a specified size
     * @param eraserSize
     */
    public EraserFigure(double eraserSize) {
        this.points = new ArrayList<>();
        this.eraserSize = eraserSize;
    }

    /**
     * Adds a point where
     * eraser has been dragged
     * @param point
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * Creates eraser points on the canvas
     * @param g2d
     * @param panel
     */
    @Override
    public void draw(GraphicsContext g2d, PaintPanel panel) {
        for (Point point : points) {
            g2d.clearRect(point.x - eraserSize / 2, point.y - eraserSize / 2, eraserSize, eraserSize);
        }
    }

    /**
     * Moves eraser by specified deltaX/deltaY
     * @param deltaX
     * @param deltaY
     */
    @Override
    public void move(double deltaX, double deltaY) {
        for (Point point : points) {
            point.x += deltaX;
            point.y += deltaY;
        }
    }

    /**
     * Gets the bounding box of the eraser figure.
     *
     * @return BoundingBox
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
     * @param figure
     * @return Null
     */
    @Override
    public Figure makeCopy(Figure figure) {
        return null;
    }
}
