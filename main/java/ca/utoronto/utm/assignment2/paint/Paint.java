package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A paint object that runs the paint program
 */
public class Paint extends Application {

        PaintModel model; // Model
        View view; // View + Controller

        public static void main(String[] args) {
                launch(args);
        }

        /**
         * Start the program
         * @param stage the stage that contains all the features
         * @throws Exception exceptions that are made in specific situation
         */
        @Override
        public void start(Stage stage) throws Exception {

                this.model = new PaintModel();

                // View + Controller
                this.view = new View(model, stage);
        }
}
