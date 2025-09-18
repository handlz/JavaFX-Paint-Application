package ca.utoronto.utm.assignment2.paint;


import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A paint panel which will allow figures to be drawn onto the canvas, while also organizing their respective strategies
 */
public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {

    private DrawingStrategy drawingStrategy;
    private PaintModel model;
    private Color currentColor = Color.BLACK;
    private int currentThickness;
    private boolean isFill = true;

    /**
     * Construct a new paint panel
     * @param model the model that contains all figures to be drawn on the canvas
     */
    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model = model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }

    /**
     * Set the current colour of the figures to be made
     * @param color a colour for figures to be made
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    /**
     * Set the current colour of the figures to be made
     * @return the colour for the figures which will be/already are made
     */
    public Color getCurrentColor() {
        return this.currentColor;
    }

    /**
     * Set the thickness for figures to be made
     * @param thickness the thickness of the outlines of figures, from 1-10
     */
    public void setCurrentThickness(int thickness) {
        this.currentThickness = thickness;
    }

    /**
     * The current thickness of figures which were made or are going to be made
     * @return the thickness of the outlines of figures, from 1-10
     */
    public int getCurrentThickness() {
        return this.currentThickness;
    }

    /**
     * Set a new strategy for a figure to be drawn
     * @param drawingStrategy the strategy of a figure
     */
    public void setStrategyForDrawing(DrawingStrategy drawingStrategy) {

        this.drawingStrategy = drawingStrategy;
    }

    /**
     * Set the fill style of the figure
     * @param isFill the fill style of true or false
     */
    public void setFillStyle(boolean isFill) {

        this.isFill = isFill;
    }

    /**
     * Handle the mouse event for a figure to be drawn
     * @param mouseEvent the mouse event made by a user
     */
    @Override
    public void handle(MouseEvent mouseEvent) {

        if (this.drawingStrategy != null) {
            drawingStrategy.handleMouseEvent(mouseEvent, this.model, this);

            Figure figure = drawingStrategy.getDrawingFigure();
            if (figure != null) {
                Shape shape = (Shape) figure;

                if (isFill) {
                    shape.setFill();
                } else {
                    shape.setOutline();
                }
            }
        }
    }

    /**
     * Redraw the canvas
     */
    public void redraw() {
        GraphicsContext gc = this.getGraphicsContext2D();

        // Clear the canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Draw all figures
        for (Figure figure : model.getFigures()) {
            figure.draw(gc, this);
        }

        // Highlight selected figures if using SelectionStrategy
            SelectionStrategy selectionStrategy = (SelectionStrategy) drawingStrategy;

            // Draw selection box if it's active
            if (selectionStrategy.isSelectionBoxActive()) {
                drawSelectionBox(gc, selectionStrategy.getSelectionBox());
            }

            // Highlight each selected figure
            for (Figure figure : model.getFigures()) {
                if (selectionStrategy.isSelected(figure)) {
                    highlightFigure(gc, figure);

            }
        }
    }

    /**
     * Draw boxes for selected figures
     * @param gc the graphics context
     * @param selectionBox the boxes for selection
     */
    private void drawSelectionBox(GraphicsContext gc, Bounds selectionBox) {
        if (selectionBox != null) {
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(1);
            gc.setLineDashes(4);  // Dashed outline for selection box
            gc.strokeRect(selectionBox.getMinX(), selectionBox.getMinY(),
                    selectionBox.getWidth(), selectionBox.getHeight());
            gc.setLineDashes(null);  // Reset line dashes
        }
    }

    /**
     * Highlight a figure
     * @param gc the graphics context
     * @param figure the figure of context
     */
    private void highlightFigure(GraphicsContext gc, Figure figure) {
        Bounds bounds = figure.getBounds();
        if (bounds != null) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.setLineDashes(4);  // Dashed outline for highlighting
            gc.strokeRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
            gc.setLineDashes(null); // Reset line dash to solid
        }}

    /**
     * Repaint the shape on the canvas
     * @param shape the shape that will be repainted
     */
    public void clearAndRepaint(Figure shape) {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        this.update(null, null);

        if (shape != null) {
            shape.draw(g2d, this);
        }
    }


    /**
     * Update the canvas with the model of figures to be drawn
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {

        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Figure> figures = model.getFigures();
        for (Figure figure : figures) {
            if (figure != null) {
                figure.draw(g2d, this);
            }
        }
    }
}
