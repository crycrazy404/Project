package editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Eraser implements DrawElement{
    private double ERASER_SIZE = 20.0;
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.clearRect(x - ERASER_SIZE / 2, y - ERASER_SIZE / 2, ERASER_SIZE, ERASER_SIZE);
    }
}
