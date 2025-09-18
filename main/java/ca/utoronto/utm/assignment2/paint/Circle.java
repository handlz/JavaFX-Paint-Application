package ca.utoronto.utm.assignment2.paint;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A circle object that can be used to display on a canvas through user interactions
 */
public class Circle extends Shape {

        private Point centre;
        private double radius;
        public Color color;
        private int thickness;
        private int filled;

        /**
         * Construct a new Circle with certain features needed to draw in the panel
         * @param centre The centre point of the circle
         * @param radius The distance from the centre to the outline of the circle
         * @param color The colour of the circle
         * @param thickness The thickness of the circle's outline
         */
        public Circle(Point centre, double radius, Color color, int thickness) {
                this.centre = centre;
                this.radius = radius;
                this.color = color;
                this.thickness = thickness;
                this.filled = 2;
        }

        /**
         * Construct a copy a circle given the old circle's information
         * @param oldCircle the circle used as a reference for the new clone
         */
        public Circle(Circle oldCircle) {
                this.centre = new Point(oldCircle.centre.getX(), oldCircle.centre.getY());
                this.radius = oldCircle.radius;
                this.color = oldCircle.color;
                this.thickness = oldCircle.thickness;
                this.filled = oldCircle.filled;
        }

        /**
         * Get the Centre of the Circle
         * @return The centre point of the circle
         */
        public Point getCentre() {
                return centre;
        }

        /**
         * Set the centre of a circle with a point
         * @param centre the new point which will be made the centre
         */
        public void setCentre(Point centre) {
                this.centre = centre;
        }

        /**
         * Get the radius of the circle
         * @return the radius, which is a double
         */
        public double getRadius() {
                return radius;
        }

        /**
         * Set the radius of the circle
         * @param radius the new distance from the centre
         */
        public void setRadius(double radius) {
                this.radius = radius;
        }

        /**
         * Get the thickness of the circle's outline
         * @return the thickness, which is an int from 1-10
         */
        public int getThickness() {
                return thickness;
        }

        /**
         * Set the thickness of the circle's outline
         * @param thickness the thickness, which is an int from 1-10
         */
        public void setThickness(int thickness) {
                this.thickness = thickness;
        }

        /**
         * Draw the figure onto a graphics context such that it is now displayed
         * @param g2d The graphics context that will be drawn on
         * @param panel The paint panel that operates figure construction
         */
        @Override
        public void draw(GraphicsContext g2d, PaintPanel panel) {
                double x = getCentre().x - getRadius();
                double y = getCentre().y - getRadius();
                double diameter = getRadius() * 2;
                /*
                if (isFill || this.filled == 1) {
                        this.filled = 1;
                        g2d.setFill(color);
                        g2d.fillOval(x, y, diameter, diameter);

                        g2d.setStroke(Color.BLACK);
                } else if (!isFill || this.filled == 0) {
                        this.filled = 0;
                        g2d.setStroke(color);
                }

                 */

                if (this.filled == 1) {
                        g2d.setFill(color);
                        g2d.fillOval(x, y, diameter, diameter);

                        g2d.setStroke(Color.BLACK);
                } else if (this.filled == 0) {
                        g2d.setStroke(color);
                } else if (isFill) {
                        this.filled = 1;
                        g2d.setFill(color);
                        g2d.fillOval(x, y, diameter, diameter);
                        g2d.setStroke(Color.BLACK);
                } else if (!isFill) {
                        this.filled = 0;
                        g2d.setStroke(color);
                }

                g2d.setLineWidth(this.thickness);
                g2d.strokeOval(x, y, diameter, diameter);
        }

        /**
         * Get the bounds of the circle
         * @return a bounds object with information of the positions within
         */
        @Override
        public Bounds getBounds() {
                return new BoundingBox(centre.x - radius, centre.y - radius, radius * 2, radius * 2);
        }

        /**
         * Make a copy of the current circle
         * @param figure The circle that will be copied
         * @return The new copied figure
         */
        @Override
        public Figure makeCopy(Figure figure) {
                return new Circle((Circle) figure);
        }

        /**
         * Move the circle by changing its centre
         * @param deltaX the amount of change in the x direction
         * @param deltaY the amount of change in the y direction
         */
        @Override
        public void move(double deltaX, double deltaY) {
                centre.x += deltaX;
                centre.y += deltaY;
        }
}
