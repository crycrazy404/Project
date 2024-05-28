package editor;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class Eraser extends DrawElement{
    private double x,y;
    private double thickness = 20;
    Color color;
    @Override
    public void draw(GraphicsContext gc) {
        gc.clearRect(x - thickness / 2, y - thickness / 2, thickness, thickness);
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

    @Override
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
