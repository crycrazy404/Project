package editor;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class Eraser extends DrawElement{
    double ERASER_SIZE = 20.0;
    double x,y;
    Color color;
    @Override
    public void draw(GraphicsContext gc) {
        gc.clearRect(x - ERASER_SIZE / 2, y - ERASER_SIZE / 2, ERASER_SIZE, ERASER_SIZE);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
