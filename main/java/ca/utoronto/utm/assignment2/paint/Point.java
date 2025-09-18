package ca.utoronto.utm.assignment2.paint;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public class Point implements Figure {

        protected boolean isFill = false;

        double x, y; // Available to our package

        /**
         * Construct a new point
         * @param x the x coordinate
         * @param y the y coordinate
         */
        Point(double x, double y){
                this.x=x; this.y=y;
        }

        /**
         * Get the x coordinate
         * @return the x coordinate
         */
        public double getX(){
                return this.x;
        }

        /**
         * Get the y coordinate
         * @return the x coordinate
         */
        public double getY(){
                return this.y;
        }

        /**
         * Draw the point onto a convas
         * @param g2d the graphics context that will help in drawing the figure
         * @param panel the panel that allows for the figure to be drawn
         */
        @Override
        public void draw(GraphicsContext g2d, PaintPanel panel) {
        }

        /**
         * Move the point
         * @param deltaX the change in x
         * @param deltaY the change in y
         */
        @Override
        public void move(double deltaX, double deltaY) {
                x += deltaX;
                y += deltaY;
        }

        /**
         * Get the bounds of the point
         * @return the bounds
         */
        @Override
        public Bounds getBounds() {
                return new BoundingBox(x, y, 1, 1);  // Point has a minimal bounding box
        }

        /**
         * Return this point
         * @param figure the figure being copied
         * @return the point object
         */
        @Override
        public Figure makeCopy(Figure figure) {
                return null;
        }
}
