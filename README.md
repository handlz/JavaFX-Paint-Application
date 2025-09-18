# JavaFX-Paint-Application

A full-featured JavaFX paint program showcasing advanced design patterns, GUI development, and interactive drawing tools.

## 📌 Overview
This project is a sophisticated paint application built with **JavaFX**, designed to demonstrate mastery of **object-oriented programming**, **event-driven systems**, and **software architecture**.  

The application supports creating, editing, and manipulating multiple shapes with professional-grade features such as undo/redo, clipboard operations, and selection systems.

## 🚀 Features
- **15+ Drawing Tools** including lines, circles, rectangles, polygons, and freehand tools  
- **Advanced Design Patterns**: Strategy, Command, Observer, and MVC  
- **Undo/Redo System** powered by the Command pattern  
- **Clipboard Operations**: cut, copy, paste with full object lifecycle management  
- **Interactive Selection & Editing** with drag-and-drop, highlighting, and collision detection  
- **Custom Shapes** like pentagons and equilateral triangles using trigonometric algorithms  
- **Eraser Tool** implemented via low-level graphics context manipulation  

## 🛠️ Technical Highlights
- **JavaFX GUI Development** with BorderPane, StackPane, and dynamic resizing  
- **Event-Driven Programming** with mouse handlers and state machines  
- **Graphics Programming**: canvas rendering, bounds calculation, and geometric algorithms  
- **Clean Architecture** with clear Model–View–Controller separation  
- **SOLID Principles** ensuring extensibility and maintainability  

## 📂 Project Structure
- `Paint.java` – Main entry point; initializes MVC architecture  
- `PaintModel.java` – Data management, command stack, observer updates  
- `PaintPanel.java` – View component, drawing logic, and event handling  
- `View.java` – GUI orchestration, menus, layout, and file I/O  
- **Shape Classes** – Circle, Rectangle, Square, Oval, Polygon, Triangle, etc.  
- **Strategies** – Drawing, selection, deletion, and eraser strategies  

## 🎯 Learning Outcomes
This project demonstrates:  
- Strong grasp of **software engineering principles**  
- Ability to integrate multiple **design patterns** in a cohesive architecture  
- **Mathematical programming skills** through trigonometry and geometry  
- Professional code practices with **scalable, maintainable design**  

## 📸 Demo
*(Add screenshots or GIFs of your paint app here)*

## 📖 How to Run
1. Clone this repository  
   ```bash
   git clone https://github.com/<your-username>/JavaFX-Paint-Application.git
