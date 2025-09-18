package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * A polyline strategy which can draw a polyline based on user interaction
 */
public class PolylineStrategy implements DrawingStrategy{
    private Polyline polyline;
    private Polyline tempPolyline;
    private boolean running;

    /**
     * Check if the strategy is still running
     */
    public PolylineStrategy() {
        this.running = true;
    }

    /**
     * Handle user mouse events for drawing
     * @param mouseEvent the current mouse event
     * @param model the model which stores figures
     * @param panel the panel which draws figures
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        if (!running){
            polyline = null;
            running = true;
        }

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        if (mouseEventType == MouseEvent.MOUSE_CLICKED) {
            if (this.polyline == null){
                this.polyline = new Polyline(panel.getCurrentColor(), panel.getCurrentThickness());
            }
            if (mouseEvent.getClickCount() == 2){
                this.running = false;
                model.addFigure(polyline);
                return;
            }
            this.polyline.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
        } else if (mouseEventType == MouseEvent.MOUSE_MOVED){
            if (this.polyline != null){
                this.tempPolyline = new Polyline(polyline);
                this.tempPolyline.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
                panel.clearAndRepaint(this.tempPolyline);
            }
        }
    }

    /**
     * Return the polyline
     * @return the polyline
     */
    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
