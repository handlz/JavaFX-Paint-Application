package ca.utoronto.utm.assignment2.paint;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {

        private PaintPanel paintPanel;
        private Button pressedButton = null;
        private MenuItem pressedMenuItem = null;
        private MenuButton pressedMenuButton = null;
        private ToggleButton pressedToggle;
        private ColorPicker color;
        private PolylineStrategy polyline;
        private ToggleButton fillToggle;
        private ToggleButton outlineToggle;
        private String command = "";

    /**
     * Construct a new shapechooser panel
      * @param paintPanel the panel that allows shapes to be drawn and handles strategies
     */
    public ShapeChooserPanel(PaintPanel paintPanel) {

            this.paintPanel = paintPanel;
            color = new ColorPicker(Color.BLACK);
            color.setOnAction(event -> {
                Color selected = color.getValue();
                paintPanel.setCurrentColor(color.getValue());
                System.out.println("Selects Colour: " + selected);
            });

                String[] buttonLabels = {"Squiggle", "Polyline", "Eraser", "Fill", "Select", "ShapeDeleter"};
                String[] iconImagePaths = {
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-squiggle.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-polyline.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-eraser.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-fill.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-select.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-shapedeleter.png"
                };

                String[] shapeButtonLabels = { "Circle", "Rectangle", "Square", "Triangle", "Oval", "Pentagon"};
                String[] shapeiconImagePaths = {
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-circle.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-rectangle.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-square.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-triangle.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-oval.png",
                        "/ca/utoronto/utm/assignment2/paint-icons/icon-pentagon.png"

                };

                int row = 0;
                for (String label : buttonLabels) {
                        Button button = new Button("");

                Image image = new Image(getClass().getResourceAsStream(iconImagePaths[row]));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(35);
                imageView.setFitWidth(35);
                button.setGraphic(imageView);

                button.setUserData(label);

                button.setMinWidth(140);
                this.add(button, 0, row); // Adding button to column 0, current row
                row++; // Increment row for next component
                button.setOnAction(this);
            }

            // Add the MenuButton at the next row
            MenuButton shapeMenu = new MenuButton("Shape");
            Image menuImage = new Image(getClass().getResourceAsStream("/ca/utoronto/utm/assignment2/paint-icons/icon-shapes.png"));
            ImageView menuImageView = new ImageView(menuImage);
            menuImageView.setFitHeight(35);
            menuImageView.setFitWidth(35);
            shapeMenu.setGraphic(menuImageView);
            shapeMenu.setMinWidth(140);
            this.add(shapeMenu, 0, row); // Adding MenuButton to column 0, current row
            shapeMenu.setOnAction(event -> {
                if (pressedMenuButton != null) {
                    pressedMenuButton.setStyle(""); // Reset style of previous ToggleButton
                }
                if (pressedButton != null) {
                    pressedButton.setStyle("");
                }
                pressedMenuButton = (MenuButton) event.getSource();
                pressedMenuButton.setStyle("-fx-background-color: lightblue;");
            });
            row++; // Increment row after adding MenuButton

            // Adding MenuItems to the MenuButton
            int menuItemIndex = 0; // Changed from shaperow to menuItemIndex for clarity
            for (String shape : shapeButtonLabels) {
                MenuItem shapeButton = new MenuItem("");
                Image picture = new Image(getClass().getResourceAsStream(shapeiconImagePaths[menuItemIndex]));
                ImageView pictureView = new ImageView(picture);
                pictureView.setFitHeight(35);
                pictureView.setFitWidth(35);
                shapeButton.setGraphic(pictureView);

                shapeButton.setUserData(shape);
                shapeMenu.getItems().add(shapeButton);
                menuItemIndex++; // Increment menuItemIndex for next MenuItem
                shapeButton.setOnAction(this);
            }

            // Add the ColorPicker at the next row
            color.setMinWidth(140);
            this.add(color, 0, row); // Adding ColorPicker to column 0, current row
            row++; // Increment row after adding ColorPicker

            // Add the Slider at the next row
            Slider thicknessSlider = new Slider(1, 10, 1);
            thicknessSlider.setShowTickLabels(true);
            thicknessSlider.setShowTickMarks(true);
            thicknessSlider.setMajorTickUnit(1);
            thicknessSlider.setBlockIncrement(1);
            thicknessSlider.setMinWidth(140);
            this.add(thicknessSlider, 0, row); // Adding Slider to column 0, current row
            row++;
            paintPanel.setCurrentThickness(1);

            thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                int thickness = newValue.intValue();
                paintPanel.setCurrentThickness(thickness);
                System.out.println("Selects line thickness to " + thickness);
            });

            // fill style option
            fillToggle = new ToggleButton("Solid Style");
            fillToggle.setMinWidth(140);

            // fillToggle is on by default
            fillToggle.setSelected(true);
            fillToggle.setStyle("-fx-background-color: lightblue;");

            // outline style option
            outlineToggle = new ToggleButton("Outline Style");
            outlineToggle.setMinWidth(140);

            // making sure that fillToggle is on only when outlineToggle is off, and vice versa
            ToggleGroup fillSelector = new ToggleGroup();
            fillToggle.setToggleGroup(fillSelector);
            outlineToggle.setToggleGroup(fillSelector);

            this.add(fillToggle, 0, row);
            row++;
            this.add(outlineToggle, 0, row);

            pressedToggle = fillToggle; // set pressedToggle as fillToggle for fill style "selected" by default

            fillToggle.setOnAction(event -> {
                // make sure old shape drawn if applicable is not affected by changed fill style
                // also make sure lines are not affected when toggling fill/outline styles
                DrawingStrategy drawingStrategy;
                if (command.equals("Circle")) {
                    drawingStrategy = new CircleStrategy();
                } else if (command.equals("Rectangle")) {
                    drawingStrategy = new RectangleStrategy();
                } else if (command.equals("Square")) {
                    drawingStrategy = new SquareStrategy();
                } else if (command.equals("Triangle")) {
                    drawingStrategy = new TriangleStrategy();
                } else if (command.equals("Oval")) {
                    drawingStrategy = new OvalStrategy();
                } else if (command.equals("Pentagon")) {
                    drawingStrategy = new PentagonStrategy();
                } else if (command.equals("Squiggle")) {
                    drawingStrategy = new SquiggleStrategy();
                } else if (command.equals("Eraser")) {
                    drawingStrategy = new EraserStrategy();
                } else if (command.equals("Fill")) {
                    drawingStrategy = new FillStrategy();
                } else if (command.equals("Polyline")) {
                    // special case for choosing Polyline since needing private field holding new PolylineStrategy
                    this.polyline = new PolylineStrategy();
                    drawingStrategy = polyline;
                } else if (command.equals("Select")){
                    drawingStrategy = new SelectionStrategy();
                } else if (command.equals("ShapeDeleter")) {
                    drawingStrategy = new ShapeDeleterStrategy();
                } else {
                    drawingStrategy = null;
                }
                paintPanel.setStrategyForDrawing(drawingStrategy);
                paintPanel.setFillStyle(true);

                if (pressedToggle != null) {
                    pressedToggle.setStyle(""); // Reset style of previous ToggleButton
                }
                pressedToggle = (ToggleButton) event.getSource();
                pressedToggle.setStyle("-fx-background-color: lightblue;");
            });

            outlineToggle.setOnAction(event -> {
                // make sure old shape drawn if applicable is not affected by changed fill style
                // also make sure lines are not affected when toggling fill/outline styles
                DrawingStrategy drawingStrategy;
                if (command.equals("Circle")) {
                    drawingStrategy = new CircleStrategy();
                } else if (command.equals("Rectangle")) {
                    drawingStrategy = new RectangleStrategy();
                } else if (command.equals("Square")) {
                    drawingStrategy = new SquareStrategy();
                } else if (command.equals("Triangle")) {
                    drawingStrategy = new TriangleStrategy();
                } else if (command.equals("Oval")) {
                    drawingStrategy = new OvalStrategy();
                } else if (command.equals("Pentagon")) {
                    drawingStrategy = new PentagonStrategy();
                } else if (command.equals("Squiggle")) {
                    drawingStrategy = new SquiggleStrategy();
                } else if (command.equals("Eraser")) {
                    drawingStrategy = new EraserStrategy();
                } else if (command.equals("Fill")) {
                    drawingStrategy = new FillStrategy();
                } else if (command.equals("Polyline")) {
                    // special case for choosing Polyline since needing private field holding new PolylineStrategy
                    this.polyline = new PolylineStrategy();
                    drawingStrategy = polyline;
                } else if (command.equals("Select")){
                    drawingStrategy = new SelectionStrategy();
                } else if (command.equals("ShapeDeleter")) {
                    drawingStrategy = new ShapeDeleterStrategy();
                } else {
                    drawingStrategy = null;
                }
                paintPanel.setStrategyForDrawing(drawingStrategy);
                paintPanel.setFillStyle(false);

                if (pressedToggle != null) {
                    pressedToggle.setStyle(""); // Reset style of previous ToggleButton
                }
                pressedToggle = (ToggleButton) event.getSource();
                pressedToggle.setStyle("-fx-background-color: lightblue;");
            });

            // panel is not selectable
            this.setFocusTraversable(false);
        }

    /**
     * Handle action events made by the user pressing a button
     * @param event
     */
    @Override
        public void handle(ActionEvent event) {
            String source = event.getSource().toString();

            if (source.startsWith("MenuItem")) {
                 String command = (String) ((MenuItem) event.getSource()).getUserData();
                 this.command = command;

                 DrawingStrategy drawingStrategy;
                 if (command.equals("Circle")) {
                     drawingStrategy = new CircleStrategy();
                 } else if (command.equals("Rectangle")) {
                     drawingStrategy = new RectangleStrategy();
                 } else if (command.equals("Square")) {
                     drawingStrategy = new SquareStrategy();
                 } else if (command.equals("Triangle")) {
                     drawingStrategy = new TriangleStrategy();
                 } else if (command.equals("Oval")) {
                     drawingStrategy = new OvalStrategy();
                 } else if (command.equals("Pentagon")) {
                     drawingStrategy = new PentagonStrategy();
                 } else {
                     drawingStrategy = null;
                     this.command = "";
                 }
                 paintPanel.setStrategyForDrawing(drawingStrategy);

                 if (pressedMenuItem != null) {
                     pressedMenuItem.setStyle(""); // Reset style of previous MenuItem
                 }
                 if (pressedButton != null) {
                     pressedButton.setStyle("");
                 }
                 pressedMenuItem = (MenuItem) event.getSource();
                 pressedMenuItem.setStyle("-fx-background-color: lightblue;");

                 System.out.println(command);
            } else if (source.startsWith("Button")) {
                String command = (String) ((Button) event.getSource()).getUserData();
                this.command = command;

                DrawingStrategy drawingStrategy;
                if (command.equals("Squiggle")) {
                    drawingStrategy = new SquiggleStrategy();
                } else if (command.equals("Eraser")) {
                    drawingStrategy = new EraserStrategy();
                } else if (command.equals("Fill")) {
                    drawingStrategy = new FillStrategy();
                } else if (command.equals("Polyline")) {
                    // special case for choosing Polyline since needing private field holding new PolylineStrategy
                    this.polyline = new PolylineStrategy();
                    drawingStrategy = polyline;
                } else if (command.equals("Select")){
                    drawingStrategy = new SelectionStrategy();
                } else if (command.equals("ShapeDeleter")) {
                    drawingStrategy = new ShapeDeleterStrategy();
                } else {
                    drawingStrategy = null;
                    this.command = "";
                }
                paintPanel.setStrategyForDrawing(drawingStrategy);

                if (pressedButton != null) {
                    pressedButton.setStyle(""); // Reset style of previous button
                }
                if (pressedMenuButton != null) {
                    pressedMenuButton.setStyle("");
                }
                if (pressedMenuItem != null) {
                    pressedMenuItem.setStyle("");
                }
                pressedButton = (Button) event.getSource();
                pressedButton.setStyle("-fx-background-color: lightblue;");
                System.out.println(command);
            }
        }
    }
