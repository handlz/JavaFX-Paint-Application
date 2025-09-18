package ca.utoronto.utm.assignment2.paint;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.shape.Rectangle;
import java.io.File;

/**
 * Represents the view of the paint application
 * It sets up the user interface
 */
public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;
        private ImageView imageView = new ImageView();

    /**
     * Constructor for the view with specified paint model and stage
     * @param model
     * @param stage
     */
    public View(PaintModel model, Stage stage) {
            this.paintModel = model;

            this.paintPanel = new PaintPanel(this.paintModel);
            this.shapeChooserPanel = new ShapeChooserPanel(this.paintPanel);

            // Create a border rectangle without fixed size
            Rectangle borderRectangle = new Rectangle();
            borderRectangle.setStroke(Color.BLACK);
            borderRectangle.setFill(Color.TRANSPARENT);
            borderRectangle.setStrokeWidth(2);

            // Bind the rectangle's size to the PaintPanel's size
            borderRectangle.widthProperty().bind(this.paintPanel.widthProperty());
            borderRectangle.heightProperty().bind(this.paintPanel.heightProperty());

            // Configure StackPane to allow resizing
            StackPane paintpanelcontainer = new StackPane();
            paintpanelcontainer.getChildren().addAll(imageView, borderRectangle, this.paintPanel);
            StackPane.setAlignment(this.paintPanel, Pos.CENTER);
            StackPane.setAlignment(borderRectangle, Pos.CENTER);

            // Set preferred width for ShapeChooserPanel if necessary
            this.shapeChooserPanel.setPrefWidth(150);

            // Configure the root BorderPane
            BorderPane root = new BorderPane();
            root.setTop(createMenuBar());
            root.setCenter(paintpanelcontainer);
            root.setLeft(this.shapeChooserPanel);

            // Create the scene without fixed dimensions
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Paint");
            stage.setResizable(true);
            stage.show();

            this.paintPanel.requestFocus();
        }

    /**
     * Sets the background for the paint panel
     * @param file the image file to set as the background
     */
    private void setBackground(File file){
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(this.paintPanel.widthProperty().get());
            imageView.setFitHeight(this.paintPanel.heightProperty().get());
        }

    /**
     * creates the menu bar with the file and edit menus
     * which are used for cut copy and paste
     * @return the constructed MenuBar
     */
    private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // A menu for File

                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for Edit

                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());
                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                return menuBar;
        }

    /**
     * Handles action event events triggered by clicking
     * the menu items
     * @param event
     */
    @Override
        public void handle(ActionEvent event) {

                System.out.println(((MenuItem) event.getSource()).getText());
                String command = ((MenuItem) event.getSource()).getText();
                System.out.println(command);

                switch (command) {
                    case "Undo":
                        this.paintModel.undo();
                        break;
                    case "Redo":
                        this.paintModel.redo();
                        break;
                    case "Copy":
                        this.paintModel.copy();
                        break;
                    case "Paste":
                        this.paintModel.paste();
                        break;
                    case "Cut":
                        this.paintModel.cut();
                        break;
                    case "Exit":
                        Platform.exit();
                        break;
                    case "New":
                        this.paintModel.clear();
                        break;
                    case "Open":
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Open Image File");
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
                        File file = fileChooser.showOpenDialog(null);
                        if (file != null) {
                            setBackground(file);
                        }
                        break;
                }
        }
}
