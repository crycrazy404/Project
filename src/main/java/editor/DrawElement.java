package editor;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public abstract class DrawElement{
    private Color color;
    private double x,y;
    private double thickness;
    public abstract void draw(GraphicsContext gc);
    public abstract void setColor(Color color);
    public abstract void setXY(double x, double y);
    public abstract void setThickness(double thickness);
}
