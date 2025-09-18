package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;

import java.util.ArrayList;
/**
 * Manages the selection and movement of figures within the paint application.
 * Implements the {@DrawingStrategy} interface
 */
public class SelectionStrategy implements DrawingStrategy {

    /**
     * Represents the current state of the selection process.
     */
    private enum State { IDLE, SELECTING, MOVING }
    private State state = State.IDLE;

    private static ArrayList<Figure> selectedFigures;
    private double startX, startY;
    private double endX, endY;

    private double lastX, lastY; // For moving figures
    private boolean selectionBoxActive = false;

    /**
     * Initializes the selection strategy with an empty selection.
     */
    public SelectionStrategy() {
        selectedFigures = new ArrayList<>();
    }

    /**
     * Handles mouse events to manage selection and movement of figures.
     * @param mouseEvent the mouse event to process
     * @param model      the paint model containing figures
     * @param panel      the paint panel to redraw as needed
     */
    @Override
    public void handleMouseEvent(MouseEvent mouseEvent, PaintModel model, PaintPanel panel) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            if (state == State.IDLE) {
                // Check if click is inside the selection box
                Bounds selectionBounds = getMovableSelectionBounds();
                if (selectionBoxActive && selectionBounds.contains(x, y)) {
                    // Start moving selected figures
                    state = State.MOVING;
                    lastX = x;
                    lastY = y;

                } else {
                    // Start selection
                    state = State.SELECTING;
                    startX = x;
                    startY = y;
                    endX = x;
                    endY = y;
                    selectionBoxActive = true;
                    selectedFigures.clear();  // Clear previous selection
                }
                panel.redraw();  // Update display
            }
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            if (state == State.SELECTING) {
                // Update selection box dimensions as the user drags
                endX = x;
                endY = y;
                panel.redraw();           // Redraw to update the selection box in PaintPanel
            } else if (state == State.MOVING) {
                // Move selected figures
                double deltaX = x - lastX;
                double deltaY = y - lastY;
                moveSelectedFigures(deltaX, deltaY);
                lastX = x;
                lastY = y;
                panel.redraw();  // Update canvas to reflect moved figures
            }
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (state == State.SELECTING) {
                // Finalize selection box
                endX = x;
                endY = y;

                // Select figures within the selection box
                Bounds selectionBounds = getSelectionBounds();
                selectedFigures.clear();
                for (Figure figure : model.getFigures()) {
                    if (isWithinSelectionBox(figure, selectionBounds)) {
                        selectedFigures.add(figure);
                    }
                }
                // Keep selection box visible
                state = State.IDLE;
                panel.redraw();              // Redraw to finalize the display with selection
            } else if (state == State.MOVING) {
                // Finish moving
                state = State.IDLE;
                selectionBoxActive = false;  // Hide selection box after moving figures
                selectedFigures.clear();     // Clear selection
                panel.redraw();
            }
        }
    }
    /**
     * Moves all selected figures by the specified deltaX/deltaY.
     * @param deltaX the change in the X direction
     * @param deltaY the change in the Y direction
     */
    private void moveSelectedFigures(double deltaX, double deltaY) {
        selectionBoxActive = false;
        for (Figure figure : selectedFigures) {
            figure.move(deltaX, deltaY);
        }
    }

    /**
     * Calculates the bounds within which the selected figures can be moved.
     * @return the movable selection bounds or {@code null} if not active
     */
    private Bounds getMovableSelectionBounds(){
        if (!selectionBoxActive) {
            return null;
        }
        Bounds temp_bounds = getSelectionBounds();
        Double minix = temp_bounds.getMinX();
        Double miniy = temp_bounds.getMinY();
        Double maxix = temp_bounds.getMaxX();
        Double maxiy = temp_bounds.getMaxY();
        for (Figure figure : selectedFigures) {
            temp_bounds = figure.getBounds();
            minix = Math.min(temp_bounds.getMinX(), minix);
            miniy = Math.min(temp_bounds.getMinY(), miniy);
            maxix = Math.max(temp_bounds.getMaxX(), maxix);
            maxiy = Math.max(temp_bounds.getMaxY(), maxiy);


        }
        return new BoundingBox(minix,miniy,maxix-minix,maxiy-miniy);
    }
    /**
     * Retrieves the current bounds of the selection box.
     * @return the selection bounds or null if not active
     */
    private Bounds getSelectionBounds() {
        if (!selectionBoxActive) {
            return null;
        }
        double minX = Math.min(startX, endX);
        double minY = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        return new BoundingBox(minX, minY, width, height);
    }

    /**
     * Checks if a figure intersects with the selection bounds.
     * @param figure         the figure to check
     * @param selectionBounds the bounds of the selection box
     * @return true if the figure intersects the selection box, false otherwise
     */
    private boolean isWithinSelectionBox(Figure figure, Bounds selectionBounds) {
        return selectionBounds != null && figure.getBounds().intersects(selectionBounds);
    }
    /**
     * Determines if a specific figure is currently selected.
     *
     * @param figure the figure to check
     * @return true if selected, false otherwise
     */
    public boolean isSelected(Figure figure) {
        return selectedFigures.contains(figure);
    }

    /**
     * Indicates whether the selection box is currently active.
     * @return true if active, false otherwise
     */
    public boolean isSelectionBoxActive() {
        return selectionBoxActive;
    }

    /**
     * Retrieves the current selection box bounds.
     * @return the selection box bounds or null if not active
     */
    public Bounds getSelectionBox() {
        return getSelectionBounds();
    }

    /**
     * Gets the list of currently selected figures.
     * @return a list of selected figures
     */
    public static ArrayList<Figure> getSelectedFigures() {
        return selectedFigures;
    }

    /**
     * Returns null as this strategy does not involve drawing new figures.
     * @return null
     */
    @Override
    public Figure getDrawingFigure() {
        return null;
    }
}
