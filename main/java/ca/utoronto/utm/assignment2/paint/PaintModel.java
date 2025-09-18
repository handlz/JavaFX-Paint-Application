package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * PaintModel manages the list of figures and supports undo and redo operations using the Command pattern.
 */
public class PaintModel extends Observable {
        private ArrayList<Figure> figures = new ArrayList<>();
        private Stack<Command> undoStack = new Stack<>();
        private Stack<Command> redoStack = new Stack<>();
        private ArrayList<Figure> copyFigures = new ArrayList<>();
        private ArrayList<Figure> selected = new ArrayList<>();

        /**
         * Executes a command and adds it to the undo stack.
         * Clears the redo stack.
         *
         * @param command The command to execute.
         */
        public void executeCommand(Command command) {
                command.execute();
                undoStack.push(command);
                redoStack.clear();
                notifyObserversOfChange(command);
        }

        /**
         * Adds a figure using a command.
         *
         * @param f The figure to add.
         */
        public void addFigure(Figure f) {
                Command command = new AddFigureCommand(this, f);
                executeCommand(command);
        }

        /**
         * Removes a figure using a command.
         *
         * @param f The figure to remove.
         */
        public void removeFigure(Figure f) {
                Command command = new RemoveFigureCommand(this, f);
                executeCommand(command);
        }

        public ArrayList<Figure> getFigures() {
                return figures;
        }

        /**
         * Undoes the last command.
         */
        public void undo() {
                if (!undoStack.isEmpty()) {
                        Command command = undoStack.pop();
                        command.undo();
                        redoStack.push(command);
                        notifyObserversOfChange(command);
                }
        }

        /**
         * Redoes the last undone command.
         */
        public void redo() {
                if (!redoStack.isEmpty()) {
                        Command command = redoStack.pop();
                        command.execute();
                        undoStack.push(command);
                        notifyObserversOfChange(command);
                }
        }

        public void cut(){
                selected = SelectionStrategy.getSelectedFigures();
                copyFigures.clear();
                if (selected == null || selected.isEmpty()) {
                        return;
                }
                for (Figure oldFigure : selected) {
                        removeFigure(oldFigure);
                        Figure newFigure = oldFigure.makeCopy(oldFigure);
                        copyFigures.add(newFigure);
                }

        }

        /**
         * Copies the selected figures.
         */
        public void copy() {
                // Assuming SelectionStrategy manages selected figures
                selected = SelectionStrategy.getSelectedFigures();
                copyFigures.clear();
                if (selected == null || selected.isEmpty()) {
                        return;
                }
                for (Figure oldFigure : selected) {
                        Figure newFigure = oldFigure.makeCopy(oldFigure);
                        copyFigures.add(newFigure);
                }
        }

        /**
         * Pastes the copied figures using commands.
         */
        public void paste() {
                if (copyFigures.isEmpty()) {
                        return;
                }
                for (Figure oldFigure : copyFigures) {
                        Figure newFigure = oldFigure.makeCopy(oldFigure);
                        addFigure(newFigure);
                }
        }

        /**
         * Notifies observers of changes with a specific argument.
         *
         * @param arg The argument to pass to observers.
         */
        public void notifyObserversOfChange(Object arg) {
                setChanged();  // Mark this Observable as having changed
                notifyObservers(arg);  // Notify all observers
        }

        /**
         * Clears the list of figures in the model.
         */
        public void clear(){
                figures.clear();
                notifyObserversOfChange(null);
        }
}
